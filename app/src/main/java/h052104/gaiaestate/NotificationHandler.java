package h052104.gaiaestate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {

    private String CHANNEL_ID = "CH1";
    private NotificationManager manager;
    private Context mContext;

    public NotificationHandler(Context ctx){
        mContext = ctx;
        manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Gaia Estate", NotificationManager.IMPORTANCE_LOW);
        ch.setDescription("Notifications from Gaia Estate.");
        ch.enableLights(true);
        ch.enableVibration(true);
        this.manager.createNotificationChannel(ch);
    }

    public void send(String message){
        Intent i = new Intent(mContext, BrowserActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentTitle("Gaia Estate")
                .setContentText(message)
                .setSmallIcon(R.drawable.gaia_estate_logo)
                .setContentIntent(pi);
        this.manager.notify(0, builder.build());
    }
}
