package pl.droidsonroids.hodor.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import pl.droidsonroids.hodor.R;
import pl.droidsonroids.hodor.ui.LoginActivity;

/**
 * Created by marta on 02.08.2016.
 */
public class HodorMessagingService extends FirebaseMessagingService {

    private PendingIntent mPendingIntent;
    private PendingIntent mPendingIntentAnwser;
    private String userName;
    int mCode = 001;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Uri sound = getUri();
        userName = remoteMessage.getData().get(getString(R.string.username));
        mPendingIntent = getPendingIntent();
        mPendingIntentAnwser = getPendingIntentAnswer();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(userName)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentText(getString(R.string.hodor))
                        .setContentIntent(mPendingIntent)
                        .addAction(R.drawable.ic_plus_white_36dp, getString(R.string.answer), mPendingIntentAnwser)
                        .setPriority(1000)
                        .setAutoCancel(true)
                        .setSound(sound);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mCode, mBuilder.build());
    }

    public Uri getUri() {
        StringBuilder mStringBuilder = new StringBuilder();
        mStringBuilder.append(getString(R.string.firstPartUri))
                .append(getPackageName())
                .append(getString(R.string.secondPartUri))
                .append(R.raw.hodor2);
        return Uri.parse(mStringBuilder.toString());
    }

    public PendingIntent getPendingIntent() {
        Intent resultIntent = new Intent(this, LoginActivity.class);
        return PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent getPendingIntentAnswer() {
        Intent resultIntent = new Intent(this, AnswearService.class);
        resultIntent.putExtra(getString(R.string.token),userName);
        return PendingIntent.getService(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
