package rainvisitor.speechcalendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.model.Choice;

/**
 * Created by Ray on 2017/9/30.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Choice item, int position);
    }

    private final List<Choice> contactList;
    private final OnItemClickListener listener;
    private Context context;
    private AlertDialog alertDialog;

    public ChoiceAdapter(Context context, List<Choice> contactList, AlertDialog alertDialog, OnItemClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
        this.context = context;
        this.alertDialog = alertDialog;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_choice_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textView_title.setText(contactList.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(contactList.get(position), position);
                alertDialog.cancel();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title;
        View mView;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView_title = itemLayoutView.findViewById(R.id.textView_title);
            mView = itemLayoutView;
        }
    }
}
