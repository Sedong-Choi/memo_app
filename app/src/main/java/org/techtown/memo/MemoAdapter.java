package org.techtown.memo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


//adapter 는 RecyclerView 를 설정하고 뿌려주기 위해 사용한다.
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>
implements  OnMemoItemClickListener{
    //MemoItemClickListener >>  메모클릭했을대 수정하는 곳으로 이동하는 listener
    ArrayList<Memo> items = new ArrayList<Memo>();
    OnMemoItemClickListener listener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //memo_item.xml을 입력하기 위해
        View itemView = inflater.inflate(R.layout.memo_item,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {
        Memo item = items.get(position); // ArrayList<Memo> 에서 순서 별로 삽입하기위해
        viewholder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Memo item){
        items.add(item);
    }

    public void setItems(ArrayList<Memo> items){
        this.items= items;
    }

    public Memo getItem(int position){
        return items.get(position);
    }

    public void setItem(int position,Memo item){
        items.set(position,item);
    }


    @Override
    public void onItemClick(ViewHolder viewholder, View view, int position) {
     if (listener != null) {
            listener.onItemClick(viewholder, view, position);
        }
    }

    public void setOnItemClickListener(OnMemoItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView memo_date;
        TextView memo_subject;
        TextView memo_contents;
        ImageView memo_favorite;
        ImageView memo_palette;
        ImageView memo_delete;

        public ViewHolder(View itemView){
            super(itemView);
            memo_date = itemView.findViewById(R.id.memo_date);
            memo_subject = itemView.findViewById(R.id.memo_subject);
            memo_contents =itemView.findViewById(R.id.memo_contents);
            memo_palette = itemView.findViewById(R.id.memo_palette);//클릭시 컬러 픽커 나타나고 컬러 선택시 저장하는 메서드만들어야함
            memo_favorite =itemView.findViewById(R.id.memo_favorite);//데이터 베이스에 memo_favorite 변경 메서드
            memo_delete = itemView.findViewById(R.id.memo_delete);//삭제 메서드
        }

        public void setItem(Memo item){
            memo_date.setText(item.getCREATE_DATE());
            memo_subject.setText(item.getMEMO_SUBJECT());
            memo_contents.setText(item.getMEMO_CONTENTS());
            if( item.getMEMO_FAVORITE().equals("Y")){
                memo_favorite.setImageResource(R.drawable.baseline_star_black_18dp);
            }else{
                memo_favorite.setImageResource(R.drawable.baseline_star_border_black_18dp);
            }

        }



    }





}
