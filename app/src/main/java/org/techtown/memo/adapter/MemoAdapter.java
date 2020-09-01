package org.techtown.memo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.memo.Activity.MainActivity;
import org.techtown.memo.Memo;
import org.techtown.memo.OnMemoItemClickListener;
import org.techtown.memo.R;

import java.util.ArrayList;


//adapter 는 RecyclerView 를 설정하고 뿌려주기 위해 사용한다.
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>
implements OnMemoItemClickListener {
    //OnMemoItemClickListener >>  메모클릭했을대 수정하는 곳으로 이동하는 listener
    private ArrayList<Memo> items = new ArrayList<>();
    private OnMemoItemClickListener listener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //memo_item.xml을 입력하기 위해
        View itemView = inflater.inflate(R.layout.memo_item,viewGroup,false);

        return new ViewHolder(itemView,this);
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

    public Memo getItem(int position){ return items.get(position); }
    public void setItem(int position,Memo item){
        items.set(position,item);
    }


    public void setOnItemClickListener(OnMemoItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder ViewHolder, View view, int position) {
     if (listener != null) {
            listener.onItemClick(ViewHolder, view, position);
        }
    }

    @Override
    public void favoriteClick(Memo item, View view, int position) {
        if (listener != null) {
            listener.favoriteClick(item, view,position);
        }
    }

    @Override
    public void paletteClick(Memo item, View view, int position) {
        if (listener != null) {
            listener.paletteClick(item, view,position);
        }
    }

    @Override
    public void deleteClick(Memo item, View view, int position) {
        if (listener != null) {
            listener.deleteClick(item, view,position);
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView memo_date;
        TextView memo_subject;
        TextView memo_contents;
        ImageView memo_favorite;
        ImageView memo_palette;
        ImageView memo_delete;
        LinearLayout linearLayout;


        public ViewHolder(final View itemView, final OnMemoItemClickListener listener) {


            super(itemView);

            memo_date = itemView.findViewById(R.id.memo_date);
            memo_subject = itemView.findViewById(R.id.memo_subject);
            memo_contents = itemView.findViewById(R.id.memo_contents);
            memo_palette = itemView.findViewById(R.id.memo_palette);//클릭시 컬러 픽커 나타나고 컬러 선택시 저장하는 메서드만들어야함
            memo_favorite = itemView.findViewById(R.id.memo_favorite);//데이터 베이스에 memo_favorite 변경 메서드
            memo_delete = itemView.findViewById(R.id.memo_delete);//삭제 메서드
            linearLayout = itemView.findViewById(R.id.linearLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
            memo_favorite.setOnClickListener(new View.OnClickListener(){



                @Override
                public  void onClick(View view){
                    //cardView 에 할당된 번호 가져오기
                    int position = getAdapterPosition();
                    Toast.makeText(view.getContext(),
                            position+"번째 좋아요 클릭됨",
                            Toast.LENGTH_SHORT).show();
                    if(position != RecyclerView.NO_POSITION){
                             Memo item =    items.get(position);

                        if (listener != null) {
                            listener.favoriteClick(item, view, position);
                        }
                    }

                }
            });

            memo_palette.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View view){
                    //cardView 에 할당된 번호 가져오기
                    int position = getAdapterPosition();
                    Toast.makeText(view.getContext(),
                            position + "번째 팔레트 클릭됨",
                            Toast.LENGTH_SHORT).show();
                    if(position != RecyclerView.NO_POSITION) {
                        Memo item = items.get(position);
                        if (listener != null) {
                            listener.paletteClick(item, view, position);
                        }
                    }
                }
            });

            memo_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cardView 에 할당된 번호 가져오기
                    int position = getAdapterPosition();
                    Toast.makeText(view.getContext(),
                            position + "번째 삭제 클릭됨",
                            Toast.LENGTH_SHORT).show();
                    if(position != RecyclerView.NO_POSITION) {
                        Memo item = items.get(position);
                        if (listener != null) {
                            listener.deleteClick(item, view, position);
                        }

                    }
                }
            });
        }
        public void setItem(Memo item){
            memo_date.setText(item.getCREATE_DATE());
            memo_subject.setText(item.getMEMO_SUBJECT());
            memo_contents.setText(item.getMEMO_CONTENTS());
            memo_palette.setBackgroundColor(item.getMEMO_COLOR());
            linearLayout.setBackgroundColor(item.getMEMO_COLOR());
            if( item.getMEMO_FAVORITE().equals("Y")){
                memo_favorite.setImageResource(R.drawable.baseline_star_black_18dp);
            }else{
                memo_favorite.setImageResource(R.drawable.baseline_star_border_black_18dp);
            }

        }









}

}





