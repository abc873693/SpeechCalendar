package rainvisitor.speechcalendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.base.BaseFragment;

public class EventFragment extends BaseFragment {


    private View view;

    public EventFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.fragment_event, container, false);
        restoreArgs(savedInstanceState);
        initView();
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

    private void initView() {
    }

    private void setView() {
    }
}
