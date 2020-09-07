package org.techtown.memo.fragment;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.techtown.memo.Util.DateUtil;
import org.techtown.memo.Util.Keys;
import org.techtown.memo.R;
import org.techtown.memo.adapter.CalendarAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HealthFragment extends Fragment {
    public int mCenterPosition;
    public ArrayList<Object> mCalendarList = new ArrayList<>();
    public TextView textView;
    public RecyclerView recyclerView;
    public RecyclerView health_setRecyclerView;
    private CalendarAdapter mAdapter;
    private StaggeredGridLayoutManager manager;
    private int empty_count;


    public String header_date;

    public int mCurrentMonth;
    public int month_count= 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_health, container, false);

        initView(rootView);

        initSet();

        textView.setText(header_date);
        setCalendarRecycler();
        setDayRecycler();

        return rootView;
    }



    public void initView(View v){

        textView = (TextView)v.findViewById(R.id.title);
        recyclerView = (RecyclerView)v.findViewById(R.id.calendar);
        health_setRecyclerView = (RecyclerView) v.findViewById(R.id.health_today_goal);
        Button pre_month_button = v.findViewById(R.id.pre_month_button);
        pre_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_count = month_count -1;
                //누르다 해가 바뀌면 year_count 바뀌게 넣어야 함;
                initSet();
                textView.setText(header_date);
                setCalendarRecycler();
            }
        });
        Button next_month_button = v.findViewById(R.id.next_month_button);
        next_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_count = month_count +1;
                initSet();
                textView.setText(header_date);
                setCalendarRecycler();;
            }
        });
        Button health_add_button = v.findViewById(R.id.health_add_button);
        health_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"추가 버튼 눌렀어여",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void initSet(){

        initCalendarList();
        initTodayList();
    }

    private void initTodayList() {//누른 날짜의 운동 정보 표시

    }

    public void initCalendarList() {
        GregorianCalendar cal = new GregorianCalendar();
        System.out.println(cal.get(Calendar.DATE));
        setCalendarList(cal);
    }

    private void setCalendarRecycler() {

        if (mCalendarList == null) {
            Log.w("TAG", "No Query, not initializing RecyclerView");
        }

        manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        System.out.println("mcalendarList.size() = "+mCalendarList.size());
        mAdapter = new CalendarAdapter(mCalendarList);

        mAdapter.setCalendarList(mCalendarList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

//        if (mCenterPosition >= 0) {
//            recyclerView.scrollToPosition(mCenterPosition);
//        }
    }

    private void setDayRecycler() {//빈공간을 포함하므로 day의 포지션은 빈공간 갯수만큼 마이너스 해야한다.


    }

    public void setCalendarList(GregorianCalendar cal) {

        //월 이동 버튼 눌렀을 때 해당 월로 달력 뿌려주기 위해
        mCurrentMonth = cal.get(Calendar.MONTH);


        int mMonth = mCurrentMonth + month_count;


        ArrayList<Object> calendarList = new ArrayList<>();


        try {
            GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), mMonth, 1, 0, 0, 0);

//            mCenterPosition = calendarList.size();

//            mCurrentDay = calendar.get(Calendar.DATE);
            System.out.println("mCurrentYear = "+cal.get(Calendar.YEAR) );
            System.out.println("mCurrentMonth = "+cal.get(Calendar.MONTH) );

            // 타이틀인듯
//          calendarList.add(calendar.getTimeInMillis());

            //현재 년 월 상단에 표시 부분
            long today = calendar.getTimeInMillis();
            header_date = DateUtil.getDate(today, DateUtil.CALENDAR_HEADER_FORMAT);



            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
            System.out.println("dayOfWeek = "+dayOfWeek);
            int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

            // EMPTY 생성
            empty_count=0;
            for (int j = 0; j < dayOfWeek; j++) {
                calendarList.add(Keys.EMPTY);
                empty_count++;
            }
            for (int j = 1; j <= max; j++) {
                //여기에 저장된 운동 값 넣어서 뿌려주기
                calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
            }

            // TODO : 결과값 넣을떄 여기다하면될듯

        } catch (Exception e) {
            e.printStackTrace();
        }


        mCalendarList = calendarList;
    }


}