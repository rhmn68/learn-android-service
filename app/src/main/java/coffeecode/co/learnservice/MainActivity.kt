package coffeecode.co.learnservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.os.IBinder
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection

class MainActivity : AppCompatActivity() {

    var mServiceBound = false
    var mBoundService: BoundService? = null

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as BoundService.MyBinder
            mBoundService = myBinder.getService()
            mServiceBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound){
            unbindService(mServiceConnection)
        }
    }

    private fun onClick() {
        btnStartService.setOnClickListener {
            val mStartServiceIntent = Intent(this, OriginService::class.java)
            startService(mStartServiceIntent)
        }

        btnStartIntentService.setOnClickListener {
            val mStartIntentService = Intent(this, DicodingIntentService::class.java)
            mStartIntentService.putExtra(DicodingIntentService.EXTRA_DURATION, 5000)
            startService(mStartIntentService)
        }

        btnStartBoundService.setOnClickListener {
            val mBoundServiceIntent = Intent(this@MainActivity, BoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }

        btnStopBoundService.setOnClickListener {
            unbindService(mServiceConnection)
        }
    }

}
