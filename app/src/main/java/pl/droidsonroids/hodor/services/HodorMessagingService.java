package pl.droidsonroids.hodor.services;

import android.app.Notification;
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

    private String userName;
    int mCode = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Uri sound = getUri();
        userName = remoteMessage.getData().get(getString(R.string.username));
        PendingIntent mPendingIntent = getPendingIntent();
        PendingIntent mPendingIntentAnswer = getPendingIntentAnswer();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(userName)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentText(getString(R.string.hodor))
                        .setContentIntent(mPendingIntent)
                        .addAction(R.drawable.ic_plus_white_36dp, getString(R.string.answer), mPendingIntentAnswer)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setSound(sound);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mCode, mBuilder.build());
    }

    public Uri getUri() {
        String mStringBuilder = getString(R.string.firstPartUri) +
                getPackageName() +
                getString(R.string.secondPartUri) +
                R.raw.hodor2;
        return Uri.parse(mStringBuilder);
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
