package com.zairussalamdev.gitbox.widget

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.database.GitBoxDatabase
import com.zairussalamdev.gitbox.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class StackWidgetFactory(private val context: Context) :
        RemoteViewsService.RemoteViewsFactory {
    private var userItems = listOf<User>()
    private lateinit var userDao: UserDao

    override fun onCreate() {
        userDao = GitBoxDatabase.getInstance(context).userDao()
        fetchUserData()
    }

    override fun onDataSetChanged() {
        fetchUserData()
    }

    override fun onDestroy() {}

    override fun getCount(): Int = userItems.size

    override fun getViewAt(position: Int): RemoteViews? {
        if (userItems.isEmpty()) return null
        val user = userItems[position]
        val rv = RemoteViews(context.packageName, R.layout.stack_widget_item)
        rv.setTextViewText(R.id.user_username, user.username)
        rv.setTextViewText(R.id.user_url, user.url)
        val imageLoader = ImageLoader(context)
        runBlocking {
            val request = ImageRequest.Builder(context)
                    .data(user.avatar)
                    .transformations(CircleCropTransformation())
                    .target {
                        val bitmap = (it as BitmapDrawable).bitmap
                        rv.setImageViewBitmap(R.id.user_image, bitmap)
                    }
                    .build()
            imageLoader.execute(request)
        }
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    private fun fetchUserData() {
        runBlocking {
            launch(Dispatchers.IO) {
                val users = userDao.getAllAsList()
                userItems = users
            }
        }
    }
}