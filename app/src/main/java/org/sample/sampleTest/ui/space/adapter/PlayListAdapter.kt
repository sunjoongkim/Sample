package org.sample.sampleTest.ui.space.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sample.sampleTest.R
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.data.Picture
import org.sample.sampleTest.data.Youtube

// Play 리스트 Adapter
class PlayListAdapter(private val playList: List<PanoDetail>) :
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItem : ImageView = itemView.findViewById(R.id.imageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_item, parent, false)
        Log.e("@@@@@", "==========> onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val panoDetail = playList.firstOrNull { it.panodetailId == playList[position].panodetailId }
        var url: String? = ""

        if (panoDetail is Picture) {
            url = panoDetail.pictureUrl
        } else if (panoDetail is Youtube) {
            url = panoDetail.youtubeThumUrl
        }

        Log.e("@@@@@", "==========>url : $url ")
        Glide.with(holder.imageItem.context)
            .load(url)
            .placeholder(R.drawable.image_empty)
            .error(R.drawable.image_empty)
            .fallback(R.drawable.image_empty)
            .into(holder.imageItem)
    }


    override fun getItemCount() = playList.size

}