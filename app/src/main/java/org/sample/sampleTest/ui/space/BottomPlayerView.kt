package org.sample.sampleTest.ui.space

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.sample.sampleTest.R
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.data.Picture
import org.sample.sampleTest.data.Youtube
import org.sample.sampleTest.ui.space.adapter.PlayListAdapter

// 영상, 이미지 선택시 하단에서 올라오는 뷰
class BottomPlayerView : BottomSheetDialogFragment() {

    private var recyclerView: RecyclerView? = null
    private var pictureView: ImageView? = null
    private var videoView: YouTubePlayerView? = null
    private var youTubePlayer: YouTubePlayer? = null

    private var videoId = "0"

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

        recyclerView = view.findViewById(R.id.recyclerView)
        pictureView = view.findViewById(R.id.pictureView)
        videoView = view.findViewById(R.id.videoView)
        lifecycle.addObserver(videoView!!)

        // 유튜브 플레이어 객체를 얻기위한 리스너 등록
        videoView?.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@BottomPlayerView.youTubePlayer = youTubePlayer
                playYoutube()
            }
        })

        // 리스트 생성시 playList와 리스트 선택시 재생을 위한 playByType 함수 전달
        recyclerView?.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView?.adapter = PlayListAdapter(playList, ::playByType)

        // 뷰 진입시 재생
        playByType()
    }

    private fun playByType(id: String = panodetailId) {
        //panodetailId 로 PanoDetail 찾기
        val panoDetail = playList.firstOrNull { it.panodetailId == id }

        if (panoDetail is Picture) {
            // PanoDetail이 Picture일 경우 url을 그려줌
            val url = panoDetail.pictureUrl
            playPicture(url)
        } else if (panoDetail is Youtube) {
            // PanoDetail이 Youtube일 경우 video id 를 가져와서 플레이 해줌
            val splits = panoDetail.youtubeVideoUrl.split('/')
            videoId = splits[splits.size - 1]

            playYoutube()
        }
    }
    private fun playYoutube() {
        youTubePlayer?.loadVideo(videoId, 0f)

        videoView?.visibility = View.VISIBLE
        pictureView?.visibility = View.GONE
    }

    private fun playPicture(url: String) {
        youTubePlayer?.pause()

        Glide.with(pictureView!!.context)
            .load(url)
            .placeholder(R.drawable.image_empty)
            .error(R.drawable.image_empty)
            .fallback(R.drawable.image_empty)
            .into(pictureView!!)

        videoView?.visibility = View.GONE
        pictureView?.visibility = View.VISIBLE
    }
}