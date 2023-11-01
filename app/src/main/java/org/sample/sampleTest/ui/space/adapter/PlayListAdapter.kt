package org.sample.sampleTest.ui.space.adapter


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
class PlayListAdapter(private val playList: List<PanoDetail>, private val playByType: (String) -> Unit) :
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItem : ImageView = itemView.findViewById(R.id.imageItem)
        val playIcon : ImageView = itemView.findViewById(R.id.playIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val panoDetail = playList.firstOrNull {
            it.panodetailId == playList[position].panodetailId
        } ?: return

        holder.imageItem.setOnClickListener {
            playByType(playList[position].panodetailId)
        }

        var url: String? = ""

        if (panoDetail is Picture) {
            // 리스트 아이템이 picture 이면 url 저장후 재생아이콘 제거
            url = panoDetail.pictureUrl
            holder.playIcon.visibility = View.INVISIBLE
        } else if (panoDetail is Youtube) {
            // 리스트 아이템이 youtube 이면 thumnail url 저장후 재생아이콘 보여줌
            url = panoDetail.youtubeThumUrl
            holder.playIcon.visibility = View.VISIBLE
        }

        // 저장한 url 표시
        Glide.with(holder.imageItem.context)
            .load(url)
            .placeholder(R.drawable.image_empty)
            .error(R.drawable.image_empty)
            .fallback(R.drawable.image_empty)
            .into(holder.imageItem)
    }


    override fun getItemCount() = playList.size

}