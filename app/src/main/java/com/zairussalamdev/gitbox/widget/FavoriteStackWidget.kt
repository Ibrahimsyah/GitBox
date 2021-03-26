package com.zairussalamdev.gitbox.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.zairussalamdev.gitbox.R

/**
 * Implementation of App Widget functionality.
 */
class FavoriteStackWidget : AppWidgetProvider() {
    companion object {
        const val WIDGET_UPDATE = "com.zairussalamdev.gitbox.WIDGET_UPDATE"

        private fun updateAppWidget(
                context: Context,
                appWidgetManager: AppWidgetManager,
                appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_stack_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val intentAction = Intent(context, FavoriteStackWidget::class.java)
            intentAction.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentAction,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == WIDGET_UPDATE) {
                Log.d("hehe", "DataSetWillBeChanged")
                val widgetManager = AppWidgetManager.getInstance(context)
                val widgetIds =
                        widgetManager.getAppWidgetIds(ComponentName(context, FavoriteStackWidget::class.java))
                widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stack_view)
            }
        }
    }

    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}