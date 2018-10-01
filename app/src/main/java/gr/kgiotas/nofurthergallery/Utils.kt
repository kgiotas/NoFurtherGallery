package gr.kgiotas.nofurthergallery

import android.content.Context

class Utils{
    companion object {
        fun getAppVersion(context : Context) : String{
            val appinfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return appinfo.versionName
        }
    }
}