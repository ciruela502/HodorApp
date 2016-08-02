package pl.droidsonroids.hodor.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import pl.droidsonroids.hodor.HodorApplication;
import pl.droidsonroids.hodor.R;
import pl.droidsonroids.hodor.model.User;
import pl.droidsonroids.hodor.retrofit.RestAdapter;
import pl.droidsonroids.hodor.util.DatabaseHelper;

public class AnswearService extends Service {

    private DatabaseHelper mDatabaseHelper;
    private RestAdapter mRestAdapter;

    public AnswearService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException(getString(R.string.on_bind));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mDatabaseHelper = HodorApplication.getInstance().getDatabaseHelper();
        mRestAdapter = HodorApplication.getInstance().getRestAdapter();

        String userName = intent.getStringExtra(getString(R.string.token));
        sendToUserToken(userName);
        return super.onStartCommand(intent, flags, startId);
    }


    public void sendToUserToken(String userName) {
        mDatabaseHelper.getUserFromDatabase(userName, new DatabaseHelper.OnUserReceivedListener() {
            @Override
            public void onUserReceived(User user) {
                mRestAdapter.sendPush(user.getToken());
            }
        });
    }
}

