package org.sample.sampleTest.ui.space

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import org.sample.sampleTest.R
import org.sample.sampleTest.data.Data
import org.sample.sampleTest.data.Space
import org.sample.sampleTest.databinding.FragmentSpaceBinding
import org.sample.sampleTest.service.RetrofitBuilder
import org.sample.sampleTest.ui.BottomSheetView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger


class SpaceFragment : Fragment() {

    private var _binding: FragmentSpaceBinding? = null
    private val binding get() = _binding!!

    private lateinit var pagerView: ViewPager2

    private var totalPage = 0
    private var currentPage = 0
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spaceViewModel =
            ViewModelProvider(this).get(SpaceViewModel::class.java)

        _binding = FragmentSpaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pagerView = binding.horizontalPagerView

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(root.windowToken, 0)

        return root
    }

    override fun onResume() {
        super.onResume()

        // 화면 진입할때 초기화
        pagerView.currentItem = 0
        initPagerAdapter()
        initPagerCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPagerAdapter() {
        // 로딩중이거나 마지막 페이지일 경우 실행하지 않음
        if (isLoading || currentPage == totalPage - 1) return

        isLoading = true

        Log.e("@@@@@", "currentPage : $currentPage")
        // 현재 페이지를 읽어옴
        RetrofitBuilder.api.getSpaceList("VWV3ZTU1WEtUSWY2R29XOW0za3Fpb0JLbzRrR2FPdEY5TzdPYVFJUGZhcz0", currentPage.toString(),"")
            .enqueue(object : Callback<Space>{

                override fun onResponse(
                    call: Call<Space>,
                    response: Response<Space>) {

                    Log.i("test", response.body().toString())

                    isLoading = false

                    var space = response.body() as Space

                    // total page 저장
                    totalPage = space.total_page.toInt()
                    // 로딩할때마다 currentPage 증가
                    currentPage++

                    val data : List<Data> = space.data

                    // 최초엔 SpaceAdapter 세팅, 이후에는 기존 리스트에 추가
                    if (binding.horizontalPagerView.adapter == null) {
                        binding.horizontalPagerView.adapter = SpaceAdapter(data.toMutableList(), pagerView, this@SpaceFragment::showBottomSheet)
                    } else {
                        (binding.horizontalPagerView.adapter as SpaceAdapter).addData(data)
                    }
                }

                override fun onFailure(call: Call<Space>, t: Throwable) {

                }

            })
    }

    private fun initPagerCallback() {
        pagerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // 사용자가 마지막 페이지에 도달했는지 확인
                if (position == (pagerView.adapter?.itemCount ?: 0) - 1) {
                    initPagerAdapter()
                }
            }
        })
    }

    private fun showBottomSheet(buttonText: String) {
        val bottomSheetFragment = BottomSheetView()
        bottomSheetFragment.buttonText = buttonText
        bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
    }

    // 리스트 Adapter
    private class SpaceAdapter(private val dataList: MutableList<Data>,  private val pagerView: ViewPager2, private val showBottomSheet: (String) -> Unit) :
        RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageViewSpace : ImageView = itemView.findViewById(R.id.imageSpace)
            val textViewSpaceName: TextView = itemView.findViewById(R.id.textSpaceName)
            val webView: WebView = itemView.findViewById(R.id.webView)
            val btnExitWeb: Button = itemView.findViewById(R.id.btnExitWeb)
            val textMore: TextView = itemView.findViewById(R.id.textMore)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
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
            }

        }

        override fun getItemCount() = dataList.size

        // 새로운 페이지의 data 리스트를 기존 리스트에 추가후 ui갱신
        fun addData(newData: List<Data>) {
            dataList.addAll(newData)
            notifyDataSetChanged()
        }
    }
}