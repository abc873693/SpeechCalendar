package rainvisitor.speechcalendar.base;

import android.support.v4.app.Fragment;

import java.util.List;

import rainvisitor.speechcalendar.libs.AppDatabase;
import rainvisitor.speechcalendar.model.RoomItem;

/**
 * Created by Ray on 2017/9/22.
 */

public class BaseFragment extends Fragment {

    public AppDatabase getDB() {
        return ((BaseActivity) (getActivity())).getBaseApplication().getDB();
    }

    public BaseApplication getBaseApplication() {
        return ((BaseActivity) (getActivity())).getBaseApplication();
    }

    public List<RoomItem> getRoomItemList() {
        return ((BaseActivity) (getActivity())).getBaseApplication().getRoomItemList();
    }
}
