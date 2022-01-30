package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ContentMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    //setting the URL for downloading the files
    private lateinit var customUrl : String
    private lateinit var view : View
    private lateinit var binding : ContentMainBinding

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        binding = ContentMainBinding.inflate(layoutInflater)


        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            custom_button.buttonState = ButtonState.Loading
            onRadioButtonClicked()
            //download()
        }
    }



    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }



    private fun onRadioButtonClicked() {

        val radioGroup = binding.radioGroup
        //val selectedId = radioGroup.checkedRadioButtonId
        radioGroup.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                Log.i("Main Activity", checkedId.toString())
            }
                /*when (checkedId) {
                    R.id.loadapp_radio -> {
                        Log.i("Main Activity", "loadapp")
                    }
                    R.id.retrofit_radio -> {
                        Log.i("Main Activity", "retrofit")
                    }
                    R.id.glide_radio -> {
                        Log.i("Main Activity", "glidfe")
                    }
                    else -> {
                        Log.i("Main Activity", "no radio button selected")
                    }
                }
            }*/
        })
    }





/*        if(view is RadioButton){
            //is the button now checked?
            val checked = view.isChecked
            when(view.getId()){
                R.id.glide_radio ->
                    if(checked){
                        customUrl = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
                    }
                R.id.loadapp_radio ->
                    if(checked){
                        customUrl = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/refs/heads/master.zip"
                    }
                R.id.retrofit_radio ->
                    if(checked){
                        customUrl = "https://github.com/square/retrofit/archive/refs/heads/master.zip"
                    }
            }
        }
        else{
            Toast.makeText(this, getString(R.string.select_file_text), Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(customUrl))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        /*private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"*/
        private const val CHANNEL_ID = "channelId"
    }

}
