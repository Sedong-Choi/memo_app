package org.techtown.memo;

import android.view.View;

public interface OnMemoItemClickListener {
   public void onItemClick(MemoAdapter.ViewHolder ViewHolder, View view, int position);
}
