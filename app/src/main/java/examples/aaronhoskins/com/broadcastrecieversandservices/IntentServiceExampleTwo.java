package examples.aaronhoskins.com.broadcastrecieversandservices;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class IntentServiceExampleTwo extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_ADD_NUM = "examples.aaronhoskins.com.broadcastrecieversandservices.action.ADD";
    public static final String ACTION_MUL_NUM = "examples.aaronhoskins.com.broadcastrecieversandservices.action.MUL";

    // TODO: Rename parameters
    public static final String EXTRA_NUMBER_ONE = "examples.aaronhoskins.com.broadcastrecieversandservices.extra.NUM_1";
    public static final String EXTRA_NUMBER_TWO = "examples.aaronhoskins.com.broadcastrecieversandservices.extra.NUM_2";

    public IntentServiceExampleTwo() {
        super("IntentServiceExampleTwo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String param1 = intent.getStringExtra(EXTRA_NUMBER_ONE);
            final String param2 = intent.getStringExtra(EXTRA_NUMBER_TWO);
            if (ACTION_ADD_NUM.equals(action)) {
                handleActionAdd(param1, param2);
            } else if (ACTION_MUL_NUM.equals(action)) {
                handleActionMul(param1, param2);
            }
        }
    }
    private void handleActionAdd(String param1, String param2) {
        double value = Double.parseDouble(param1) + Double.parseDouble(param2);
        EventBus.getDefault().post(new ServiceMessageEvent(value));
    }


    private void handleActionMul(String param1, String param2) {
        double value = Double.parseDouble(param1) * Double.parseDouble(param2);
        EventBus.getDefault().post(new ServiceMessageEvent(value));
    }
}
