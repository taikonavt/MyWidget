package com.example.mywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidget extends AppWidgetProvider {

    private final static String ExtraMsg = "msg";
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    private static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        // Здесь обновим текст, будем показывать номер виджета
        views.setTextViewText(R.id.appwidget_text, String.format("%s - %d", widgetText, appWidgetId));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        Intent active = new Intent(context, MyWidget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        active.putExtra("msg", "Hello World");

        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);

        views.setOnClickPendingIntent(R.id.widget_button, actionPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (ACTION_WIDGET_RECEIVER.equals(action)){
            String masg = "null";
            try{
                masg = intent.getStringExtra("msg");
            } catch (NullPointerException e){
                Log.e("Error", "msg = null");
            }
            Toast.makeText(context, masg, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
