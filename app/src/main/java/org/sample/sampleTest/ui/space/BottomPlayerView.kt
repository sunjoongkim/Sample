package org.sample.sampleTest.ui.space

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.sample.sampleTest.R
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.ui.space.adapter.PlayListAdapter


class BottomPlayerView : BottomSheetDialogFragment() {

    var playList: List<PanoDetail> = mutableListOf()
    var panodetailId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_player_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val pictureView: ImageView = view.findViewById(R.id.pictureView)
        val videoView: YouTubePlayerView = view.findViewById(R.id.videoView)

        Log.e("@@@@@", "=======> playList len : ${playList.size}")
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = PlayListAdapter(playList)
    }

    private fun playPanoDetail(url: String) {

    }
}