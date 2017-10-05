package rainvisitor.speechcalendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.adapter.EventAdapter;
import rainvisitor.speechcalendar.base.BaseFragment;
import rainvisitor.speechcalendar.libs.DB;
import rainvisitor.speechcalendar.model.DBItem;
import rainvisitor.speechcalendar.model.Event;

public class EventFragment extends BaseFragment {


    @BindView(R.id.recycleView_event)
    RecyclerView recycleViewEvent;
    Unbinder unbinder;

    private EventAdapter eventAdapter;

    private List<Event> eventList;

    private View view;

    public EventFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_event, container, false);
        restoreArgs(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void restoreArgs(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setView() {
       /* Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 10, 4, 18, 0);
        getDB().insert(DB.EVENT_TABLE_NAME, new DBItem(0, "開啟 層板燈", new Date().getTime(), calendar.getTimeInMillis(), Event.Status.FINISHED.ordinal()));
        calendar.set(2017, 10, 10, 8, 0);
        getDB().insert(DB.EVENT_TABLE_NAME, new DBItem(1, "關閉 冷氣", new Date().getTime(), calendar.getTimeInMillis(), Event.Status.NORMAL.ordinal()));*/
        eventList = parseEventData(getDB().getAll(DB.EVENT_TABLE_NAME));
        eventAdapter = new EventAdapter(getActivity(), eventList, new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item, int position) {

            }
        });
        recycleViewEvent.setAdapter(eventAdapter);
        recycleViewEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private List<Event> parseEventData(List<DBItem> eventList) {
        List<Event> events = new ArrayList<>();
        for (int i = eventList.size() - 1; i >= 0; i--) {
            DBItem dbItem = eventList.get(i);
            events.add(new Event(dbItem.getID(), dbItem.getText(), new Date(dbItem.getAddTime()), new Date(dbItem.getTime()), dbItem.getStatus()));
        }
        return events;
    }
}
