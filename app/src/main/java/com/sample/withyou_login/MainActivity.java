package com.sample.withyou_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sample.withyou_login.Board.BoardFragment;
import com.sample.withyou_login.Chating.ChatFragment;
import com.sample.withyou_login.Home.HomeFragment;
import com.sample.withyou_login.My.MyFragment;


//      ▶BottomNavigationView로 4가지 메뉴구성, 앱의 주요메뉴로 이동할 수 있음◀

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavViewId);
        frameLayout = findViewById(R.id.frameLayoutId);

        setFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){  //하단탭 클릭시 4가지 주요메뉴(home,my,board,chat)로 이동
                    case R.id.homeId:
                        setFragment(new HomeFragment());
                        return true;

                    case R.id.myId:
                        setFragment(new MyFragment());
                        return true;

                    case R.id.chatId:
                        setFragment(new ChatFragment());
                        return true;

                    case R.id.boardId:
                        setFragment(new BoardFragment());
                        return true;

                    default:
                        return false;
                }


            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutId,fragment);
        fragmentTransaction.commit();

    }
}