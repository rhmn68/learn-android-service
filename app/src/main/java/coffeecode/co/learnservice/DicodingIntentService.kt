package coffeecode.co.learnservice

import android.app.IntentService
import android.content.Intent
import android.util.Log


class DicodingIntentService : IntentService("DicodingIntentService") {

    companion object{
        const val EXTRA_DURATION = "extra_duration"
        val TAG = DicodingIntentService::class.java.simpleName
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null){
            val duration = intent.getIntExtra(EXTRA_DURATION, 0)
            try {
                Thread.sleep(duration.toLong())
                Log.d(TAG, "OnHandleIntent: Selesai...")
            }catch (e: InterruptedException){
                e.printStackTrace()
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }
}
