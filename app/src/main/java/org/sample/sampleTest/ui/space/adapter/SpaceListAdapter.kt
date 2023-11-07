package org.sample.sampleTest.ui.space.adapter

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import org.sample.sampleTest.R
import org.sample.sampleTest.data.Data
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.data.SpaceContent
import org.sample.sampleTest.define.Define
import org.sample.sampleTest.ui.space.bridge.SpaceBridge
import org.sample.sampleTest.service.RetrofitBuilder
import org.sample.sampleTest.ui.BottomSheetView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

// Space 리스트 Adapter
class SpaceListAdapter(private val dataList: MutableList<Data>, private val pagerView: ViewPager2, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<SpaceListAdapter.ViewHolder>(), TextToSpeech.OnInitListener  {

    private lateinit var tts: TextToSpeech
    private lateinit var textTTS: String

    var context: Context? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewSpace : ImageView = itemView.findViewById(R.id.imageSpace)
        val textViewSpaceName: TextView = itemView.findViewById(R.id.textSpaceName)
        val webView: WebView = itemView.findViewById(R.id.webView)
        val btnExitWeb: Button = itemView.findViewById(R.id.btnExitWeb)
        val textMore: TextView = itemView.findViewById(R.id.textMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.space_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.textViewSpaceName.text = data.space_name

        Glide.with(holder.imageViewSpace.context)
            .load(data.space_mainimage)
            .placeholder(R.drawable.image_empty)
            .error(R.drawable.image_empty)
            .fallback(R.drawable.image_empty)
            .into(holder.imageViewSpace)

        holder.textMore.setOnClickListener{
            showBottomSheet(data.space_name)
        }


        holder.imageViewSpace.setOnClickListener {

            val webView = holder.webView
            val btnExitWeb = holder.btnExitWeb

            // back 버튼 클릭시 웹뷰, 백버튼 안보이게 함
            btnExitWeb.setOnClickListener {
                webView.visibility = View.INVISIBLE
                btnExitWeb.visibility = View.INVISIBLE
            }

            // webview 화면에서 리스트 스크롤 되지 않도록 하는 리스너
            webView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        // webview 드래그중엔 터치 이벤트 전달 금지
                        pagerView.isUserInputEnabled = false
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        // 터치가 종료되거나 취소될 때 터치 이벤트 전달 금지 해제
                        pagerView.isUserInputEnabled = true
                    }
                }
                false
            }


            // web view 구현부, 3D 렌더링을 위한 세팅값 추가
            webView.webViewClient = WebViewClient()
            val webSettings: WebSettings = webView.settings
            webSettings.javaScriptEnabled = true
            webSettings.domStorageEnabled = true

            webView.loadUrl(data.space_url)

            // 이미지 클릭시 웹뷰, 백버튼 보이게함
            webView.visibility = View.VISIBLE
            btnExitWeb.visibility = View.VISIBLE

            // 이미지 클릭시 SpaceContent 리스트 가져오기
            getSpaceContentList(data, webView)
        }

    }

    override fun getItemCount() = dataList.size

    // 새로운 페이지의 data 리스트를 기존 리스트에 추가후 ui갱신
    fun addData(newData: List<Data>) {
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    private fun getSpaceContentList(data: Data, webView: WebView) {
        var panoDetailList : MutableList<PanoDetail> = mutableListOf()

        RetrofitBuilder.apiService.getSpaceContentList(Define.SELVERS_ACCESS_TOKEN, data.space_id, data.member_id)
            .enqueue(object : Callback<SpaceContent> {

                override fun onResponse(
                    call: Call<SpaceContent>,
                    response: Response<SpaceContent>
                ) {

                    Log.i("@@@@@", response.body().toString())

                    var spaceContent = response.body() as SpaceContent

                    // YoutubeList 추가 후 pictureList 추가
                    panoDetailList.addAll(spaceContent.data.youtubeList)
                    panoDetailList.addAll(spaceContent.data.pictureList)

                    // 웹뷰 브릿지
                    webView.addJavascriptInterface(SpaceBridge(this@SpaceListAdapter, panoDetailList, fragmentManager) , "spaceHandler")


                }

                override fun onFailure(call: Call<SpaceContent>, t: Throwable) {
                    Log.i("@@@@@", "onFailure : $t")

                }

            })
    }

    private fun showBottomSheet(buttonText: String) {
        val bottomSheetFragment = BottomSheetView()
        bottomSheetFragment.buttonText = buttonText
        bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
    }

    fun speakTTS(desc: String) {
        // tts 객체 초기화
        textTTS = desc
        tts = TextToSpeech(context, this)
    }

    // tts 초기화 완료시 호출됨
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("@@@@@", "지원하지 않는 언어입니다.")
            } else {
                Log.e("@@@@@", "TTS 초기화 완료.")
                tts.speak(textTTS, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        } else {
            Log.e("@@@@@", "TTS 초기화 실패.")
        }
    }


}