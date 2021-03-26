package com.zairussalamdev.gitbox.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackWidgetFactory(this.applicationContext)
    }
}