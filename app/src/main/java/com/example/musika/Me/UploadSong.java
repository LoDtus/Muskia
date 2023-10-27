package com.example.musika.Me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadSong extends AppCompatActivity {

    private ImageView posterUpload;
    private EditText nameSong_upload, artist_upload;
    private Button chooseFile, upload;
    private TextView tv_chooseFile;
    private Uri imgUri, songUri;
    private StorageReference storagePoster, storageSong;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ProgressDialog progressDialog;
    private int POSTER = 100, SONG = 101, quanSong=0;
    private String songID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song);

        posterUpload = (ImageView) findViewById(R.id.posterUpload_xml);
        chooseFile = (Button) findViewById(R.id.chooseFile_xml);
        upload = (Button) findViewById(R.id.upload_xml);
        tv_chooseFile = (TextView) findViewById(R.id.tv_chooseFile_xml);
        nameSong_upload = (EditText) findViewById(R.id.nameSong_upload_xml);
        artist_upload = (EditText) findViewById(R.id.artist_upload_xml);

        // Chọn Poster:
        posterUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPoster();
            }
        });

        // Chọn bài hát:
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSong();
            }
        });

        // Upload:
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Song").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String nameSong = nameSong_upload.getText().toString();
                        final String artist = artist_upload.getText().toString();

                        if (snapshot.exists()) {
                            quanSong = (int) snapshot.getChildrenCount() + 1;
                            songID = "song" + Integer.toString(quanSong);
                        } else {
                            songID = "song0";
                        }

                        databaseReference.child("Song").child(songID).child("song").setValue(nameSong);
                        databaseReference.child("Song").child(songID).child("artist").setValue(artist);
                        databaseReference.child("Song").child(songID).child("liked").setValue(0);
                        databaseReference.child("Song").child(songID).child("viewed").setValue(0);
                        databaseReference.child("Song").child(songID).child("style").setValue(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                uploadSong();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_upload_song_xml, new MeAccount());
                transaction.commit();
            }
        });
    }
    private void selectPoster() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, POSTER);
    }
    private void selectSong() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SONG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==POSTER && data!=null && data.getData()!=null) {
            imgUri = data.getData();
            posterUpload.setImageURI(imgUri);
        }
        else if (requestCode==SONG && data!=null && data.getData()!=null) {
            songUri = data.getData();
            tv_chooseFile.setText("Đã chọn");
        }
    }
    private void uploadSong() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        // Tạo tên cho file khi upload lên Firebase:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);

        // Đẩy Poster lên Storage Firebase:
        storagePoster = FirebaseStorage.getInstance().getReference("Poster/" + fileName);
        storagePoster.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        posterUpload.setImageURI(null);
                        storagePoster.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child("Song").child(songID)
                                        .child("poster").setValue(uri.toString());
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
                        Toast.makeText(UploadSong.this, "Failed!", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Upload: " + (int) progressPercent + "%");
                    }
                });

        // Đẩy Song lên Storage Firebase:
        storageSong = FirebaseStorage.getInstance().getReference("Song/" + fileName);
        storageSong.putFile(songUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                        storageSong.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child("Song").child(songID)
                                        .child("url").setValue(uri.toString());
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
                        Toast.makeText(UploadSong.this, "Failed!", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Upload: " + (int) progressPercent + "%");
                    }
                });
    }
}