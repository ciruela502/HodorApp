package pl.droidsonroids.hodor.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import pl.droidsonroids.hodor.HodorApplication;
import pl.droidsonroids.hodor.util.DatabaseHelper;

/**
 * Created by marta on 02.08.2016.
 */
public class HodorInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "instanceInService";
    private DatabaseHelper mDatabaseHelper;

    public HodorInstanceIdService() {
        this.mDatabaseHelper = HodorApplication.getInstance().getDatabaseHelper();
    }

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        mDatabaseHelper.updateUserToken(refreshedToken);
    }
}
