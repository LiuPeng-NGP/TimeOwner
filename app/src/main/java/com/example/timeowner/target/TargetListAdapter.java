package com.example.timeowner.target;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;

import java.util.List;

/**
 * 列表适配器
 */

public class TargetListAdapter extends RecyclerView.Adapter<TargetListAdapter.TextHolder> {

    private final Context mContext;
    private final List<String> mList;
    private final List<String> mDay;

    TargetListAdapter(Context context, List<String> list, List<String> mDay) {
        mContext = context;
        mList = list;
        this.mDay = mDay;
    }

    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.target_item_list, parent, false);
        return new TextHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextHolder holder, int position) {
        holder.textView.setText(mList.get(position));
        holder.textView_day.setText(mDay.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class TextHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textView_day;

        TextHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            textView_day = itemView.findViewById(R.id.tv_day);
        }
    }
}
