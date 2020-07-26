package org.techtown.memo;

import android.view.View;

public interface OnMemoItemClickListener {
    void onItemClick(MemoAdapter.ViewHolder ViewHolder, View view, int position);
}
