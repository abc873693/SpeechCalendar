package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rainvisitor.speechcalendar.model.Dictionary;
import rainvisitor.speechcalendar.callback.ThingCallback;

/**
 * Created by Ray on 2017/9/22.
 */

public class ThingHelper {

    private static final String TAG = "ThingHelper";

    public static int position = 0;

    public static int state = 0;

    public static final int wait = 0;
    public static final int analysisIsArrange = 1;
    public static final int analysisIsTimeAndPurpose = 2;
    public static final int receiveVoice = 3;
    public static final int analysisIsHardwareControl = 4;
    public static final int hardwareControl = 5;
    public static final int hardwareControlStroke = 6;
    public static final int addSchedule = 7;
    public static final int unKnownCommand = 8;

    private static final String device = "device";
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

    private static final String[] to = {"到", "至"};

    private static final String[] open = {"打開", "開啟", "開"};
    private static final String[] close = {"關閉", "關"};

    private static final String[] hardwareList = {"可調燈","調光燈","層板燈", "電燈", "冷氣", "電視"};

    public static List<String> oldWords = null;

    public static List<String> words;
    public static List<Dictionary> dictionaryList;
    public static List<Dictionary> dictionaryHasMeanList;
    public static ThingCallback thingCallback;

    private static boolean flagTime = false;
    private static boolean flagThing = false;
    private static boolean isHardwareControl = false;

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
                if (position != -1) position = 0;
                ThingHelper.words = words;
                ThingHelper.dictionaryList = dictionaryList;
                ThingHelper.thingCallback = thingCallback;
                while (state != 0) {
                    switch (state) {
                        case wait:
                            Log.d(TAG, "wait");
                            break;
                        case analysisIsArrange:
                            Log.d(TAG, "analysisIsArrange");
                            isHardwareControl = false;
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
                        case unKnownCommand:
                            callUnKnownCommand(context);
                            state = wait;
                            break;
                    }
                }
            }
        }).start();
    }

    private static void callUnKnownCommand(Context context) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                thingCallback.unKnownCommand(words);
            }
        });
    }

    private static void analysisHardwareControl() {
        List<Integer> values = new ArrayList<>();
        for (Dictionary dictionary : ThingHelper.dictionaryList) {
            if (dictionary.getFlag().equals(verb)) {
                int value = isMeanControl(dictionary.getWord());
                if (value != -1) {
                    values.add(value);
                    dictionaryHasMeanList.add(dictionary);
                }
            }
        }
        if (values.size() == 0) {
            state = unKnownCommand;
            return;
        }
        Log.e("value", " " + values.toString());
        List<String> hardware = new ArrayList<>();
        for (Dictionary dictionary : ThingHelper.dictionaryList) {
            if (dictionary.getFlag().equals(device)) {
                hardware.add(dictionary.getWord());
                dictionaryHasMeanList.add(dictionary);
            }
        }
        if (hardware.size() == 0) {
            state = unKnownCommand;
            return;
        }
        Log.e("hardware", hardware.toString());
        if (hardware.size() == values.size()) {
            deviceName = hardware.get(0);
            deviceValue = values.get(0);
            isHardwareControl = true;
            if (state != analysisIsTimeAndPurpose) state = hardwareControl;
        } else if (values.size() == 1) {
            for (int i = 0; i < hardware.size(); i++) {
                thingCallback.hardwareControl(hardware.get(i), values.get(0));
            }
            deviceName = hardware.get(0);
            deviceValue = values.get(0);
            isHardwareControl = true;
            if (state != analysisIsTimeAndPurpose) state = hardwareControl;
        } else {
            if (state != analysisIsTimeAndPurpose)
                state = unKnownCommand;
        }
    }

    private static int errorAnalysisTimeAndPurpose = 0;

    private static void analysisTimeAndPurpose() {
        int flagTo = -1;
        if (!flagTime) {
            List<Dictionary> times = new ArrayList<>();
            for (Dictionary dictionary : ThingHelper.dictionaryList) {
                if (dictionary.getFlag().equals(time) || dictionary.getFlag().equals(mTime)) {
                    times.add(dictionary);
                    dictionaryHasMeanList.add(dictionary);
                }
                if (isMeanTo(dictionary.getWord())) {
                    flagTo = times.size();
                }
            }
            if (times.size() > 0) {
                Log.d("to", flagTo + "");
                int error = 0;
                if (flagTo != -1) {
                    Date dateStart = parseTime(times.subList(0, flagTo), null);
                    if (state == hardwareControl) return;
                    if (dateStart == null) error++;
                    Date dateEnd = parseTime(times.subList(flagTo, times.size()), dateStart);
                    if (state == hardwareControl) return;
                    if (dateStart == null) error++;
                    Log.d("date", Utils.ConvertTime(dateStart) + " " + Utils.ConvertTime(dateEnd));
                    Log.d("date", times.subList(0, flagTo) + " " + times.subList(flagTo, times.size()));
                    timeStart = dateStart;
                    timeEnd = dateEnd;
                } else {
                    Date date = parseTime(times, null);
                    if (state == hardwareControl) return;
                    Log.d("date", Utils.ConvertTime(date));
                    if (date == null) error++;
                    timeStart = date;
                }
                if (error == 0)
                    flagTime = true;
            }
        }
        if (!flagThing) {
            dictionaryList.removeAll(ThingHelper.dictionaryHasMeanList);
            Log.e("unMean", dictionaryList.toString());
            if (dictionaryList.size() > 0) {
                flagThing = true;
                thing = "";
                analysisHardwareControl();
                if (isHardwareControl) {
                    for (Dictionary dictionary : dictionaryList) {
                        thing = String.format("%s%s", thing, dictionary.getWord());
                    }
                } else if (thing.equals("")) {
                    flagTime = false;
                }
            }
        }
        Log.d("AnalysisTimeAndPurpose", flagThing + " " + flagTime);
        if (flagThing && flagTime) {
            if (isHardwareControl) {
                state = hardwareControlStroke;
            } else {
                state = addSchedule;
            }
        } else {
            errorAnalysisTimeAndPurpose++;
            if (errorAnalysisTimeAndPurpose > 5) {
                state = unKnownCommand;
                return;
            }
            state = receiveVoice;
            if (!flagThing && flagTime) {
                state = unKnownCommand;
            } else if (!flagTime) {
                thingCallback.needVoice("請問你要預約幾點?");
            } else if (!flagThing) {
                thingCallback.needVoice("請問你想做甚麼?");
            }
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
        Log.d("analysisIsArrange", "result dictionaryHasMeanList size = " + dictionaryHasMeanList.size());
        if (dictionaryHasMeanList.size() == 0)
            state = analysisIsHardwareControl;
    }

    private static boolean isMeanPlan(String string) {
        for (String s : plans) {
            if (s.equals(string))
                return true;
        }
        return false;
    }

    private static boolean isMeanTo(String string) {
        for (String s : to) {
            if (s.equals(string))
                return true;
        }
        return false;
    }

    private static int isMeanControl(String string) {
        for (String s : open) {
            if (s.equals(string))
                return 1;
        }
        for (String s : close) {
            if (s.equals(string))
                return 0;
        }
        return -1;
    }

    private static List<String> getHardwareKey(String string) {
        List<String> strings = new ArrayList<>();
        for (String s : hardwareList) {
            if (string.contains(s))
                strings.add(s);
        }
        return strings;
    }

    private static Date parseTime(List<Dictionary> times, Date date) {
        int error = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) calendar.setTime(date);
        boolean flagFirstChangeTime = false;
        List<String> specialTime = new ArrayList<>();
        if (times.size() == 1) {
            if (times.get(0).getWord().equals("現在") && times.get(0).getFlag().equals(ThingHelper.time)) {
                state = hardwareControl;
                return new Date();
            }
        }
        for (int i = 0; i < times.size(); i++) {
            Dictionary time = times.get(i);
            if (time.getFlag().equals(ThingHelper.time)) {
                specialTime.add(time.getWord());
            } else if (time.getFlag().equals(ThingHelper.mTime)) {
                if (time.getWord().length() == 1) {
                    continue;
                }
                String strNumber = time.getWord().substring(0, time.getWord().length() - 1);
                int number;
                try {
                    number = Integer.parseInt(strNumber);
                } catch (Exception e) {
                    number = Utils.convertChineseNumber(time.getWord().substring(0, time.getWord().length() - 1));
                }
                if (number == -1) {
                    error++;
                    continue;
                }
                if (!flagFirstChangeTime) {
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    flagFirstChangeTime = true;
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
        boolean flagHasDate = false, flagHasTime = false;
        for (String string : specialTime) {
            switch (string) {
                case "早上":
                case "下午":
                case "中午":
                case "晚上":
                case "傍晚":
                case "半夜":
                    if (flagHasDate) return null;
                    flagHasDate = true;
                    break;
                case "今天":
                case "明天":
                case "後天":
                case "週日":
                case "週末":
                case "星期日":
                case "週一":
                case "星期一":
                case "週二":
                case "星期二":
                case "週三":
                case "星期三":
                case "週四":
                case "星期四":
                case "週五":
                case "星期五":
                case "週六":
                case "星期六":
                    if (flagHasTime) return null;
                    flagHasTime = true;
                    break;
            }
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
                case "今天":
                    break;
                case "明天":
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    break;
                case "後天":
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 2);
                    break;
                case "週日":
                case "週末":
                case "星期日":
                    if (calendar.get(Calendar.DAY_OF_WEEK) > 1) {
                        calendar.set(Calendar.DAY_OF_WEEK, 7);
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
                    } else calendar.set(Calendar.DAY_OF_WEEK, 1);
                    break;
                case "週一":
                case "星期一":
                    calendar.set(Calendar.DAY_OF_WEEK, 2);
                    break;
                case "週二":
                case "星期二":
                    calendar.set(Calendar.DAY_OF_WEEK, 3);
                    break;
                case "週三":
                case "星期三":
                    calendar.set(Calendar.DAY_OF_WEEK, 4);
                    break;
                case "週四":
                case "星期四":
                    calendar.set(Calendar.DAY_OF_WEEK, 5);
                    break;
                case "週五":
                case "星期五":
                    calendar.set(Calendar.DAY_OF_WEEK, 6);
                    break;
                case "週六":
                case "星期六":
                    calendar.set(Calendar.DAY_OF_WEEK, 7);
                    break;
            }
        }
        Log.e("error", error + "");
        return calendar.getTime();
    }
}
