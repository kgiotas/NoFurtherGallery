package gr.kgiotas.nofurthergallery

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.grid_item.view.*
import com.bumptech.glide.request.RequestOptions

class GridAdapter(private val images : ArrayList<String>, private val context : AppCompatActivity, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder{
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_item, p0, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val options = RequestOptions()
        options.centerCrop()

        Glide.with(context).load(images[p1]).apply(options).into(p0.image)

        p0.itemView.setOnClickListener(){_ -> showFullImage(p1)}
    }

    private fun showFullImage(position: Int) {
        val fullimageIntent = Intent(context, ActivityShowImage::class.java)
        fullimageIntent.putExtra("images", images)
        fullimageIntent.putExtra("position", position)
        ContextCompat.startActivity(context, fullimageIntent, null)
    }
}

class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
    val image = view.grid_item_image
}