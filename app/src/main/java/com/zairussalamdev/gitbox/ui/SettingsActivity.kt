package com.zairussalamdev.gitbox.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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

                } else {
                    //TODO: Deactivate Notification Alarm
                }
                true
            }

        }
    }
}