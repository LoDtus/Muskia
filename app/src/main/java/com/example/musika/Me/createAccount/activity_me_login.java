package com.example.musika.Me.createAccount;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.musika.GlobalClass;
import com.example.musika.Me.MeAccount;
import com.example.musika.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_me_login extends AppCompatActivity {

    EditText nicknameLog, passwordLog;
    Button login_toMe;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public GlobalClass globalClass = new GlobalClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_login);

        nicknameLog = (EditText) findViewById(R.id.nicknameLog_xml);
        passwordLog = (EditText) findViewById(R.id.passwordLog_xml);
        login_toMe = (Button) findViewById(R.id.login_toMe_xml);

        login_toMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname = nicknameLog.getText().toString();
                final String password = passwordLog.getText().toString();
                globalClass.setNickname(nickname);

                if (nickname.isEmpty() || password.isEmpty()) {
                    Toast.makeText(activity_me_login.this, "Hãy điền đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Kiểm tra tồn tại:
                            if (snapshot.hasChild(nickname)) {
                                // Vì nickname đã tồn tại, nên giờ get password về để đối chiếu:
                                final String getPassword = snapshot.child(nickname).child("password").getValue().toString();
                                if (getPassword.equals(password)) {
                                    globalClass.setNickname(nickname);
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.activity_me_login_xml, new MeAccount());
                                    transaction.commit();

                                } else {
                                    Toast.makeText(activity_me_login.this, "Sai mật khẩu", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(activity_me_login.this, "Người dùng không tồn tại", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        //======================================================================================
    }
}