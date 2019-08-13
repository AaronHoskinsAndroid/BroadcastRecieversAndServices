package examples.aaronhoskins.com.broadcastrecieversandservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Receiver receiver;
    IntentFilter intentFilterForReciever;
    BoundServiceExample boundServiceExample;
    TextView tvDisplay;
    boolean isBound = false;
    Button btnBind;
    Button btnInit;
    Button btnAdd;
    Button btnUnBind;
    EditText etNumToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new Receiver();
        intentFilterForReciever = new IntentFilter();
        intentFilterForReciever.addAction("receive");
        tvDisplay = findViewById(R.id.tvDisplay);
        btnBind = findViewById(R.id.btnBindToService);
        btnInit = findViewById(R.id.btnInitList);
        btnAdd = findViewById(R.id.btnAddList);
        btnUnBind = findViewById(R.id.btnUnbindService);
        etNumToAdd = findViewById(R.id.etNumToAdd);
        toggleVisibities();
    }

    private void toggleVisibities() {
        btnBind.setVisibility(isBound ? View.GONE : View.VISIBLE);
        btnInit.setVisibility(!isBound ? View.GONE : View.VISIBLE) ;
        btnAdd.setVisibility(!isBound ? View.GONE : View.VISIBLE) ;
        btnUnBind.setVisibility(!isBound ? View.GONE : View.VISIBLE) ;
        etNumToAdd.setVisibility(!isBound ? View.GONE : View.VISIBLE) ;
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
        //Intent intent = new Intent(this, BackgroundServiceExample.class);
        //startService(intent);

        Intent intentServiceIntent = new Intent(this, IntentServiceExampleTwo.class);
        intentServiceIntent.setAction(IntentServiceExampleTwo.ACTION_MUL_NUM);
        intentServiceIntent.putExtra(IntentServiceExampleTwo.EXTRA_NUMBER_ONE, "45");
        intentServiceIntent.putExtra(IntentServiceExampleTwo.EXTRA_NUMBER_TWO, "100");
        startService(intentServiceIntent);
    }

    public void onClickBoundService(View view) {
        switch(view.getId()) {
            case R.id.btnBindToService:
                Intent boundServiceIntent = new Intent(this, BoundServiceExample.class);
                bindService(boundServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnInitList:
                boundServiceExample.initData(10);
                break;
            case R.id.btnAddList:
                String valueEntered = etNumToAdd.getText().toString();
                boundServiceExample.addNumberToList(Integer.parseInt(valueEntered));
                ArrayList<Integer> numList = boundServiceExample.getData();
                for(Integer i : numList) {
                    Log.d("TAG", "onClickBoundService: " + i);
                }
                break;
            case R.id.btnUnbindService:
                unbindService(serviceConnection);
                isBound = false;
                toggleVisibities();
                break;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("TAG", "onServiceConnected: Connect to service....");
            BoundServiceExample.MyBinder myBinder = (BoundServiceExample.MyBinder)iBinder;
            boundServiceExample = myBinder.getService();
            isBound = true;
            toggleVisibities();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("TAG", "onServiceDisconnected: DISCONNECTING SERVICE");
            isBound = false;
            toggleVisibities();
        }
    };



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
