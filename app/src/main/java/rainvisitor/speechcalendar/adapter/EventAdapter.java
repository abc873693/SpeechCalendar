package rainvisitor.speechcalendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.model.Event;

import static rainvisitor.speechcalendar.libs.Utils.ConvertTime;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Event item, int position);
    }

    private final List<Event> contactList;
    private final OnItemClickListener listener;
    private Context context;

    public EventAdapter(Context context, List<Event> contactList, OnItemClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_event, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.e("contactList",contactList.get(position).toString());
        holder.textViewContent.setText(contactList.get(position).getText());
        holder.textViewTime.setText(ConvertTime(contactList.get(position).getTime()));
        if (contactList.get(position).getStatus() == Event.Status.NORMAL.ordinal()) {
            holder.textViewStatus.setText("尚未處理");
        } else if (contactList.get(position).getStatus() == Event.Status.FINISHED.ordinal()) {
            holder.textViewStatus.setText("已完成");
            holder.layoutRoot.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.event_finished)));

        } else if (contactList.get(position).getStatus() == Event.Status.ERROR.ordinal()) {
            holder.textViewStatus.setText("錯誤");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(contactList.get(position), position);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent;
        TextView textViewStatus;
        TextView textViewTime;
        LinearLayout layoutRoot;
        View mView;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewContent = itemLayoutView.findViewById(R.id.textView_content);
            textViewStatus = itemLayoutView.findViewById(R.id.textView_status);
            textViewTime = itemLayoutView.findViewById(R.id.textView_time);
            layoutRoot = itemLayoutView.findViewById(R.id.layout_root);
            mView = itemLayoutView;
        }
    }
}
