package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.support.annotation.NonNull;
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

    public static String deviceConvertToTopic(String name) {
        switch (name) {
            case "冷氣":
                return MQTTHelper.TOPIC_AIR_CONDITIONER;
            case "電視":
                return MQTTHelper.TOPIC_TV;
            case "可調燈":
            case "調光燈":
                return MQTTHelper.TOPIC_LIGHT_DIMMING;
            case "層板燈":
            case "電燈":
                return MQTTHelper.TOPIC_LIGHT_SWITCH;
            default:
                return null;
        }
    }

    public static String deviceConvertToReserveTopic(String name) {
        switch (name) {
            case "冷氣":
                return MQTTHelper.TOPIC_RESERVE_AIR_CONDITIONER;
            case "電視":
                return MQTTHelper.TOPIC_RESERVE_TV;
            case "可調燈":
            case "調光燈":
                return MQTTHelper.TOPIC_RESERVE_LIGHT_DIMMING;
            case "層板燈":
            case "電燈":
                return MQTTHelper.TOPIC_RESERVE_LIGHT_SWITCH;
            default:
                return null;
        }
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.format(strCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static String ConvertTime(long strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return format.format(strCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
     *
     * @param chineseNumber
     * @return result
     * @author 雪见烟寒
     */
    @SuppressWarnings("unused")
    public static int convertChineseNumber(@NonNull String chineseNumber) {
        try {
            int result = 0;
            int temp = 1;//存放一个单位的数字如：十万
            int count = 0;//判断是否有chArr
            char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
            char[] chArr = new char[]{'十', '百', '千', '萬', '億'};
            for (int i = 0; i < chineseNumber.length(); i++) {
                boolean b = true;//判断是否是chArr
                char c = chineseNumber.charAt(i);
                for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                    if (c == cnArr[j]) {
                        if (0 != count) {//添加下一个单位之前，先把上一个单位值添加到结果中
                            result += temp;
                            temp = 1;
                            count = 0;
                        }
                        // 下标+1，就是对应的值
                        temp = j + 1;
                        b = false;
                        break;
                    }
                }
                if (b) {//单位{'十','百','千','万','亿'}
                    for (int j = 0; j < chArr.length; j++) {
                        if (c == chArr[j]) {
                            switch (j) {
                                case 0:
                                    temp *= 10;
                                    break;
                                case 1:
                                    temp *= 100;
                                    break;
                                case 2:
                                    temp *= 1000;
                                    break;
                                case 3:
                                    temp *= 10000;
                                    break;
                                case 4:
                                    temp *= 100000000;
                                    break;
                                default:
                                    break;
                            }
                            count++;
                        }
                    }
                }
                if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                    result += temp;
                }
            }
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
}
