package com.example.timeowner.target;

import static com.example.timeowner.target.TargetMainActivity.UPDATE_TEXT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeowner.R;
import com.example.timeowner.dbconnect.DBConnectTarget;
import com.example.timeowner.object.Target;

import java.util.ArrayList;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.myViewHolder> {

    private final Context context;

    private final ArrayList<Target> targetArrayList;

    public TargetAdapter(Context context, ArrayList<Target> targetArrayList) {
        this.context = context;
        this.targetArrayList = targetArrayList;
    }





    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType==)
        View itemView = View.inflate(context, R.layout.target_item, null);
        return new myViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Target target = targetArrayList.get(position);
        holder.mCheckBox.setText(target.getTargetName());
//        holder.mTargetCountTextView.setText(String.valueOf(target.getTargetCount()));
        if(target.getTargetIsCompleted()>0){
            holder.mCheckBox.setChecked(true);
        }else{
            holder.mCheckBox.setChecked(false);
        }

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(holder.mCheckBox.isChecked()==true){
//                    targetArrayList.get(holder.getAdapterPosition()).setTargetIsCompleted(1);
//                    targetArrayList.get(holder.getAdapterPosition()).setTargetCount(targetArrayList.get(holder.getAdapterPosition()).getTargetCount()+1);
//                    holder.mTargetCountTextView.setText(String.valueOf(Integer.valueOf(holder.mTargetCountTextView.getText().toString())+1));
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DBConnectTarget dbConnectTarget = new DBConnectTarget();
//                            dbConnectTarget.update(targetArrayList.get(holder.getAdapterPosition()));
//
//                        }
//                    }).start();
//                }else {
//                    targetArrayList.get(holder.getAdapterPosition()).setTargetIsCompleted(0);
//                    targetArrayList.get(holder.getAdapterPosition()).setTargetCount(targetArrayList.get(holder.getAdapterPosition()).getTargetCount()-1);
//                    holder.mTargetCountTextView.setText(String.valueOf(Integer.valueOf(holder.mTargetCountTextView.getText().toString())-1));
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DBConnectTarget dbConnectTarget = new DBConnectTarget();
//                            dbConnectTarget.update(targetArrayList.get(holder.getAdapterPosition()));
//
//                        }
//                    }).start();
//
//                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return targetArrayList.size();
    }


    //自定义viewhodler
    class myViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})

        private final CheckBox mCheckBox;
        private final TextView mTargetCountTextView;



        public myViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.target_checkbox);
            mTargetCountTextView = itemView.findViewById(R.id.target_text_view);
        }
    }


}
