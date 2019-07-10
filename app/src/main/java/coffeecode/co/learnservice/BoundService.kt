package coffeecode.co.learnservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.CountDownTimer
import android.util.Log

class BoundService : Service() {

    val TAG = BoundService::class.java.simpleName
    var mBinder = MyBinder()
    val startTime = System.currentTimeMillis()

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        mTimer.start()
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        mTimer.cancel()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    var mTimer: CountDownTimer = object : CountDownTimer(100000, 1000) {
        override fun onTick(l: Long) {
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "onTick: $elapsedTime")
        }

        override fun onFinish() {}
    }

    inner class MyBinder : Binder() {
        fun getService() : BoundService{
            return BoundService()
        }
    }
}
