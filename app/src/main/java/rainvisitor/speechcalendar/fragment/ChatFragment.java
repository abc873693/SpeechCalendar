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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.base.BaseFragment;
import rainvisitor.speechcalendar.libs.DB;
import rainvisitor.speechcalendar.model.Author;
import rainvisitor.speechcalendar.model.DBItem;
import rainvisitor.speechcalendar.model.Message;

public class ChatFragment extends BaseFragment {


    @BindView(R.id.messagesList)
    MessagesList messagesList;
    Unbinder unbinder;
    private View view;

    private Author assistant, user;
    public MessagesListAdapter<Message> adapter;

    public List<Message> messages = new ArrayList<>();

    private String userID = "11111";
    private String assistantID = "99999";

    private String Word;

    public ChatFragment() {

    }

    public void setWord(String Word) {
        if (Word == null) {
            return;
        }
        this.Word = Word;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        restoreArgs(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (!Word.equals(""))
            getDB().insert(DB.CHAT_TABLE_NAME, new DBItem(Integer.valueOf(userID), Word, new Date().getTime()));
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
        assistant = new Author(assistantID, "Assistant", "Assistant");
        user = new Author(userID, "user", "user");
        messages = parseMessageData(getDB().getAll(DB.CHAT_TABLE_NAME));
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
        adapter = new MessagesListAdapter<>(userID, holdersConfig, imageLoader);
        adapter.addToEnd(messages, false);
        messagesList.setAdapter(adapter);
    }

    private List<Message> parseMessageData(List<DBItem> chatList) {
        List<Message> messageList = new ArrayList<>();
        for (int i = chatList.size() - 1; i >= 0; i--) {
            DBItem dbItem = chatList.get(i);
            if ((dbItem.getID() + "").equals(userID)) {
                messageList.add(new Message(userID + "", dbItem.getText(), user, new Date(dbItem.getAddTime())));
            } else if ((dbItem.getID() + "").equals(assistantID)) {
                messageList.add(new Message(assistantID + "", dbItem.getText(), assistant, new Date(dbItem.getAddTime())));
            }
        }
        return messageList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
