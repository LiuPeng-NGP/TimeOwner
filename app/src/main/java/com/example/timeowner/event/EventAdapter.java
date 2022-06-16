package com.example.timeowner.event;
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
import com.example.timeowner.dbconnect.DBConnectEvent;
import com.example.timeowner.object.Event;
import java.util.ArrayList;
public class EventAdapter extends RecyclerView.Adapter<com.example.timeowner.event.EventAdapter.myViewHolder> {
    private final Context context;
    private final ArrayList<Event> eventArrayList;
    public EventAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public com.example.timeowner.event.EventAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.event_item, null);
        return new com.example.timeowner.event.EventAdapter.myViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull com.example.timeowner.event.EventAdapter.myViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.mEventName.setText(event.getEventName());
    }
    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }
    //自定义viewhodler
    class myViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        private final TextView mEventName;
        public myViewHolder(View itemView) {
            super(itemView);
            mEventName = itemView.findViewById(R.id.event_name);
        }
    }

}
