package gr.kgiotas.nofurthergallery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    val picList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when {
            intent?.action == Intent.ACTION_SEND_MULTIPLE && intent.type?.startsWith("image/") == true -> {
                handleIncomingMultipleImages(intent)
            }
        }

        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val recyclerAdapter = GridAdapter(picList, this, supportFragmentManager)
        recyclerView.adapter = recyclerAdapter
    }

    private fun handleIncomingMultipleImages(intent: Intent) {
         intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            for (uri in it){
                picList.add(uri.toString())
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            exitFullScreen()
        }
        super.onBackPressed()
    }

    private fun exitFullScreen(){
        window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        supportActionBar?.show()
    }
}
