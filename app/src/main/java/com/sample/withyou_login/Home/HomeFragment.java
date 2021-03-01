package com.sample.withyou_login.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sample.withyou_login.Home.TypeActivity;
import com.sample.withyou_login.Home.VideoActivity;
import com.sample.withyou_login.R;

//      ▶홈메뉴(HomeFragment) : 동영상,견종 버튼으로 구성◀

public class HomeFragment extends Fragment {
    Button btn_video,btn_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btn_video = v.findViewById(R.id.btn_video); // 버튼클릭시 VideoActivity로 이동
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });

        btn_type = v.findViewById(R.id.btn_Type); // 버튼 클릭시 TypeActivity로 이동
        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TypeActivity.class);
                startActivity(intent);
            }
        });


        return v;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
