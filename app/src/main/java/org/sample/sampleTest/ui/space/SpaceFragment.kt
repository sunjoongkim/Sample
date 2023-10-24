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
import com.bumptech.glide.Glide
import org.sample.sampleTest.R
import org.sample.sampleTest.data.Data
import org.sample.sampleTest.data.Space
import org.sample.sampleTest.databinding.FragmentSpaceBinding
import org.sample.sampleTest.service.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SpaceFragment : Fragment() {

    private var _binding: FragmentSpaceBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spaceViewModel =
            ViewModelProvider(this).get(SpaceViewModel::class.java)

        _binding = FragmentSpaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.horizontalRecyclerView

        RetrofitBuilder.api.getSpaceList("VWV3ZTU1WEtUSWY2R29XOW0za3Fpb0JLbzRrR2FPdEY5TzdPYVFJUGZhcz0", "3","")
            .enqueue(object : Callback<Space>{

                override fun onResponse(
                    call: Call<Space>,
                    response: Response<Space>) {

                    Log.i("test", response.body().toString())

                    val data : List<Data> = response.body()!!.data

                    binding.horizontalRecyclerView.adapter = SpaceAdapter(data, recyclerView)
                }

                override fun onFailure(call: Call<Space>, t: Throwable) {

                }

            })

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(root.windowToken, 0)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 리스트 Adapter
    private class SpaceAdapter(private val dataList: List<Data>,  private val recyclerView: RecyclerView) :
        RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageViewSpace : ImageView = itemView.findViewById(R.id.imageSpace)
            val textViewSpaceName: TextView = itemView.findViewById(R.id.textSpaceName)
            val webView: WebView = itemView.findViewById(R.id.webView)
            val btnExitWeb: Button = itemView.findViewById(R.id.btnExitWeb)
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
                        MotionEvent.ACTION_DOWN -> {
                            // 터치가 시작될 때 RecyclerView에게 터치 이벤트 전달하지 않음
                            recyclerView.requestDisallowInterceptTouchEvent(true)
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            // 터치가 종료되거나 취소될 때 터치 이벤트 전달 금지 해제
                            recyclerView.requestDisallowInterceptTouchEvent(false)
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
    }
}