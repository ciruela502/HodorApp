package pl.droidsonroids.hodor.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import pl.droidsonroids.hodor.HodorApplication;
import pl.droidsonroids.hodor.util.DatabaseHelper;

/**
 * Created by marta on 02.08.2016.
 */
public class HodorInstanceIdService extends FirebaseInstanceIdService {

    private DatabaseHelper mDatabaseHelper;

    public HodorInstanceIdService() {
        this.mDatabaseHelper = HodorApplication.getInstance().getDatabaseHelper();
    }

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        mDatabaseHelper.updateUserToken(refreshedToken);
    }
}
