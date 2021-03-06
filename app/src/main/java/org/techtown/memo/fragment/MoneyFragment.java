package org.techtown.memo.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.memo.Util.AppConstants;
import org.techtown.memo.Memo;
import org.techtown.memo.MemoDatabase;
import org.techtown.memo.OnMemoItemClickListener;
import org.techtown.memo.OnTabItemSelectedListener;
import org.techtown.memo.R;
import org.techtown.memo.adapter.MemoAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MoneyFragment extends Fragment {
    private static final String TAG = "MemoFragment";

    TextView sectionTextView;//메모_'공부 || 운동 || 가계부 || 좋아요 || 통계' 입력하기 위해 객체 가져온다.
    RecyclerView recyclerView;
    MemoAdapter adapter;
    Memo item;

    Context context;
    OnTabItemSelectedListener listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_memo, container, false);
//        sectionTextView = rootView.findViewById(R.id.section_title);
//        sectionTextView.setText("가계부");



        initUI(rootView);



         //데이터 로딩
        loadMemoListData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        Button todayWriteButton = rootView.findViewById(R.id.add_button);
        todayWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String section  = (String) sectionTextView.getText();
                Bundle bundle = new Bundle();
                bundle.putString("MEMO_SUBJECT",section);
                if (listener != null) {
                    listener.showNewMemo(item);
                }

            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MemoAdapter();
        adapter.addItem(new Memo(1,"가계부","100억 모으기",321321,"N","2020-07-25",""));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnMemoItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder ViewHolder, View view, int position) {
                Memo item = adapter.getItem(position);// position ==> cardView 의 위치

                if(listener != null){
                    listener.showNewMemo(item);
                }
            }

            @Override
            public void favoriteClick(Memo item, View view, int position) {

            }

            @Override
            public void paletteClick(Memo item, View view, int position) {

            }

            @Override
            public void deleteClick(Memo item, View view, int position) {

            }




        });


    }

    public int loadMemoListData() {
        AppConstants.println("loadNoteListData called.");

        String sql = "select _id,MEMO_SUBJECT, MEMO_CONTENTS, MEMO_COLOR, MEMO_FAVORITE, CREATE_DATE, MODIFY_DATE from " + MemoDatabase.TABLE_MEMO
                + " where  MEMO_SUBJECT = '가계부' order by CREATE_DATE desc";

        int recordCount = -1;
        MemoDatabase database = MemoDatabase.getInstance(context);
        if (database != null) {
            Cursor outCursor = database.rawQuery(sql);

            recordCount = outCursor.getCount();
            AppConstants.println("record count : " + recordCount + "\n");

            ArrayList<Memo> items = new ArrayList<Memo>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String subject = outCursor.getString(1);
                String contents = outCursor.getString(2);
                int color = outCursor.getInt(3);
                String favorite = outCursor.getString(4);
                String dateStr = outCursor.getString(5);
                String createDateStr = null;
                if (dateStr != null && dateStr.length() > 10) {
                    try {
                        Date inDate = AppConstants.dateFormat4.parse(dateStr);
                        createDateStr = AppConstants.dateFormat3.format(inDate);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    createDateStr = "";
                }

                AppConstants.println("#" + i + " -> " + _id + ", " + subject + ", " +
                        contents + ", " + color + ", " + favorite + ", " + createDateStr);

                items.add(new Memo(_id, subject, contents, color, favorite, createDateStr));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();

        }

        return recordCount;
    }
}
