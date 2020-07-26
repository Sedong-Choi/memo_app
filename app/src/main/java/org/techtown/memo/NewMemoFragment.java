package org.techtown.memo;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import java.util.Date;

public class NewMemoFragment extends Fragment {
    private static final String TAG = "MemoFragment";
    Context context;
    OnTabItemSelectedListener listener;
    TextView dateTextView;
    TextView sectionTextView;
    EditText contentsInput;
    Memo item;
    String[] items={"공부","운동","가계부"};
    int mMode = AppConstants.MODE_INSERT;



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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_new, container, false);

        sectionTextView = rootView.findViewById(R.id.subject_title);
        //메모 수정 누르면 section 가져와서 spinner setting 해야 함

        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
               rootView.getContext(), android.R.layout.simple_spinner_item ,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public  void onItemSelected(AdapterView<?> adapterView,View view,int position, long id){
                Toast.makeText(context,"선택했엉",Toast.LENGTH_SHORT).show();
                sectionTextView.setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
                sectionTextView.setText("");
            }
        });

        initUI(rootView);
        applyItem();
        return rootView;
    }
    private void initUI(ViewGroup rootView) {

        dateTextView = rootView.findViewById(R.id.date_TextView);
        contentsInput = rootView.findViewById(R.id.contentsInput);
        sectionTextView = rootView.findViewById(R.id.subject_title);

        

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "nMode "+mMode);
                Log.d(TAG, "AppConstants.MODE_INSERT "+mMode);
                if(mMode == AppConstants.MODE_INSERT) {
                    saveMemo();
                } else if(mMode == AppConstants.MODE_MODIFY) {
                    modifyMemo();
                }

                if (listener != null) {
                    listener.onTabSelected(0);//첫번째 탭으로 이동
                }
            }
        });

        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMemo();

                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });

    }
    public void setItem(Memo item) {
        this.item = item;
    }
    public void setDateString(String dateString) { dateTextView.setText(dateString); }
    public void setSectionTextView(String sectionString){sectionTextView.setText(sectionString); }
    public void setContents(String contentsString) {
        contentsInput.setText(contentsString);
    }


    public void applyItem() {
        AppConstants.println("applyItem called.");

        if (item != null) {
            mMode = AppConstants.MODE_MODIFY;

            setSectionTextView(item.getMEMO_SUBJECT());
            setDateString(item.getCREATE_DATE());
            setContents(item.getMEMO_CONTENTS());

        } else {

            Date currentDate = new Date();
            String currentDateString = AppConstants.dateFormat3.format(currentDate);
            setDateString(currentDateString);

            contentsInput.setText("");
        }

    }
    /**
     * 데이터베이스 레코드 추가
     */
    private void saveMemo() {
        String memo_subject = sectionTextView.getText().toString();
        String contents = contentsInput.getText().toString();

        String sql = "insert into " + MemoDatabase.TABLE_MEMO +
                "(MEMO_SUBJECT, MEMO_CONTENTS,MEMO_COLOR,MEMO_FAVORITE) values(" +
                "'"+ memo_subject + "', " +
                "'"+ contents + "', " +
                "'"+ "" + "', " +
                "'"+ "" + "' " + ")";

        Log.d(TAG, "sql : " + sql);
        MemoDatabase database = MemoDatabase.getInstance(context);
        database.execSQL(sql);

    }
    /**
     * 데이터베이스 레코드 수정
     */
    private void modifyMemo() {
        if (item != null) {
            String memo_subject = sectionTextView.getText().toString();
            String contents = contentsInput.getText().toString();


            // update note
            String sql = "update " + MemoDatabase.TABLE_MEMO +
                    " set " +
                    "   MEMO_SUBJECT = '" + memo_subject + "'" +
                    "   ,MEMO_CONTENTS = '" + contents + "'" +
                    "   ,MEMO_COLOR = '" + "" + "'" +
                    "   ,MEMO_FAVORITE = '" + "" + "'" +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }

    /**
     * 레코드 삭제
     */
    private void deleteMemo() {
        AppConstants.println("deleteNote called.");

        if (item != null) {
            // delete note
            String sql = "delete from " + MemoDatabase.TABLE_MEMO +
                    " where " +
                    "   _id = " + item._id;

            Log.d(TAG, "sql : " + sql);
            MemoDatabase database = MemoDatabase.getInstance(context);
            database.execSQL(sql);
        }
    }
}
