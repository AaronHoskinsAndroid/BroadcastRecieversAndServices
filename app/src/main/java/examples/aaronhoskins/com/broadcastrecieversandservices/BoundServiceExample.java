package examples.aaronhoskins.com.broadcastrecieversandservices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class BoundServiceExample extends Service {
    ArrayList<Integer> integerArrayList;
    IBinder binder;

    public BoundServiceExample() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        integerArrayList = new ArrayList<>();
        binder = new MyBinder();
        Log.d(TAG, "onCreate: SERVICE CREATED");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: BOUND TO SERVICE"  );
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: UNBOUND FROM SERVICE");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service DESTROYED");
        super.onDestroy();
    }

    public ArrayList<Integer> getData() {
        Log.d(TAG, "getData: SENDING DATA TO CALLER");
        return integerArrayList;
    }

    public void initData(int amountOfNumbersToAdd) {
        Log.d(TAG, "initData: INITIALIZING LIST");
        for(int i = 0 ; i < amountOfNumbersToAdd ; i++) {
            integerArrayList.add(new Random().nextInt());
        }
    }

    public void addNumberToList(int numToAdd) {
        integerArrayList.add(numToAdd);
    }

    public class MyBinder extends Binder {
        public BoundServiceExample getService() {
            return BoundServiceExample.this;
        }
    }
}
