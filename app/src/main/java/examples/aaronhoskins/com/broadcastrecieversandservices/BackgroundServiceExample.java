package examples.aaronhoskins.com.broadcastrecieversandservices;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class BackgroundServiceExample extends Service {
    public BackgroundServiceExample() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        double random = (new Random().nextDouble() * 10 + 1) % 10;
        EventBus.getDefault().post(new ServiceMessageEvent(random));
        Bundle bundle = new Bundle();
        bundle.putString("key", String.valueOf(random));
        Intent intent1 = new Intent("receive");
        intent1.putExtras(bundle);

        sendBroadcast(intent1);
        return super.onStartCommand(intent, flags, startId);
    }
}
