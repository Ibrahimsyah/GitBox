package com.zairussalamdev.gitbox.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.services.notification.AlarmReceiver

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.title_activity_settings)


    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val alarmReceiver = AlarmReceiver()

            val langPreferences = findPreference<Preference>("pref_language")
            val notificationPreference = findPreference<SwitchPreferenceCompat>("pref_notification")
            langPreferences?.setOnPreferenceClickListener {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue == true) {
                    alarmReceiver.setNotificationAlarm(context as Context, "09:00")
                    Toast.makeText(
                        context,
                        resources.getString(R.string.reminder_notification_set),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    alarmReceiver.unsetNotificationAlarm(context as Context)
                    Toast.makeText(
                        context,
                        resources.getString(R.string.reminder_notification_unset),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }

        }
    }
}