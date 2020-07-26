package org.techtown.memo;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class StudyFragment extends Fragment {
    private static final String TAG = "MemoFragment";

    TextView sectionTextView;
    ImageView favoriteImageView;
    RecyclerView recyclerView;
    MemoAdapter adapter;
    Memo item;
    Context context;
    OnTabItemSelectedListener listener;
    NewMemoFragment newMemoFragment;
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
        sectionTextView = rootView.findViewById(R.id.section_title);
        sectionTextView.setText("공부");

        initUI(rootView);
        // 데이터 로딩
        loadMemoListData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        Button todayWriteButton = rootView.findViewById(R.id.add_button);
        todayWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("MEMO_SUBJECT","STUDY");
                newMemoFragment = new NewMemoFragment();
                newMemoFragment.setArguments(bundle);
                Toast.makeText(getContext(),"add_button clicked",Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onTabSelected(4);
                }

            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MemoAdapter();
        adapter.addItem(new Memo(1,"공부","안드로이드 정복","#FF00FF00","N","2020-07-25",""));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnMemoItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder holder, View view, int position) {
                Memo item = adapter.getItem(position);
                setItem(item);//클릭한 recyclerView 저장
                Log.d(TAG, "아이템 선택됨 : " + item.get_id());

                if (listener != null) {
                    listener.showNewMemo(item);
                }
            }
        });


    }

    public int loadMemoListData() {
        AppConstants.println("loadSTUDYListData called.");

        String sql = "select _id,MEMO_SUBJECT, MEMO_CONTENTS, MEMO_COLOR, MEMO_FAVORITE, CREATE_DATE, MODIFY_DATE from " + MemoDatabase.TABLE_MEMO
                + " where  MEMO_SUBJECT = '공부' order by CREATE_DATE desc";

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
                String color = outCursor.getString(3);
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

    public void setItem(Memo item) {
        this.item = item;
    }

    private void favoriteChange() {
        if (item != null) {
            String favo = item.getMEMO_FAVORITE();


            // update note
            String sql = "update " + MemoDatabase.TABLE_MEMO +
                    " set " +
                    "   ,MEMO_FAVORITE = '" + "Y" + "'" +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }
}
