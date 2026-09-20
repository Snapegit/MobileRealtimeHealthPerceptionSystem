package com.design.appproject.ui.jiankangxinxi

import android.Manifest
import com.union.union_basic.permission.PermissionUtil
import com.design.appproject.ext.UrlPrefix
import androidx.core.widget.addTextChangedListener
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.core.view.children
import com.design.appproject.utils.Utils
import com.design.appproject.bean.BaiKeBean
import androidx.core.app.ActivityCompat.startActivityForResult
import com.blankj.utilcode.util.UriUtils
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.internal.LinkedTreeMap
import com.union.union_basic.ext.*
import com.blankj.utilcode.util.RegexUtils
import com.union.union_basic.utils.StorageUtil
import com.github.gzuliyujiang.wheelpicker.DatimePicker
import com.design.appproject.widget.BottomSpinner
import com.design.appproject.base.CommonBean
import com.blankj.utilcode.util.TimeUtils
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity
import com.github.gzuliyujiang.wheelpicker.impl.BirthdayFormatter
import com.github.gzuliyujiang.wheelpicker.impl.UnitTimeFormatter
import java.text.SimpleDateFormat
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.repository.UserRepository
import com.union.union_basic.image.selector.SmartPictureSelector
import java.io.File
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.bean.JiankangxinxiItemBean
import com.design.appproject.databinding.JiankangxinxiaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 健康信息新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_JIANKANGXINXI)
class AddOrUpdateActivity:BaseBindingActivity<JiankangxinxiaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mJiankangxinxiItemBean = JiankangxinxiItemBean()

    override fun initEvent() {
        setBarTitle("健康信息")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mJiankangxinxiItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mJiankangxinxiItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mJiankangxinxiItemBean,mRefid)
                }
            }
            if (mJiankangxinxiItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mJiankangxinxiItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mJiankangxinxiItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mJiankangxinxiItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mJiankangxinxiItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mJiankangxinxiItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun JiankangxinxiaddorupdateLayoutBinding.initView(){
            mJiankangxinxiItemBean.lurushijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            lurushijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mlurushijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                lurushijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mJiankangxinxiItemBean.lurushijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            lurushijianTv.setOnClickListener {
            mlurushijianPicker.show()
        }
            submitBtn.setOnClickListener{/*提交*/
                submit()
            }
            setData()
    }

    lateinit var mUserBean:LinkedTreeMap<String, Any>/*当前用户数据*/

    override fun initData() {
        super.initData()
        UserRepository.session<Any>().observeKt {
            it.getOrNull()?.let {
                it.data.toConversion<LinkedTreeMap<String, Any>>()?.let {
                    mUserBean = it
                    it["touxiang"]?.let { it1 -> StorageUtil.encode(CommonBean.HEAD_URL_KEY, it1) }
                    /**ss读取*/
                    if (mJiankangxinxiItemBean.zhanghao.isNullOrEmpty()){
                        mJiankangxinxiItemBean.zhanghao = it["zhanghao"]?.toString()?:""
                    }
                    binding.zhanghaoEt.keyListener = null
                    if (mJiankangxinxiItemBean.xingming.isNullOrEmpty()){
                        mJiankangxinxiItemBean.xingming = it["xingming"]?.toString()?:""
                    }
                    binding.xingmingEt.keyListener = null
                    if (mJiankangxinxiItemBean.xingbie.isNullOrEmpty()){
                        mJiankangxinxiItemBean.xingbie = it["xingbie"]?.toString()?:""
                    }
                    binding.xingbieEt.keyListener = null
                    it["nianling"]?.toString()?.let{
                        mJiankangxinxiItemBean.nianling = it.toInt()
                    }
                    binding.nianlingEt.keyListener = null
                    binding.setData()
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<JiankangxinxiItemBean>("jiankangxinxi",mId).observeKt {
                it.getOrNull()?.let {
                    mJiankangxinxiItemBean = it.data
                    mJiankangxinxiItemBean.id = mId
                    binding.setData()
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun JiankangxinxiaddorupdateLayoutBinding.submit() {
        mJiankangxinxiItemBean.zhanghao = zhanghaoEt.text.toString()
        mJiankangxinxiItemBean.xingming = xingmingEt.text.toString()
        mJiankangxinxiItemBean.xingbie = xingbieEt.text.toString()
        nianlingEt.inputType = InputType.TYPE_CLASS_NUMBER
        mJiankangxinxiItemBean.nianling = nianlingEt.text.toString().toInt()
        mJiankangxinxiItemBean.shengao = shengaoEt.text.toString()
        mJiankangxinxiItemBean.tizhong = tizhongEt.text.toString()
        mJiankangxinxiItemBean.xuexing = xuexingEt.text.toString()
        mJiankangxinxiItemBean.tiwen = tiwenEt.text.toString()
        mJiankangxinxiItemBean.xinlv = xinlvEt.text.toString()
        mJiankangxinxiItemBean.xueya = xueyaEt.text.toString()
        mJiankangxinxiItemBean.xuetang = xuetangEt.text.toString()
        if(mJiankangxinxiItemBean.zhanghao.isNullOrEmpty()){
            "账号不能为空".showToast()
            return
        }
        if(mJiankangxinxiItemBean.shengao.isNullOrEmpty()){
            "身高不能为空".showToast()
            return
        }
        if(mJiankangxinxiItemBean.tizhong.isNullOrEmpty()){
            "体重不能为空".showToast()
            return
        }
        if(mJiankangxinxiItemBean.xuexing.isNullOrEmpty()){
            "血型不能为空".showToast()
            return
        }
        addOrUpdate()

}
    private fun addOrUpdate(){/*更新或添加*/
        if (mJiankangxinxiItemBean.id>0){
            UserRepository.update("jiankangxinxi",mJiankangxinxiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<JiankangxinxiItemBean>("jiankangxinxi",mJiankangxinxiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }


    private fun JiankangxinxiaddorupdateLayoutBinding.setData(){
        if (mJiankangxinxiItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoEt.setText(mJiankangxinxiItemBean.zhanghao.toString())
        }
        if (mJiankangxinxiItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mJiankangxinxiItemBean.xingming.toString())
        }
        if (mJiankangxinxiItemBean.xingbie.isNotNullOrEmpty()){
            xingbieEt.setText(mJiankangxinxiItemBean.xingbie.toString())
        }
        if (mJiankangxinxiItemBean.nianling>=0){
            nianlingEt.setText(mJiankangxinxiItemBean.nianling.toString())
        }
        if (mJiankangxinxiItemBean.shengao.isNotNullOrEmpty()){
            shengaoEt.setText(mJiankangxinxiItemBean.shengao.toString())
        }
        if (mJiankangxinxiItemBean.tizhong.isNotNullOrEmpty()){
            tizhongEt.setText(mJiankangxinxiItemBean.tizhong.toString())
        }
        if (mJiankangxinxiItemBean.xuexing.isNotNullOrEmpty()){
            xuexingEt.setText(mJiankangxinxiItemBean.xuexing.toString())
        }
        if (mJiankangxinxiItemBean.tiwen.isNotNullOrEmpty()){
            tiwenEt.setText(mJiankangxinxiItemBean.tiwen.toString())
        }
        if (mJiankangxinxiItemBean.xinlv.isNotNullOrEmpty()){
            xinlvEt.setText(mJiankangxinxiItemBean.xinlv.toString())
        }
        if (mJiankangxinxiItemBean.xueya.isNotNullOrEmpty()){
            xueyaEt.setText(mJiankangxinxiItemBean.xueya.toString())
        }
        if (mJiankangxinxiItemBean.xuetang.isNotNullOrEmpty()){
            xuetangEt.setText(mJiankangxinxiItemBean.xuetang.toString())
        }
    }
}