package gaongil.safereturnhome.scene;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.util.ArrayList;
import java.util.Date;
import gaongil.safereturnhome.R;
import gaongil.safereturnhome.adapter.ChatAdapter;
import gaongil.safereturnhome.fragment.*;
import gaongil.safereturnhome.support.*;
import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.MessageType;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_chat)
public class ChatActivity extends FragmentActivity {

    /***********************************************************************
     * Forground Check
     */
    private static boolean VISIBILITY = false;

    public static boolean isVisible() {
        return ChatActivity.VISIBILITY;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatActivity.VISIBILITY = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatActivity.VISIBILITY = false;
    }
    /**
     * Forground Check
     *************************************************************************/

    /**
     * Drawer
     */
    @ViewById(R.id.drawer_chat_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.drawer_chat_left)
    View leftDrawerLayout;

    @ViewById(R.id.drawer_chat_right)
    View rightDrawerLayout;

    @ViewById(R.id.chat_toolbar_title)
    TextView chatRoomTitle;

    @ViewById(R.id.chat_toolbar_left_toggle)
    ImageButton leftDrawerToggle;

    @ViewById(R.id.chat_toolbar_right_toggle)
    ImageButton rightDrawerToggle;

    /**
     * MainContents
     */
    // Message List
    private ArrayList<MessageData> mMessageList;

    // Chat Custom Adapter.
    private ChatAdapter mChatAdapter;

    @ViewById(R.id.chat_list)
    ListView chatList;

    @ViewById(R.id.chat_edt)
    EditText userInput;

    @ViewById(R.id.chat_btn_send)
    Button send;

    // Control Keyboard Panel
    @SystemService
    InputMethodManager inputMethodManager;

    @AfterViews
    void init() {
        setupDrawer("Test Test Test Test Test Test Test Test");
        loadMessageList();

        //TODO DELETE
        //Test Start
        setupTest();
        //TODO DELETE
        //Test End
    }

    @Click(R.id.chat_toolbar_left_toggle)
    void leftToggle(View v) {
        drawerLayout.closeDrawer(rightDrawerLayout);
        drawerLayout.openDrawer(leftDrawerLayout);
    }

    @Click(R.id.chat_toolbar_right_toggle)
    void rightToggle(View v) {
        drawerLayout.closeDrawer(leftDrawerLayout);
        drawerLayout.openDrawer(rightDrawerLayout);
    }

    @Click(R.id.chat_btn_send)
    void sendMessage() {
        if (userInput.length() == 0)
            return;

        inputMethodManager.hideSoftInputFromWindow(userInput.getWindowToken(), 0);

        //TODO getGroupId & UserId & MessageType & etc..
        MessageData newMessage = new MessageData(
                1,
                1,
                userInput.getText().toString(),
                new Date(),
                MessageType.NORMAL,
                false
        );

        //TODO Post Message, and Send Success, then add chatList
        mMessageList.add(newMessage);

        mChatAdapter.notifyDataSetChanged();
        userInput.setText(null);
    }

    private void setupDrawer(String title) {
        chatRoomTitle.setText(title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_chat_left, new ChatLeftDrawerFragment_());
        fragmentTransaction.replace(R.id.drawer_chat_right, new ChatRightDrawerFragment_());
        fragmentTransaction.commit();

        DrawerLayout.DrawerListener drawerListener = new DrawableListener(findViewById(R.id.chat_content_layout), drawerLayout, leftDrawerLayout, rightDrawerLayout);
        drawerLayout.setDrawerListener(drawerListener);
    }

    private void loadMessageList() {
        //Get Data
        // TODO getDataFrom Server or Local DB
        mMessageList = new ArrayList<MessageData>();

        mMessageList.add(new MessageData(
                1, 1, "test", new Date(), MessageType.NORMAL, true
        ));


        //Data Set
        mChatAdapter = new ChatAdapter(this, mMessageList);
        chatList.setAdapter(mChatAdapter);
        chatList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatList.setStackFromBottom(true);
    }

    //TODO DELETE
    //Test Start
    private void setupTest() {
        mMessageList.add(new MessageData(0, 0, "[귀가시작] 집까지 같이해 주세요.위치정보, 센서정보 공유가 시작되었습니다.\n", new Date(1, 1, 1, 20, 51), MessageType.ANNOUNCE, true));
        mMessageList.add(new MessageData(0, 0, "[충격 위협감지] 갑작스러운\n 충격이 감지되었습니다.\n위치 : 성남시 분당구 삼평동 봇들공원\n !무슨일인지 확인해주세요!", new Date(1, 1, 1, 21, 03), MessageType.URGENT, true));
        mMessageList.add(new MessageData(0, 1, "무슨일이야??", new Date(1, 1, 1, 21, 03), MessageType.NORMAL, false));
        mMessageList.add(new MessageData(0, 4, "내가 전화해볼게", new Date(1, 1, 1, 21, 04), MessageType.ANNOUNCE, false));
        mMessageList.add(new MessageData(0, 0, "아.. 괜찮아! 딴데보다가 살짝 넘어졌어ㅠ", new Date(1, 1, 1, 21, 04), MessageType.NORMAL, true));
        mMessageList.add(new MessageData(0, 3, "으이구.. 괜찮아?\n데릴러 나갈게", new Date(1, 1, 1, 21, 04), MessageType.NORMAL, false));
        mMessageList.add(new MessageData(0, 0, "응응! 고마워어ㅠㅠㅠ", new Date(1, 1, 1, 21, 05), MessageType.NORMAL, true));

        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.activity_stop, R.anim.activity_sliding_down);
    };
}
