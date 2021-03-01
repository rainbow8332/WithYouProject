package com.sample.withyou_login.My;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sample.withyou_login.R;

//      ▶my(MyFragment) : 로그인 버튼으로 구성◀

public class MyFragment extends Fragment {

    Button btn_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my, container, false);

        btn_login = v.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() { // 버튼 클릭시 회원가입 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
