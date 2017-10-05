package rainvisitor.speechcalendar.base;

import android.support.v4.app.Fragment;

import rainvisitor.speechcalendar.libs.DB;

/**
 * Created by Ray on 2017/9/22.
 */

public class BaseFragment extends Fragment {

    public DB getDB(){
       return  ((BaseApplication)(getActivity().getApplicationContext())).getDb();
    }
}
