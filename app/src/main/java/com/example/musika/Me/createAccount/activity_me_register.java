package com.example.musika.Me.createAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musika.GlobalClass;
import com.example.musika.Me.Setting.EditProfile;
import com.example.musika.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_me_register extends AppCompatActivity {
    EditText nicknameReg, fullnameReg, passwordReg, compasswordReg;
    Button register_toMe;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    GlobalClass globalClass = new GlobalClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_register);

        nicknameReg = (EditText) findViewById(R.id.nicknameReg_xml);
        fullnameReg = (EditText) findViewById(R.id.fullnameReg_xml);
        passwordReg = (EditText) findViewById(R.id.passwordReg_xml);
        compasswordReg = (EditText) findViewById(R.id.compassword_xml);
        register_toMe = (Button) findViewById(R.id.register_toMe_xml);

        // tạo sự kiện cho button đăng ký:
        register_toMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname = nicknameReg.getText().toString();
                final String fullname = fullnameReg.getText().toString();
                final String password = passwordReg.getText().toString();
                final String compassword = compasswordReg.getText().toString();

                if (nickname.isEmpty() || fullname.isEmpty() || password.isEmpty() || compassword.isEmpty()) {
                    // Check đủ thông tin:
                    Toast.makeText(activity_me_register.this, "Hãy điền đủ thông tin", Toast.LENGTH_LONG).show();
                } else if (!password.equals(compassword)) {
                    // Check mật khẩu:
                    Toast.makeText(activity_me_register.this, "Xác nhận lại mật khẩu", Toast.LENGTH_LONG).show();
                } else {
                    // Check tồn tại:
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(nickname)) {
                                Toast.makeText(activity_me_register.this, "Người dùng đã tồn tại", Toast.LENGTH_LONG).show();
                            } else {
                                globalClass.setNickname(nickname);
                                databaseReference.child("User").child(nickname).child("fullname").setValue(fullname);
                                databaseReference.child("User").child(nickname).child("password").setValue(password);
                                databaseReference.child("User").child(nickname).child("country").setValue(0);
                                databaseReference.child("User").child(nickname).child("describe").setValue(0);
                                databaseReference.child("User").child(nickname).child("isArtist").setValue(0);
                                databaseReference.child("User").child(nickname).child("follow").setValue(0);
                                databaseReference.child("User").child(nickname).child("followby").setValue(0);
                                databaseReference.child("User").child(nickname).child("liked").setValue(0);
                                databaseReference.child("User").child(nickname).child("viewd").setValue(0);
                                databaseReference.child("User").child(nickname).child("song").setValue(0);
                                databaseReference.child("User").child(nickname).child("style").setValue(0);
                                databaseReference.child("User").child(nickname).child("avatar").setValue(0);

                                Intent intent = new Intent(activity_me_register.this, EditProfile.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        //==========================================================================================


    }
}