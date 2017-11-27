package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rainvisitor.speechcalendar.api.models.Dictionary;
import rainvisitor.speechcalendar.callback.ThingCallback;

/**
 * Created by Ray on 2017/9/22.
 */

public class ThingHelper {

    private static final String TAG = "ThingHelper";

    public static int state = 0;

    public static final int wait = 0;
    public static final int analysisIsArrange = 1;
    public static final int analysisIsTimeAndPurpose = 2;
    public static final int receiveVoice = 3;
    public static final int analysisIsHardwareControl = 4;
    public static final int hardwareControl = 5;
    public static final int hardwareControlStroke = 6;
    public static final int addSchedule = 7;

    private static final String verb = "v";
    private static final String time = "t";  //時間
    private static final String mTime = "m";  //時間
    private static final String space = "s";
    private static final String noun = "n";
    private static final String place = "ns";
    private static final String quantity = "q";
    private static final String quantityVerb = "qv";
    private static final String quantityTime = "qt";
    private static final String prepositional = "p";

    private static final String[] plans = {"幫", "安排", "新增", "預約"};

    private static final String[] open = {"打開", "開啟", "開"};
    private static final String[] close = {"關閉", "關"};

    public static List<String> oldWords = null;

    public static List<String> words;
    public static List<Dictionary> dictionaryList;
    public static List<Dictionary> dictionaryHasMeanList;
    public static ThingCallback thingCallback;

    private static boolean flagTime = false;
    private static boolean flagThing = false;

    private static String deviceName = null;
    private static int deviceValue = -1;
    private static String thing = null;
    private static Date timeStart = null;
    private static Date timeEnd = null;
    private static String location = null;

    public static void send(final Context context, final List<String> words, final List<Dictionary> dictionaryList, final ThingCallback thingCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThingHelper.words = words;
                ThingHelper.dictionaryList = dictionaryList;
                ThingHelper.thingCallback = thingCallback;
                while (state != 0) {
                    switch (state) {
                        case wait:
                            thingCallback.unKnownCommand(words);
                            break;
                        case analysisIsArrange:
                            Log.d(TAG, "analysisIsArrange");
                            analysisIsArrange();
                            break;
                        case analysisIsHardwareControl:
                            Log.d(TAG, "analysisIsHardwareControl");
                            analysisHardwareControl();
                            break;
                        case receiveVoice:
                            //Log.d(TAG, "receiveVoice");
                            break;
                        case analysisIsTimeAndPurpose:
                            Log.d(TAG, "analysisIsTimeAndPurpose");
                            analysisTimeAndPurpose();
                            break;
                        case hardwareControl:
                            Log.d(TAG, "hardwareControl " + deviceName + " " + deviceValue);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    thingCallback.hardwareControl(deviceName, deviceValue);
                                }
                            });
                            state = wait;
                            break;
                        case hardwareControlStroke:
                            Log.d(TAG, "hardwareControlStroke");
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    thingCallback.hardwareStroke(deviceName, deviceValue, timeStart);
                                }
                            });
                            state = wait;
                            break;
                        case addSchedule:
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, String.format("addSchedule %s %s %s %s ", thing, timeStart, timeEnd, location));
                                    thingCallback.createCalender(thing, timeStart, timeEnd, location);
                                }
                            });
                            state = wait;
                            break;
                    }
                }
            }
        }).start();
    }

    private static void analysisHardwareControl() {
        state = wait;
    }

    private static int errorAnalysisTimeAndPurpose = 0;

    private static void analysisTimeAndPurpose() {
        if (!flagTime) {
            List<Dictionary> times = new ArrayList<>();
            for (Dictionary dictionary : ThingHelper.dictionaryList) {
                if (dictionary.getFlag().equals(time) || dictionary.getFlag().equals(mTime)) {
                    times.add(dictionary);
                    dictionaryHasMeanList.add(dictionary);
                }
            }
            if (times.size() > 0) {
                Date date = parseTime(times);
                Log.d("date", Utils.ConvertTime(date));
                flagTime = true;
                timeStart = date;
            }
        }
        if (!flagThing) {
            dictionaryList.removeAll(ThingHelper.dictionaryHasMeanList);
            if (dictionaryList.size() > 0) {
                flagThing = true;
                thing = "";
                for (Dictionary dictionary : dictionaryList) {
                    thing = String.format("%s%s", thing, dictionary.getWord());
                }
            }
        }
        if (flagThing && flagTime) {
            state = addSchedule;
        } else {
            errorAnalysisTimeAndPurpose++;
            Log.d("AnalysisTimeAndPurpose", flagThing + " " + flagTime);
            if (errorAnalysisTimeAndPurpose > 5) {
                state = wait;
            }
            state = receiveVoice;
            thingCallback.needVoice(flagThing, flagTime);
        }
    }

    private static void analysisIsArrange() {
        dictionaryHasMeanList = new ArrayList<>();
        flagTime = false;
        flagThing = false;
        for (Dictionary dictionary : ThingHelper.dictionaryList) {
            if (dictionary.getFlag().equals(verb)) {
                if (isMeanPlan(dictionary.getWord())) {
                    dictionaryHasMeanList.add(dictionary);
                    state = analysisIsTimeAndPurpose;
                }
            }
        }
        if (dictionaryHasMeanList.size() == 0)
            state = analysisIsHardwareControl;
    }

    private static boolean isMeanPlan(String string) {
        for (String plan : plans) {
            if (plan.equals(string))
                return true;
        }
        return false;
    }

    private static Date parseTime(List<Dictionary> times) {
        int error = 0;
        Calendar calendar = Calendar.getInstance();
        List<String> specialTime = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            Dictionary time = times.get(i);
            if (time.getFlag().equals(ThingHelper.time)) {
                specialTime.add(time.getWord());
            } else if (time.getFlag().equals(ThingHelper.mTime)) {
                if (time.getWord().length() == 1) {
                    continue;
                }
                int number = Utils.convertChineseNumber(time.getWord().substring(0, time.getWord().length() - 1));
                if (number == -1) {
                    error++;
                    continue;
                }
                switch (time.getWord().substring(time.getWord().length() - 1, time.getWord().length())) {
                    case "年":
                        calendar.set(Calendar.YEAR, number);
                        break;
                    case "月":
                        calendar.set(Calendar.MONTH, number);
                        break;
                    case "日":
                        calendar.set(Calendar.DAY_OF_MONTH, number);
                        break;
                    case "時":
                    case "點":
                        if (number <= 12)
                            calendar.set(Calendar.HOUR_OF_DAY, number);
                        else calendar.set(Calendar.HOUR, number);
                        break;
                    case "分":
                        calendar.set(Calendar.MINUTE, number);
                        break;
                    case "秒":
                        calendar.set(Calendar.SECOND, number);
                        break;
                    default:
                        error++;
                        break;
                }
            } else {
                error++;
            }
        }
        for (String string : specialTime) {
            switch (string) {
                case "早上":
                    if (calendar.get(Calendar.HOUR_OF_DAY) > 12)
                        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 12);
                    break;
                case "下午":
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 12)
                        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 12);
                    break;
                case "中午":
                    break;
                case "晚上":
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 12)
                        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 12);
                    break;
                case "傍晚":
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 12)
                        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 12);
                    break;
                case "半夜":
                    break;
            }
        }
        Log.e("error", error + "");
        return calendar.getTime();
    }
}
