package rainvisitor.speechcalendar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rainvisitor.speechcalendar.adapter.ChoiceAdapter;
import rainvisitor.speechcalendar.api.Helper;
import rainvisitor.speechcalendar.api.models.Dictionary;
import rainvisitor.speechcalendar.callback.DictionariesCallback;
import rainvisitor.speechcalendar.callback.ThingCallback;
import rainvisitor.speechcalendar.fragment.ChatFragment;
import rainvisitor.speechcalendar.fragment.EventFragment;
import rainvisitor.speechcalendar.fragment.RoomFragment;
import rainvisitor.speechcalendar.libs.ThingHelper;
import rainvisitor.speechcalendar.libs.Utils;
import rainvisitor.speechcalendar.model.Choice;

import static rainvisitor.speechcalendar.libs.ThingHelper.analysisIsTimeAndPurpose;
import static rainvisitor.speechcalendar.libs.ThingHelper.receiveVoice;
import static rainvisitor.speechcalendar.libs.ThingHelper.state;
import static rainvisitor.speechcalendar.libs.ThingHelper.wait;

public class MainActivity extends AppCompatActivity {

    private static final int TAG_FRAGMENT_CHAT = 0;
    private static final int TAG_FRAGMENT_ROOM = 1;
    private static final int TAG_FRAGMENT_EVENT = 2;

    private final int REQUEST_VOICE_RECOGNIZE = 1;

    private String Word = "";

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @BindView(R.id.fab)
    FloatingActionButton fab_call_mic;
    @BindView(R.id.content_main)
    CoordinatorLayout contentMain;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        manager = getSupportFragmentManager();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                changeTab(tabId);
            }
        });
    }

    private void changeTab(int tabId) {
        transaction = manager.beginTransaction();
        switch (tabId) {
            case R.id.tab_chat:
                ChatFragment chatFragment = new ChatFragment();
                chatFragment.setWord(Word);
                transaction.replace(R.id.content_main, chatFragment, TAG_FRAGMENT_CHAT + "");
                break;
            case R.id.tab_room:
                RoomFragment verifiedFragment = new RoomFragment();
                transaction.replace(R.id.content_main, verifiedFragment, TAG_FRAGMENT_ROOM + "");
                break;
            case R.id.tab_event:
                EventFragment recordFragment = new EventFragment();
                transaction.replace(R.id.content_main, recordFragment, TAG_FRAGMENT_EVENT + "");
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VOICE_RECOGNIZE) {
            if (resultCode == RESULT_OK) {
                //把所有辨識的可能結果印出來看一看，第一筆是最 match 的。
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String all = "";
                for (String r : result) {
                    all = all + r + "\n";
                }
                if (result.size() > 0) {
                    Word = result.get(0);
                    changeTab(R.id.tab_chat);
                    searchWord(result);
                    Word = "";
                }
                Log.d("resultCode", all);
            }
        }
    }

    private void searchWord(final ArrayList<String> Words) {
        Log.d("searchWord", Words.get(0));
        Helper.get(this, Words.get(0), new DictionariesCallback() {
            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
                e.printStackTrace();
            }

            @Override
            public void onSuccess(String word, List<Dictionary> response) {
                super.onSuccess(word, response);
                Log.e("onSuccess", response.toString());
                if (state == wait) {
                    thingWork(Words, response);
                } else if (state == receiveVoice) {
                    ThingHelper.oldWords = ThingHelper.words;
                    ThingHelper.words = Words;
                    ThingHelper.dictionaryList = response;
                    state = analysisIsTimeAndPurpose;
                }
            }
        });
    }

    private void thingWork(List<String> Words, List<Dictionary> response) {
        state = ThingHelper.analysisIsArrange;
        ThingHelper.send(this, Words, response, new ThingCallback() {
            @Override
            public void unKnownCommand(List<String> commands) {
                super.unKnownCommand(commands);
                Log.d("ThingHelper", "unKnownCommand");
                Utils.createChoiceDialog(MainActivity.this, "無法解析 請問您是哪種意思?", Utils.CovertWord(commands), new ChoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Choice item, int position) {

                    }
                }).show();
            }

            @Override
            public void createCalender(String title, Date startDate, Date endDate, String location) {
                super.createCalender(title, startDate, endDate, location);
                Intent calendarIntent =
                        new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                if (startDate != null)
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTime());
                if (endDate != null)
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.getTime());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                calendarIntent.putExtra(CalendarContract.Events.TITLE, title);
                if (location != null)
                    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                try {
                    startActivity(calendarIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, R.string.calender_app_not_found, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void needVoice(Boolean flagThing, Boolean flagTime) {
                super.needVoice(flagThing, flagTime);
                if(!flagThing){
                    openSpeaker("請問你是想做甚麼事情?");
                }else if(!flagTime){
                    openSpeaker("請問你要預約幾點?");
                }
            }
        });
    }

    @OnClick({R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //openSpeaker("請說話...");
                ArrayList<String> result = new ArrayList<>();
                result.add("幫我新增跟研究生開會");
                searchWord(result);
                break;
        }
    }

    private void openSpeaker(String message) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說話..."); //語音辨識 Dialog 上要顯示的提示文字
        startActivityForResult(intent, REQUEST_VOICE_RECOGNIZE);
    }
}
