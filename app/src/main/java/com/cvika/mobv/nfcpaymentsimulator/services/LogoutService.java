package com.cvika.mobv.nfcpaymentsimulator.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.cvika.mobv.nfcpaymentsimulator.MainActivity;
import com.cvika.mobv.nfcpaymentsimulator.fragments.MerchandiseFragment;

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
        int timeToLogout = 180;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.remove(MerchandiseFragment.LOG_TAG);
                editor.commit();
                Intent i = new Intent(LogoutService.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                LogoutService.this.stopSelf();
            }
        }, 1000*timeToLogout);

        return super.onStartCommand(intent, flags, startId);
    }
}
