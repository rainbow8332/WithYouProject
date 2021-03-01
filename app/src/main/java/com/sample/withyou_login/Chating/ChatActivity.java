package com.sample.withyou_login.Chating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.withyou_login.GetStartedMainActivity;
import com.sample.withyou_login.My.MemberInitActivity;
import com.sample.withyou_login.My.SignUpActivity;
import com.sample.withyou_login.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//      ▶채팅 : 1.닉네임 입력(다이얼로그창) 2.채팅방 개설 3.채팅방 입장(listView 클릭) 4.채팅◀

public class ChatActivity extends AppCompatActivity {
    private Button btnLogout,add_room;
    private EditText room_name;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnLogout = findViewById(R.id.btn_logout); // 버튼클릭시 로그아웃
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    getStartedActivity();
                }
                switch (v.getId()) {
                    case R.id.btn_logout:
                        FirebaseAuth.getInstance().signOut();
                        getStartedActivity();
                        break;

                }
            }
        });

        add_room = findViewById(R.id.btn_add_room); // 채팅방 개설 버튼
        room_name = findViewById(R.id.room_name_edittext); // 채팅방 제목 입력(input)
        listView = findViewById(R.id.listView); // 채팅방 목록(listView)

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_of_rooms);

        listView.setAdapter(arrayAdapter);

        request_user_name();

        //버튼 클릭시 채팅방 개설
        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put(room_name.getText().toString(),""); // 입력한 채팅방 제목 firebase에 저장됨
                Toast.makeText(getApplicationContext(), "  '"+room_name.getText()+"'  채팅방 개설",Toast.LENGTH_SHORT).show();
                root.updateChildren(map);
            }
        });

        //개설된 채팅방 리스트
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Set<String> set = new HashSet<>();
                Iterator i = snapshot.getChildren().iterator();
                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //개별 채팅방으로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Chat_Room.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });

    }

    //닉네임 입력 다이얼로그
    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("\uD83D\uDC36닉네임을 입력해주세요\uD83D\uDC36");

        EditText intput_field = new EditText(this);

        builder.setView(intput_field);

        //닉네임 입력 다이얼로그_등록
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(intput_field.getText())){
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    request_user_name();
                }else {
                    Toast.makeText(getApplicationContext(),"닉네임 등록",Toast.LENGTH_SHORT).show();
                    name = intput_field.getText().toString();

                }
            }
        });

        //닉네임 입력 다이얼로그_취소
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"취소",Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void getStartedActivity () {
        Intent intent = new Intent(this, GetStartedMainActivity.class);
        startActivity(intent);
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}