package com.cvika.mobv.nfcpaymentsimulator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.cvika.mobv.nfcpaymentsimulator.MainActivity;

public class LogoutService extends Service {
    public LogoutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LogoutService.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                LogoutService.this.stopSelf();
            }
        }, 20000);

        return super.onStartCommand(intent, flags, startId);
    }
}
