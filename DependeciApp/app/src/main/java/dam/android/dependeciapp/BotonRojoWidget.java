package dam.android.dependeciapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class BotonRojoWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.boton_rojo_widget);
        ComponentName thisWidget = new ComponentName(context, BotonRojoWidget.class);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.boton_rojo);
        remoteViews.setImageViewBitmap(R.id.imButton,icon);
        remoteViews.setOnClickPendingIntent(R.id.imButton, getPendingSelfIntent(context, "id"));

        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

//        ComponentName thisWidget = new ComponentName(context, BotonRojoWidget.class);
//        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//        for (int id : allWidgetIds) {
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.boton_rojo_widget);
//
//            remoteViews.setOnClickPendingIntent(R.id.imButton, getPendingSelfIntent(context, "id"));
//
//
//            appWidgetManager.updateAppWidget(id, remoteViews);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "onEnabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "Disabled", Toast.LENGTH_LONG).show();
    }

    public void prueba(View v) {
        Toast.makeText(v.getContext(), "OOOOLEEEE", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

            Bundle extras = intent.getExtras();
            if (extras != null) {
                int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);

                if (appWidgetIds != null && appWidgetIds.length > 0) {
                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
                }
            }


    }
    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, context.getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}

