package examples.aaronhoskins.com.broadcastrecieversandservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Receiver receiver;
    IntentFilter intentFilterForReciever;
    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new Receiver();
        intentFilterForReciever = new IntentFilter();
        intentFilterForReciever.addAction("receive");
        tvDisplay = findViewById(R.id.tvDisplay);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString("key", "Hello People it is friday");
        Intent intent = new Intent("receive");
        intent.putExtras(bundle);
        //sendBroadcast(intent);//Standard with no restrictions
        //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);//Using LocalBroadcast Manager
        sendBroadcast(intent, "broadcast.permission");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, intentFilterForReciever);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayServiceReturn(ServiceMessageEvent serviceMessageEvent) {
        tvDisplay.setText(String.valueOf(serviceMessageEvent.getRandomNumber()));
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, BackgroundServiceExample.class);
        startService(intent);
    }

    public class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle passedBundle = intent.getExtras();
            String passedMsg = passedBundle.getString("key");
            Log.d("Tag", passedMsg);
            tvDisplay.setText(passedMsg);
        }
    }
}
