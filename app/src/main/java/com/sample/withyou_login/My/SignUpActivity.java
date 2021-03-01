package com.sample.withyou_login.My;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sample.withyou_login.R;

//      ▶회원가입 : 회원가입,로그인 버튼으로 구성
//                  1.회원가입 성공시 회원정보 입력(MemberInitActivity)로 이동
//                  2.로그인 버튼 클릭시 로그인 화면으로 이동◀

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user == null){
//            myStartActivity(SignUpActivity.class);
//        }else{
//            for (UserInfo profile : user.getProviderData()){
//                String name = profile.getDisplayName();
//                if(name != null){
//                    if (name.length() == 0){
//                        myStartActivity(MemberInitActivity.class);
//                    }
//                }
//            }
//        }

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener); // 회원가입 버튼
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener); // 로그인 페이지로 이동 버튼

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signUpButton:
                    signUp();
                    break;
                case R.id.gotoLoginButton:
                    startLoginActivity();
                    break;


            }
        }
    };

    //회원가입
    private void signUp() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.passwordCheckEditText)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 ){
            if (password.equals(passwordCheck)) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();


                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null){
                                        myStartActivity(MemberInitActivity.class);
                                    }

                                } else {
                                    if (task.getException() != null){
                                        Log.w(TAG, "createUserWithEmain:failure ", task.getException());
                                        Toast.makeText(getApplicationContext(), "이미 가입된 회원", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호 불일치", Toast.LENGTH_SHORT).show();

            }

        }else {
            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();

        }

    }

    private void startLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,MemberInitActivity.class);
        startActivity(intent);
    }

}
