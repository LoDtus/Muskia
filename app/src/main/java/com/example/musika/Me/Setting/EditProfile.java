package com.example.musika.Me.Setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.musika.GlobalClass;
import com.example.musika.Me.MeAccount;
import com.example.musika.Me.Setting.isArtist.ArtistAdapter;
import com.example.musika.Me.Setting.isArtist.CategoryArtist;
import com.example.musika.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditProfile extends AppCompatActivity {
    EditText fullname_ep, password_ep, country_ep, describe_ep;
    ImageView avatar;
    Drawable drawable;
    Spinner isArtist;
    ArtistAdapter artistAdapter;
    Button saveProfile;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    GlobalClass globalClass = new GlobalClass();
    private Uri avaUri;
    private int AVATAR = 102;
    private ProgressDialog progressDialog;
    private StorageReference storageAvatar;
    TextView nickname_pf, fullname_title;


    // Tạo tên cho file khi upload lên Firebase:
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA);
    Date now = new Date();
    String fileName = formatter.format(now);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Get các thông tin đã có:
        avatar = (ImageView) findViewById(R.id.avatar_xml);
        fullname_title = (TextView) findViewById(R.id.fullname_title_xml);
        nickname_pf = (TextView) findViewById(R.id.nickname_pf_xml);
        fullname_ep = (EditText) findViewById(R.id.fullname_ep_xml);
        password_ep = (EditText) findViewById(R.id.password_ep_xml);
        country_ep = (EditText) findViewById(R.id.country_ep_xml);
        describe_ep = (EditText) findViewById(R.id.describe_ep_xml);

        databaseReference.child("User").child(globalClass.getNickname())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getFullname = snapshot.child("fullname").getValue().toString();
                final String getPassword = snapshot.child("password").getValue().toString();
                final String getCountry = snapshot.child("country").getValue().toString();
                final String getDescribe = snapshot.child("describe").getValue().toString();
                final String getAvatar = snapshot.child("avatar").getValue().toString();
                final String getisArtist = snapshot.child("isArtist").getValue().toString();

                fullname_title.setText(getFullname);
                nickname_pf.setText(globalClass.getNickname());
                fullname_ep.setHint(getFullname);
                password_ep.setHint(getPassword);
                country_ep.setHint(getCountry);
                describe_ep.setHint(getDescribe);

                if (getAvatar == null) {
                    drawable = getResources().getDrawable(R.drawable.account_ava);
                    avatar.setImageDrawable(drawable);
                } else {
                    Picasso.get().load(getAvatar).into(avatar);
                }
                // Set Blur cho Avatar:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    avatar.setRenderEffect(RenderEffect.createBlurEffect(8, 8, Shader.TileMode.MIRROR));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set trạng thái isArtist:
        isArtist = (Spinner) findViewById(R.id.isArtist_xml);
        artistAdapter = new ArtistAdapter(this, R.layout.item_select_artist, getListCate());
        isArtist.setAdapter(artistAdapter);

        isArtist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (artistAdapter.getItem(i).getNameCategory() == "Người dùng cơ bản") {
                    Toast.makeText(EditProfile.this, artistAdapter.getItem(i).getNameCategory(), Toast.LENGTH_SHORT).show();
                } else if (artistAdapter.getItem(i).getNameCategory() == "Nhà sáng tạo") {
                    Toast.makeText(EditProfile.this, artistAdapter.getItem(i).getNameCategory(), Toast.LENGTH_SHORT).show();
                } else if (artistAdapter.getItem(i).getNameCategory() == "Nghệ sĩ") {
                    Toast.makeText(EditProfile.this, artistAdapter.getItem(i).getNameCategory(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Upload ảnh cho avatar:
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAvatar();
            }
        });

        // saveProfile:
        saveProfile = (Button) findViewById(R.id.saveProfile_xml);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = fullname_ep.getText().toString();
                final String password = password_ep.getText().toString();
                final String country = country_ep.getText().toString();
                final String describe = describe_ep.getText().toString();

                if (!fullname.isEmpty()) {
                    databaseReference.child("User").child(globalClass.getNickname()).child("fullname").setValue(fullname);
                } else if (!password.isEmpty()) {
                    databaseReference.child("User").child(globalClass.getNickname()).child("password").setValue(password);
                } else if (!country.isEmpty()) {
                    databaseReference.child("User").child(globalClass.getNickname()).child("country").setValue(country);
                } else if (!describe.isEmpty()) {
                    databaseReference.child("User").child(globalClass.getNickname()).child("describe").setValue(describe);
                }
                // Upload avatar lên Storage Firebase:
                // uploadAvatar();

                // Chuyển về màn hình Profile:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.editProfile_xml, new MeAccount());
                transaction.commit();
            }
        });

    }
    // Chọn avatar:
    private void selectAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, AVATAR);
    }
    // Hiện avatar đã chọn:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==AVATAR && data!=null && data.getData()!=null) {
            avaUri = data.getData();
            avatar.setImageURI(avaUri);
        }
    }
    private void uploadAvatar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        // Đẩy Avatar lên Storage Firebase:
        storageAvatar = FirebaseStorage.getInstance().getReference("Avatar/" + fileName);
        storageAvatar.putFile(avaUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //avatar.setImageURI(null);
                        storageAvatar.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child("User").child(globalClass.getNickname())
                                        .child("avatar").setValue(uri.toString());
                            }
                        });
                        Snackbar.make(findViewById(android.R.id.content), "Done!", Snackbar.LENGTH_LONG).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(EditProfile.this, "Failed!", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Upload: " + (int) progressPercent + "%");
                    }
                });
    }

    private List<CategoryArtist> getListCate() {
        List<CategoryArtist> list = new ArrayList<>();
        list.add(new CategoryArtist("Người dùng cơ bản"));
        list.add(new CategoryArtist("Nhà sáng tạo"));
        list.add(new CategoryArtist("Nghệ sĩ"));
        return list;
    }
}