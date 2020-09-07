package org.techtown.memo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.memo.fragment.HealthFragment;
import org.techtown.memo.Memo;
import org.techtown.memo.MemoDatabase;
import org.techtown.memo.fragment.MemoFragment;
import org.techtown.memo.fragment.MoneyFragment;
import org.techtown.memo.fragment.NewMemoFragment;
import org.techtown.memo.OnTabItemSelectedListener;
import org.techtown.memo.R;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener {
    private static final String TAG = "MainActivity";

    MemoFragment studyFragment;
    HealthFragment healthFragment;
    MoneyFragment moneyFragment;

    NewMemoFragment newMemoFragment;
    BottomNavigationView bottomNavigation;
    String subject="제목 입력";


    public MemoDatabase mDatabase = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //fragment 생성됐지만 아직 작동하지 않는다
        studyFragment= new MemoFragment();
        healthFragment = new HealthFragment();
        moneyFragment = new MoneyFragment();



        //기본 fragment 설정을 위해 replace and commit 을해서 작동
        getSupportFragmentManager().beginTransaction().replace(R.id.container, studyFragment).commit();//


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //아래 네비게이션 클릭시 fragment 변경

                switch (item.getItemId()) {
                    case R.id.tab_study:
                        subject ="제목 입력";
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, studyFragment).commit();
                        return true;

                    case R.id.tab_health:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, healthFragment).commit();
                        return true;

                    case R.id.tab_money:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, moneyFragment).commit();
                        return true;


//                    case R.id.tab_newmemo:
//                        newMemoFragment = new NewMemoFragment();
//                        //subject를  번들에 담아 newMomoFragment로 이동
//                        Bundle result = new Bundle();
//                            if(subject !=null) {
//                                Toast.makeText(getApplicationContext(),subject+" 왔다!!",Toast.LENGTH_SHORT).show();
//                                result.putString("MEMO_SUBJECT", subject);
//                                newMemoFragment.setArguments(result);
//                            }
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, newMemoFragment).commit();
//
//                        return true;
                }

                return false;
            }
        });


        openDatabase();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    /**
     * 데이터베이스 열기 (데이터베이스가 없을 때는 만들기)
     */
    public void openDatabase() {
        // open database
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = MemoDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Memo database is open.");
        } else {
            Log.d(TAG, "Memo database is not open.");
        }
    }
    @Override
    public void onTabSelected(int position) {

        if (position == 0) {

            bottomNavigation.setSelectedItemId(R.id.tab_study);

        } else if (position == 1) {

            bottomNavigation.setSelectedItemId(R.id.tab_health);

        } else if (position == 2) {

            bottomNavigation.setSelectedItemId(R.id.tab_money);

        }
//        else if (position == 3) {
//            bottomNavigation.setSelectedItemId(R.id.tab_favorite);
//
//        }else if (position == 4) {
//            newMemoFragment = new NewMemoFragment();
//            Bundle result = new Bundle();
//            if(subject !=null) {
//                Toast.makeText(getApplicationContext(),subject+" 왔다!!22",Toast.LENGTH_SHORT).show();
//                result.putString("MEMO_SUBJECT", subject);
//                newMemoFragment.setArguments(result);
//            }
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, newMemoFragment).commit();
//            bottomNavigation.setSelectedItemId(R.id.tab_newmemo);
//        }

    }
    @Override
    public void showNewMemo(Memo item) {

        NewMemoFragment newMemoFragment= new NewMemoFragment();
        newMemoFragment.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newMemoFragment).commit();

    }







}







