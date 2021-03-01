package com.sample.withyou_login.Board;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.sample.withyou_login.R;

//      ▶게시판(BoardFragment) : 게시판 버튼으로 구성◀

public class BoardFragment extends Fragment {

    Button btn_bord;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bord, container, false);

        btn_bord = v.findViewById(R.id.btn_bord);
        btn_bord.setOnClickListener(new View.OnClickListener() { // 버튼 클릭시 BoardActivity로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                startActivity(intent);
            }
        });




        return v;
    }
}