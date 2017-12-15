package rainvisitor.speechcalendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.base.BaseFragment;
import rainvisitor.speechcalendar.callback.GeneralCallback;
import rainvisitor.speechcalendar.model.Message;

public class ChatFragment extends BaseFragment {

    @BindView(R.id.messagesList)
    MessagesList messagesList;
    Unbinder unbinder;
    private View view;

    public MessagesListAdapter<Message> adapter;

    public List<Message> messages = new ArrayList<>();

    public ChatFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        restoreArgs(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setView();
        getBaseApplication().setChatCallback(new GeneralCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                setView();
            }
        });
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
        messages = getDB().messageDao().getAll();
        for (Message message : messages) {
            if (message.getId().equals(getBaseApplication().getUser().getId()))
                message.setAuthor(getBaseApplication().getUser());
            if (message.getId().equals(getBaseApplication().getAssistant().getId()))
                message.setAuthor(getBaseApplication().getAssistant());
        }
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                if (url.equals("Assistant")) {
                    Picasso.with(getActivity()).load(R.mipmap.ic_launcher).into(imageView);
                } /*else if (url.equals("user")) {
                    Picasso.with(QAndAActivity.this).load(R.drawable.ic_user_chat).into(imageView);
                }*/
            }

        };
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        adapter = new MessagesListAdapter<>(getBaseApplication().getUser().getId(), holdersConfig, imageLoader);
        adapter.addToEnd(messages, true);
        messagesList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getBaseApplication().setChatCallback(null);
    }
}
