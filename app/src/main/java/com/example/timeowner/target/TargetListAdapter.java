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

    TargetListAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TextHolder extends RecyclerView.ViewHolder {

        TextView textView;

        TextHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
