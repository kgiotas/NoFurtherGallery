package gr.kgiotas.nofurthergallery

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_show_image.*
import android.content.Intent
import android.os.Parcelable
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler

class ActivityShowImage : AppCompatActivity(){

    var incImg = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_show_image)

        when{
            intent?.action == Intent.ACTION_SEND -> {
                if(intent.type?.startsWith("image/") == true){
                    incImg = true
                }
            }
        }
        var picUri : String = ""
        var picUris : ArrayList<String>
        var pos : Int

        if(!incImg) {
            picUris = intent.getSerializableExtra("images") as ArrayList<String>
            pos = intent.getIntExtra("position", 0)
            val adapter = ImageAdapter(this, picUris)
            fragment_iv_viewpager.adapter = adapter
            fragment_iv_viewpager.currentItem = pos
            fragment_full_image.setOnTouchListener(ImageMatrixTouchHandler(this))
        }else{
            (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                // Update UI to reflect image being shared
                picUri = it.toString()
                fragment_iv_viewpager.visibility = View.GONE
                fragment_full_image.visibility = View.VISIBLE
                Glide.with(this).load(picUri).into(fragment_full_image)
                fragment_full_image.setOnTouchListener(ImageMatrixTouchHandler(this))
            }
        }
        setFullScreen()
    }

    private fun setFullScreen(){
        window?.decorView.apply {
            this!!.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            supportActionBar?.hide()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setFullScreen()
    }
}