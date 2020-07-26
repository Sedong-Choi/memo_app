package org.techtown.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener{
    private static final String TAG = "MainActivity";

    StudyFragment studyFragment;
    HealthFragment healthFragment;
    MoneyFragment moneyFragment;
    FavoriteFragment favoriteFragment;
    NewMemoFragment newMemoFragment;
    BottomNavigationView bottomNavigation;
    Bundle bundle = new Bundle();

    public static MemoDatabase mDatabase = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        studyFragment= new StudyFragment();
        healthFragment = new HealthFragment();
        moneyFragment = new MoneyFragment();
        favoriteFragment = new FavoriteFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, studyFragment).commit();


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab_study:

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

                    case R.id.tab_favorite:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, favoriteFragment).commit();
                        return true;
                    case R.id.tab_newmemo:
                        newMemoFragment = new NewMemoFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, newMemoFragment).commit();

                        return true;
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
        }   else if (position == 3) {
            bottomNavigation.setSelectedItemId(R.id.tab_favorite);
        }else if (position == 4) {
            newMemoFragment = new NewMemoFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, newMemoFragment).commit();
        }
    }
    @Override
            public void showNewMemo(Memo item) {

        NewMemoFragment newMemoFragment= new NewMemoFragment();
        newMemoFragment.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newMemoFragment).commit();

    }
}