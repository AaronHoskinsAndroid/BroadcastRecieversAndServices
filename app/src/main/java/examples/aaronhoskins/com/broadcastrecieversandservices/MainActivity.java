package examples.aaronhoskins.com.broadcastrecieversandservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Receiver receiver;
    IntentFilter intentFilterForReciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new Receiver();
        intentFilterForReciever = new IntentFilter();
        intentFilterForReciever.addAction("receive");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString("key", "Hello People it is friday");
        Intent intent = new Intent("receive");
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, intentFilterForReciever);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle passedBundle = intent.getExtras();
            String passedMsg = passedBundle.getString("key");
            Log.d("Tag", passedMsg);
        }
    }
}
