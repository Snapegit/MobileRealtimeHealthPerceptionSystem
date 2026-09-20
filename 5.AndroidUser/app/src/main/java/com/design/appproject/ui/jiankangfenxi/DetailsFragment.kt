package com.design.appproject.ui.jiankangfenxi

import com.design.appproject.logic.repository.UserRepository
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import android.annotation.SuppressLint
import com.union.union_basic.utils.StorageUtil
import com.design.appproject.utils.ArouterUtils
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.design.appproject.base.*
import com.design.appproject.ext.postEvent
import android.media.MediaPlayer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import com.google.gson.Gson
import android.view.Gravity
import android.view.ViewGroup
import com.design.appproject.widget.DetailBannerAdapter
import android.widget.*
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.ColorUtils
import com.design.appproject.R
import com.qmuiteam.qmui.layout.QMUILinearLayout
import com.union.union_basic.ext.*
import androidx.core.view.setMargins
import com.alibaba.android.arouter.launcher.ARouter
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.*
import com.union.union_basic.network.DownloadListener
import com.union.union_basic.network.DownloadUtil
import java.io.File
import androidx.core.view.setPadding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.utils.Utils
import java.util.*
import kotlin.concurrent.timerTask
import com.design.appproject.ext.load
import com.design.appproject.logic.viewmodel.jiankangfenxi.DetailsViewModel
import androidx.activity.viewModels
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.databinding.JiankangfenxicommonDetailsLayoutBinding
import com.design.appproject.bean.*
import com.design.appproject.ui.CommentsAdatper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import android.text.Html
import com.design.appproject.widget.MyTextView
import com.design.appproject.widget.MyFlexBoxLayout
import com.design.appproject.widget.MyImageView
import android.view.ContextThemeWrapper
import com.google.android.flexbox.FlexWrap
import com.union.union_basic.image.loader.GlideLoader.load
/**
 * 健康分析详情页
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_DETAILS_JIANKANGFENXI)
class DetailsFragment : BaseBindingFragment<JiankangfenxicommonDetailsLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0 /*id*/

    @JvmField
    @Autowired
    var mIsBack: Boolean = false /*是否用户后台进入*/

    private val mDetailsViewModel by viewModels<DetailsViewModel>()

    private var mJiankangfenxiItemBean=JiankangfenxiItemBean()/*详情内容*/


    @SuppressLint("SuspiciousIndentation")
    override fun initEvent() {
        setBarTitle("健康分析详情页")
        setBarColor("#FFFFFF","black")
        binding.apply{
            srv.setOnRefreshListener {
                loadData()
            }
             jiankangbaogaoTv.setOnClickListener {/*下载*/
                DownloadUtil.download("${NetRetrofitClient.BASE_URL}file/download?fileName=${mJiankangfenxiItemBean.jiankangbaogao.replace("file/","")}","${CommonBean.DOWNLOAD_PATH}/jiankangbaogao",object : DownloadListener {
                    override fun onStart() {"开始下载".showToast()}

                    override fun onProgress(progress: Int) {}

                    override fun onFinish(path: String?) {
                        "下载完成".showToast()
                    }

                    override fun onFail(errorInfo: String?) {
                        File(CommonBean.DOWNLOAD_PATH, "jiankangbaogao").delete()
                        runOnUiThread {
                            "下载失败,请重试".showToast()
                        }
                    }
                })
        }
    }
    }

    override fun initData() {
        super.initData()
        showLoading()
        loadData()
        mDetailsViewModel.infoLiveData.observeKt(errorBlock = {binding.srv.isRefreshing =false}) {
            it.getOrNull()?.let { info->
                binding.srv.isRefreshing =false
                mJiankangfenxiItemBean = info.data
                 binding.setInfo()
            }
        }
        mDetailsViewModel.updateLiveData.observeKt {
            it.getOrNull()?.let {
            }

        }
    }

    private fun loadData(){
        mDetailsViewModel.info("jiankangfenxi",mId.toString())
    }


    private fun JiankangfenxicommonDetailsLayoutBinding.setInfo(){
        zhanghaoTv.text = "${mJiankangfenxiItemBean.zhanghao}"
        xingmingTv.text = "${mJiankangfenxiItemBean.xingming}"
        xingbieTv.text = "${mJiankangfenxiItemBean.xingbie}"
        nianlingTv.text = "${mJiankangfenxiItemBean.nianling}"
        fenxituIfv.load(requireActivity(),"${mJiankangfenxiItemBean.fenxitu}")
        yichangjianceTv.text = "${mJiankangfenxiItemBean.yichangjiance}"
        jiankangbaogaoTv.text = "${mJiankangfenxiItemBean.jiankangbaogao}下载文件"
        fenxineirongTv.text = "${mJiankangfenxiItemBean.fenxineirong}"
        fenxiriqiTv.text = "${mJiankangfenxiItemBean.fenxiriqi}"
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== AppCompatActivity.RESULT_OK && requestCode==101){
            loadData()
        }
    }

}