package org.sample.sampleTest.ui.space

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spaceViewModel =
            ViewModelProvider(this).get(SpaceViewModel::class.java)

        _binding = FragmentSpaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        RetrofitBuilder.api.getSpaceList("VWV3ZTU1WEtUSWY2R29XOW0za3Fpb0JLbzRrR2FPdEY5TzdPYVFJUGZhcz0", "20","")
            .enqueue(object : Callback<Space>{

                override fun onResponse(
                    call: Call<Space>,
                    response: Response<Space>) {

                    Log.i("test", response.body().toString())

                    val data : List<Data> = response.body()!!.data

                    binding.horizontalRecyclerView.adapter = SpaceAdapter(data)



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
    private class SpaceAdapter(private val dataList: List<Data>) :
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

                btnExitWeb.setOnClickListener {
                    webView.visibility = View.INVISIBLE
                    btnExitWeb.visibility = View.INVISIBLE
                }

                webView.webViewClient = WebViewClient()
                val webSettings: WebSettings = webView.settings
                webSettings.javaScriptEnabled = true
                webSettings.domStorageEnabled = true

                webView.loadUrl(data.space_url)

                webView.visibility = View.VISIBLE
                btnExitWeb.visibility = View.VISIBLE
            }

        }

        override fun getItemCount() = dataList.size
    }
}