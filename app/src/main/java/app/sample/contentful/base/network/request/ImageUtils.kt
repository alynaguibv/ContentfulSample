package app.sample.contentful.base.network.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.URL

class ImageUtils {
    fun loadBackground(urlString: String, collapsingToolbarLayout: CollapsingToolbarLayout) {
        try {
            val params = arrayOf(urlString, collapsingToolbarLayout)
            TaskLoadImage().execute(params)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

class TaskLoadImage : AsyncTask<Any, Bitmap, Bitmap>() {

    var weakReference: WeakReference<CollapsingToolbarLayout>? = null

    override fun doInBackground(vararg params_: Any?): Bitmap? {
        val arr = params_[0] as Array<Any>
        var urlString = arr[0] as String
        weakReference = WeakReference(arr[1] as CollapsingToolbarLayout)
        if (!urlString.contains("http:") && !urlString.contains("https:")) {
            urlString = "http:$urlString"
        }
        val url = URL(urlString)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    override fun onPostExecute(result: Bitmap?) {
        weakReference?.get()?.let { collapsingToolbarLayout ->
            val drawable = BitmapDrawable(collapsingToolbarLayout.resources, result)
            collapsingToolbarLayout.background = drawable
            weakReference?.clear()
        }
        super.onPostExecute(result)
    }
}