package org.techtown.memo.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

import petrov.kristiyan.colorpicker.ColorPicker;

public class MemoFragment extends Fragment {
    private static final String TAG = "MemoFragment";

    TextView sectionTextView;
    RecyclerView recyclerView;
    MemoAdapter adapter;
    Memo item;
    Context context;
    OnTabItemSelectedListener listener;
    String section="모든 메모";



    String[] items={"모든 메모","좋아요"};






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




        Spinner spinner = rootView.findViewById(R.id.memo_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                rootView.getContext(), R.layout.spinner_custom,items);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        spinner.setAdapter(adapter);
        switch(section){
            case "모든 메모":
                spinner.setSelection(0);
                break;
            case "좋아요":
                spinner.setSelection(1);
                break;
            default:
                spinner.setSelection(0);
                break;
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public  void onItemSelected(AdapterView<?> adapterView,View view,int position, long id){


                if(position ==0){
                    section="모든 메모";
                    loadMemoListData();
                }else if(position == 1){
                    section="좋아요";
                    loadFavoMemoListData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
                sectionTextView.setText("");
            }
        });

        //리사이클러뷰 세팅
        initUI(rootView);

        // 데이터 로딩
        loadMemoListData();

        return rootView;
    }

    private void initUI(ViewGroup rootView) {





        Button todayWriteButton = rootView.findViewById(R.id.add_button);
        todayWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//메모 추가 버튼 작성시

                //newMemo 생성 및 이동
                if (listener != null) {
                    Bundle result = new Bundle();
                    result.putString("MEMO_SUBJECT","제목 입력");

                    listener.onTabSelected(4);
                }

            }
        });
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MemoAdapter();
        adapter.addItem(new Memo(1,"공부","안드로이드 정복",321321,"N","2020-07-25",""));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnMemoItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder ViewHolder, View view, int position) {
                Memo item = adapter.getItem(position);// position ==> cardView 의 위치
                Toast.makeText(getContext(),"선택된 탭 "+position,Toast.LENGTH_SHORT).show();
                if(listener != null){
                    listener.showNewMemo(item);
                }


            }

            @Override
            public void favoriteClick(Memo item, View view, int position) {
                item = adapter.getItem(position);// position ==> cardView 의 위치
                Toast.makeText(getContext(),"선택된 탭 "+position,Toast.LENGTH_SHORT).show();
                favoriteChange(item);

            }

            @Override
            public void paletteClick(Memo item, View view, int position) {
//                item = adapter.getItem(position);// position ==> cardView 의 위치
                Toast.makeText(getContext(),"선택된 탭 "+position,Toast.LENGTH_SHORT).show();
                openColorPicker(position);

            }

            @Override
            public void deleteClick(Memo item, View view, int position) {
                item= adapter.getItem(position);
                deleteMemo(item);

            }




        });




    }

    public int loadMemoListData() {
        AppConstants.println("loadSTUDYListData called.");

        String sql = "select _id,MEMO_SUBJECT, MEMO_CONTENTS, MEMO_COLOR, MEMO_FAVORITE, CREATE_DATE, MODIFY_DATE from " + MemoDatabase.TABLE_MEMO
                + " order by CREATE_DATE desc";

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

    public void setItem(Memo item) {
        this.item = item;
    }

    private void favoriteChange(Memo selectItem) {
        if (selectItem != null) {

            String favo = selectItem.getMEMO_FAVORITE();
            if(favo.equals("Y")){
                favo="N";
            }else if(favo.equals("N")){
                favo="Y";
            }
            if(!(favo.equals("Y")||favo.equals("N"))){
                favo ="Y";
            }


            // update note
            String sql = "update " + MemoDatabase.TABLE_MEMO +
                    " set " +
                    "   MEMO_FAVORITE = '" + favo + "'" +
                    " where " +
                    "   _id = " + selectItem.get_id();

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
        if(section.equals("모든 메모")){
            loadMemoListData();
        }else if(section.equals("좋아요")){
            loadFavoMemoListData();
        }

    }

    public void openColorPicker(int position) {
        item = adapter.getItem(position);
        final ColorPicker colorPicker = new ColorPicker(getActivity());  // context 들어가야 함 ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#ffab91");
        colors.add("#F48FB1");
        colors.add("#ce93d8");
        colors.add("#b39ddb");
        colors.add("#9fa8da");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {


                        // 색상까지는 들어옴
                        Toast.makeText(getContext(),"selectedColor : "+color,Toast.LENGTH_SHORT).show();
                        //DB의 값을 변경
                        item.setMEMO_COLOR(color);
                        //데이터 변경
                        modifyMemo();
                        //데이터 리로딩
                        loadMemoListData();
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성


    }

    private void deleteMemo(Memo selectItem) {
        AppConstants.println("Student deleteNote called.");

        if (selectItem != null) {
            // delete note
            String sql = "delete from " + MemoDatabase.TABLE_MEMO +
                    " where " +
                    "   _id = " + selectItem.get_id();

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
        loadMemoListData();
    }

    private void modifyMemo() {
        if (item != null) {


            String contents = item.getMEMO_CONTENTS();
            int color = item.getMEMO_COLOR();



            // update note
            String sql = "update " + MemoDatabase.TABLE_MEMO +
                    " set " +

                    "   MEMO_CONTENTS = '" + contents + "'" +
                    "   ,MEMO_COLOR =  "+ color  +
                    " where " +
                    "   _id = " + item.get_id();

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
        loadMemoListData();
    }


    public int loadFavoMemoListData() {
        AppConstants.println("loadFAVOListData called.");

        String sql = "select _id,MEMO_SUBJECT, MEMO_CONTENTS, MEMO_COLOR, MEMO_FAVORITE, CREATE_DATE, MODIFY_DATE from " + MemoDatabase.TABLE_MEMO
                + " where  MEMO_FAVORITE = 'Y' order by CREATE_DATE desc";

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
