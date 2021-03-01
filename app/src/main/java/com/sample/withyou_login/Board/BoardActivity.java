package com.sample.withyou_login.Board;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sample.withyou_login.GetStartedMainActivity;
import com.sample.withyou_login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//      ▶게시판 : 게시글 CRUD◀

public class BoardActivity extends AppCompatActivity {

    private FloatingActionButton btnAdd; // 플로팅 버튼 : 클릭하여 게시글 작성
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(); // FirebaseDatabase 객체생성
    private DatabaseReference myRef = database.getReference("Notes"); // Firebase에 Notes테이블 생성

    private List<BoardNotes> list = new ArrayList<>(); // BoardNotes 객체를 어댑터 쪽으로 담을 arraylist
    private BoardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.board_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNote();
            }
        });

        readDate();

    }

    // C : 게시글 작성 다이얼로그
    private void showDialogAddNote(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_note);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        ImageButton btnClose = dialog.findViewById(R.id.btn_close); //다이얼로그 내 'X' 버튼, 클릭시 다이얼로그창 닫힘
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText edNote = dialog.findViewById(R.id.ed_note);
        Button btnAdd = dialog.findViewById(R.id.btn_add);


        //플로팅 버튼 클릭시 게시글 작성 다이얼로그 팝업됨
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edNote.getText())){
//                    edNote.setError("게시글을 입력해주세요.");
                    Toast.makeText(getApplicationContext(),"게시글을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    addDataToFirebase(edNote.getText().toString());
//                    Toast.makeText(getApplicationContext(),"게시글 입력",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    //다이얼로그 창 내 게시글 direbase로 저장
    private void addDataToFirebase(String text){

        String id = myRef.push().getKey();
        BoardNotes notes = new BoardNotes(id,text);

        myRef.child(id).setValue(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"입력",Toast.LENGTH_SHORT).show();
            }
        });


    }

    // R : 게시글 읽기(recyclerView)
    private void readDate(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //firebase 데이터베이스의 데이터를 받아오는 곳

                list.clear(); // 기존 배열리스트가 존재하지 않게 초기화

                //반복문으로 데이터 list를 추출해냄, firebase에서 가져온 데이터를 arraylist에 담아 adapter로 전달
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                   BoardNotes value = snapshot1.getValue(BoardNotes.class); // 만들어뒀던 BoardNotes객체에 데이터를 담는다.
                    list.add(value); //담은 데이터들을 리사이클러뷰로 보낼 준비
                }

                //adapter.notifyDataSetChanged(); 리스트 저장 및 새로고침
                adapter = new BoardAdapter(BoardActivity.this,list);
                recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던 중 에러발생 시
                Log.d("TAG", "onCancelled: ",error.toException());
            }
        });

    }

    //recyclerView 내 삭제,수정 interface
    private void setClick() {
        adapter.setOnCallBack(new BoardAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(BoardNotes boardNotes) {
                deleteNote(boardNotes);
            }

            @Override
            public void onButtonEditClick(BoardNotes boardNotes) {
                showDialogUpdateNote(boardNotes);

            }
        });
    }

    // U : 게시글 수정
    private void updateNote(BoardNotes boardNotes, String newText) {
        myRef.child(boardNotes.getId()).child("text").setValue(newText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"수정",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // D : 게시글 삭제
    private void deleteNote(BoardNotes boardNotes){
        myRef.child(boardNotes.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(),"게시글 :    ' "+boardNotes.getText()+" '    삭제",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogUpdateNote(BoardNotes boardNotes){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_note);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final  EditText edNote = dialog.findViewById(R.id.ed_note);
        edNote.setText(boardNotes.getText());
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        btnAdd.setText("수정");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edNote.getText())){
                    edNote.setError("This field can't be empty!");
                }else{
                    updateNote(boardNotes,edNote.getText().toString());
                    dialog.dismiss();
                }
            }
        });


        dialog.show();


    }


}