package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.adapter.ChoiceAdapter;
import rainvisitor.speechcalendar.model.Choice;

/**
 * Created by Ray on 2017/9/22.
 */

public class Utils {

    public static AlertDialog createChoiceDialog(Activity activity, String title, List<Choice> choiceList, ChoiceAdapter.OnItemClickListener listener) {
        final View item = LayoutInflater.from(activity).inflate(R.layout.dialog_choice, null);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setView(item).create();
        item.setBackgroundColor(ContextCompat.getColor(activity, R.color.dialog_background));
        ((TextView) item.findViewById(R.id.textView_title)).setText(title);
        RecyclerView recyclerView = item.findViewById(R.id.recycleView_choice);
        recyclerView.setAdapter(new ChoiceAdapter(activity, choiceList, alertDialog, listener));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        return alertDialog;
    }

    public static List<Choice> CovertWord(List<String> Words) {
        List<Choice> choices = new ArrayList<>();
        for (String s : Words) {
            choices.add(new Choice(s));
        }
        return choices;
    }

    //ex 2017-03-27 01:12:36
    public static Date ConvertTime(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.getDefault());
        try {
            return format.parse(strCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    //ex 2017-03-27 01:12:36
    public static String ConvertTime(Date strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.getDefault());
        try {
            return format.format(strCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
