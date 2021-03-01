package com.sample.withyou_login.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sample.withyou_login.R;

import java.util.ArrayList;

//      ▶강아지 종류를 RecyclerView로 구현◀

public class TypeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TypeModel> typeModels;
    TypeAdapter typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        recyclerView = findViewById(R.id.recycler_view);

        Integer[] langLogo = {R.drawable.dog_poodle,R.drawable.dog_bostonterrier,
                R.drawable.dog_pomeranian,R.drawable.dog_maltese,
                R.drawable.dog_schnauzer,R.drawable.dog_cockerspaniel,
                R.drawable.dog_shiba,R.drawable.dog_spitz,
                R.drawable.dog_oldenglishsheepdog,R.drawable.dog_goldretriever,
                R.drawable.dog_siberianhusky};

        String[] lamgName = {"푸들","보스턴테리어","포메리안","말티즈","슈나우저","코카스파니엘","시바견","스피츠","올드잉글리쉬쉽독","골드리트리버","시베리안허스키"};

        typeModels = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            TypeModel model = new TypeModel(langLogo[i],lamgName[i]);
            typeModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                TypeActivity.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        typeAdapter = new TypeAdapter(typeModels, TypeActivity.this);
        recyclerView.setAdapter(typeAdapter);
    }
}