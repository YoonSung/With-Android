package gaongil.safereturnhome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.MessageType;
import gaongil.safereturnhome.adapter.TimeLineAdapter;


@EFragment(R.layout.drawer_main_right)
public class MainRightDrawerFragment extends Fragment {

    private TimeLineAdapter mTimeLineAdapter;

    @ViewById(R.id.drawer_main_right_listview)
    ListView mRightDrawerListView;

    @AfterViews
    void init() {
        //TODO DELETE
        //Test Start
        ArrayList<MessageData> testList = new ArrayList<MessageData>();
        testList.add(new MessageData(0, 3, "경로를 벗어났습니다. 관심을 가져주세요", new Date(1,1,1, 21, 02),  MessageType.WARN, true ));
        testList.add(new MessageData(0, 1, "충격이 감지되었습니다 !!", new Date(1,1,1, 20, 51),  MessageType.URGENT, true ));
        testList.add(new MessageData(0, 4, "(집)으로 귀가를 시작했습니다.", new Date(1,1,1, 18, 30),  MessageType.ANNOUNCE, true ));
        testList.add(new MessageData(0, 2, "상태가 (외로움) 으로 변경되었습니다.", new Date(1, 1, 1, 17, 11),  MessageType.NORMAL, true ));
        testList.add(new MessageData(0, 4, "이제 슬슬 집으로 가봐야겠다~ 다들 오늘 몇시에 들어와?.", new Date(1, 1, 1, 16, 55),  MessageType.NORMAL, true ));
        //Test End

        this.mTimeLineAdapter = new TimeLineAdapter(getActivity(), testList);

        mRightDrawerListView.setAdapter(mTimeLineAdapter);
        mRightDrawerListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mRightDrawerListView.setStackFromBottom(false);
    }

}
