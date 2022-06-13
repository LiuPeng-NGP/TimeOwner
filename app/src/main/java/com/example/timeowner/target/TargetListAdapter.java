package com.example.timeowner.target;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Target列表适配器
 */

public class TargetListAdapter extends RecyclerView.Adapter<TargetListAdapter.TextHolder> {

    private final Context mContext;
    private final List<String> mList;//name
    private final List<String> SList;//start
    private final List<String> EList;//end
    private final List<Integer> mDay;

    TargetListAdapter(Context context, List<Map<String, Object>> list, CalendarDay date) {
        this.mContext = context;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        List<String> list1 = new ArrayList<>();//targetName
        List<String> list2 = new ArrayList<>();//Start
        List<String> list3 = new ArrayList<>();//End
        List<Integer> list4 = new ArrayList<>();//mDay
        for (int i = 0; i < list.size(); i++){
            Map<String,Object> map = list.get(i);
            String name = map.get("TargetName").toString();
            String start = map.get("Start").toString();
            String end = map.get("End").toString();
            //算mday
            int m = 0;
            try {
                date1 = sdf.parse(end);
            }catch (ParseException e){
                e.printStackTrace();
            }
            date2 = date.getCalendar().getTime();
            long diff = date1.getTime() - date2.getTime();
            m = (int)diff;
            list1.add(name);
            list2.add(start);
            list3.add(end);
            list4.add(m);
        }
        this.mList = list1;
        this.SList = list2;
        this.EList = list3;
        this.mDay = list4;
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
