package coffeecode.co.learnservice

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import java.lang.ref.WeakReference

class OriginService : Service(), DummyAsyncCallback {

    companion object{
        //Kenapa gk bisa pake const
        val TAG = OriginService::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        val dummyAsync = DummyAsync(this)
        dummyAsync.execute()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ")
    }

    override fun preAsync() {
        Log.d(TAG, "preAsync: Mulai... ")
    }

    override fun postAsync() {
        Log.d(TAG, "postAsync: Selesai.....")
        stopSelf()
    }

    @SuppressLint("StaticFieldLeak")
    inner class DummyAsync(callback: DummyAsyncCallback) : AsyncTask<Void, Void, Void>() {

        private val callback : WeakReference<DummyAsyncCallback> = WeakReference(callback)

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(TAG, "onPreExecute: ")
            callback.get()?.preAsync()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            Log.d(TAG, "doInbackground: ")
            try {
                Thread.sleep(3000)
            }catch (e : InterruptedException){
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Log.d(TAG, "onPostExecuter: ")
            callback.get()?.postAsync()
        }

    }
}
