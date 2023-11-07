package org.sample.sampleTest.ui.space

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.sample.sampleTest.R
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.data.Picture
import org.sample.sampleTest.data.Show
import org.sample.sampleTest.data.SpaceShow
import org.sample.sampleTest.data.SpaceTTS
import org.sample.sampleTest.data.Youtube
import org.sample.sampleTest.define.Define
import org.sample.sampleTest.service.RetrofitBuilder
import org.sample.sampleTest.ui.space.adapter.PlayListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 전시 선택시 하단에서 올라오는 뷰
class BottomShowView : BottomSheetDialogFragment() {

    private var imageMain: ImageView? = null
    private val imageThumbList = mutableListOf<ImageView?>()
    private var textTitle: TextView? = null
    private var textDesc: TextView? = null
    private var textUrl: TextView? = null

    var showId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_show_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageMain = view.findViewById(R.id.imageMain)
        imageThumbList.add(view.findViewById(R.id.imageSub1))
        imageThumbList.add(view.findViewById(R.id.imageSub2))
        imageThumbList.add(view.findViewById(R.id.imageSub3))
        imageThumbList.add(view.findViewById(R.id.imageSub4))
        imageThumbList.add(view.findViewById(R.id.imageSub5))

        textTitle = view.findViewById(R.id.textTitle)
        textDesc = view.findViewById(R.id.textDesc)
        textUrl = view.findViewById(R.id.textUrl)
    }

    override fun onResume() {
        super.onResume()

        initShow()
    }

    private fun initShow() {
        RetrofitBuilder.apiService.getShow(Define.SELVERS_ACCESS_TOKEN, showId!!)
            .enqueue(object : Callback<SpaceShow> {

                override fun onResponse(
                    call: Call<SpaceShow>,
                    response: Response<SpaceShow>
                ) {
                    var spaceShow = response.body() as SpaceShow

                    textTitle?.text = spaceShow.data.result
                    textDesc?.text = spaceShow.data.message
                    textUrl?.text = spaceShow.data.originImgList[0].imgUrl

                    // 메인 이미지를 첫번째 이미지로 초기화
                    Glide.with(imageMain!!.context)
                        .load(spaceShow.data.originImgList[0].imgUrl)
                        .placeholder(R.drawable.image_empty)
                        .error(R.drawable.image_empty)
                        .fallback(R.drawable.image_empty)
                        .centerCrop()
                        .into(imageMain!!)

                    generateThumbnails(spaceShow.data)
                }

                override fun onFailure(call: Call<SpaceShow>, t: Throwable) {
                    Log.i("@@@@@", "onFailure : $t")
                }

            })
    }

    private fun generateThumbnails(show: Show) {

        for (i in show.thumImgList.indices) {
            // thumImgList 갯수만큼 thumnail 이미지 생성
            Glide.with(imageThumbList[i]!!.context)
                .load(show.thumImgList[i].imgUrl)
                .placeholder(R.drawable.image_empty)
                .error(R.drawable.image_empty)
                .fallback(R.drawable.image_empty)
                .centerCrop()
                .into(imageThumbList[i]!!)

            // thumbnail 선택시 해당 original image 그려주는 클릭 리스너 연결
            imageThumbList[i]?.setOnClickListener {
                Glide.with(imageMain!!.context)
                    .load(show.originImgList[i].imgUrl)
                    .placeholder(R.drawable.image_empty)
                    .error(R.drawable.image_empty)
                    .fallback(R.drawable.image_empty)
                    .centerCrop()
                    .into(imageMain!!)

                textUrl?.text = show.originImgList[i].imgUrl
            }
        }
    }

}