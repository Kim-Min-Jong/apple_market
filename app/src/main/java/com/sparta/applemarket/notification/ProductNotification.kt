package com.sparta.applemarket.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sparta.applemarket.MainActivity
import com.sparta.applemarket.R

class ProductNotification(private val context: Context) {
    private val myNotificationID = 1
    // notification 실행 메서드
    fun runNotification() {
        createNotificationChannel()
        val notification = createNotification()
        // 안드로이드 13이후로는 notification 을 하려면 권한 설정이 필요함
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
        NotificationManagerCompat.from(context).notify(myNotificationID, notification)
    }

    // notification 생성 메서드
    private fun createNotification(): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.apply {
            setSmallIcon(R.drawable.notifications_clicked)
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(context.getString(R.string.notification_text))
        }

        return builder.build()
    }

    // notification channel 생성 메서드
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            val channel = NotificationChannel(
                CHANNEL_ID, "apple market channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "this is apple market's channel"
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "APPLE_MARKET"
    }
}