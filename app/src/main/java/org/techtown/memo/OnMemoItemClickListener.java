package org.techtown.memo;

import android.view.View;

public interface OnMemoItemClickListener {
   void onItemClick(MemoAdapter.ViewHolder ViewHolder, View view, int position);
   void favoriteClick(Memo item, View view, int position);
   void paletteClick(Memo item, View view, int position);
   void deleteClick(Memo item, View view, int position);
}
