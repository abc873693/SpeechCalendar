package rainvisitor.speechcalendar.base;

import android.support.v4.app.Fragment;

import java.util.List;

import rainvisitor.speechcalendar.libs.DB;
import rainvisitor.speechcalendar.model.RoomItem;

/**
 * Created by Ray on 2017/9/22.
 */

public class BaseFragment extends Fragment {

    public DB getDB(){
       return  ((BaseApplication)(getActivity().getApplicationContext())).getDb();
    }

    public List<RoomItem> getRoomItemList(){
        return  ((BaseApplication)(getActivity().getApplicationContext())).getRoomItemList();
    }
}
