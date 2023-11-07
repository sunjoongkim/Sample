package org.sample.sampleTest.ui.space

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import org.sample.sampleTest.data.Data
import org.sample.sampleTest.data.Space
import org.sample.sampleTest.databinding.FragmentSpaceBinding
import org.sample.sampleTest.define.Define
import org.sample.sampleTest.service.RetrofitBuilder
import org.sample.sampleTest.ui.BottomSheetView
import org.sample.sampleTest.ui.space.adapter.SpaceListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        RetrofitBuilder.apiService.getSpaceList(Define.SELVERS_ACCESS_TOKEN, currentPage.toString(),"")
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
                        binding.horizontalPagerView.adapter = SpaceListAdapter(data.toMutableList(), pagerView, childFragmentManager)
                    } else {
                        (binding.horizontalPagerView.adapter as SpaceListAdapter).addData(data)
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

}