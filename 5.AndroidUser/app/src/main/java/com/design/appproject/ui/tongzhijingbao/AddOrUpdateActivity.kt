package com.design.appproject.ui.tongzhijingbao

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
import com.design.appproject.bean.TongzhijingbaoItemBean
import com.design.appproject.databinding.TongzhijingbaoaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 通知警报新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_TONGZHIJINGBAO)
class AddOrUpdateActivity:BaseBindingActivity<TongzhijingbaoaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mTongzhijingbaoItemBean = TongzhijingbaoItemBean()

    override fun initEvent() {
        setBarTitle("通知警报")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mTongzhijingbaoItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mTongzhijingbaoItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mTongzhijingbaoItemBean,mRefid)
                }
            }
            if (mTongzhijingbaoItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mTongzhijingbaoItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mTongzhijingbaoItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mTongzhijingbaoItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mTongzhijingbaoItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mTongzhijingbaoItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun TongzhijingbaoaddorupdateLayoutBinding.initView(){
            zhanghaoBs.let { spinner ->
            spinner.setOnClickListener {
                spinner.options.isNullOrEmpty().yes {
                    UserRepository.option("yonghu", "zhanghao", null, null,"",false).observeKt{
                        it.getOrNull()?.let {
                            spinner.setOptions(it.data, "请选择账号", false)
                            spinner.dialogShow()
                        }
                    }
                }.otherwise {
                    spinner.dialogShow()
                }
            }
            spinner.setOnItemSelectedListener(object : BottomSpinner.OnItemSelectedListener {
                override fun onItemSelected(position: Int, content: String) {
                    super.onItemSelected(position, content)
                    spinner.text = content
                    mTongzhijingbaoItemBean.zhanghao =content
                    UserRepository.yonghufollow("yonghu", "zhanghao", content).observeKt{
                        it.getOrNull()?.let {
                            xingmingEt.setText(it.data.xingming.toString())
                            xingmingEt.isFocusable = false
                            mTongzhijingbaoItemBean.xingming = it.data.xingming
                        }
                    }
                }
            })
        }
            mTongzhijingbaoItemBean.tixingshijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            tixingshijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mtixingshijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                tixingshijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mTongzhijingbaoItemBean.tixingshijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            tixingshijianTv.setOnClickListener {
            mtixingshijianPicker.show()
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
                    binding.setData()
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<TongzhijingbaoItemBean>("tongzhijingbao",mId).observeKt {
                it.getOrNull()?.let {
                    mTongzhijingbaoItemBean = it.data
                    mTongzhijingbaoItemBean.id = mId
                    binding.setData()
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun TongzhijingbaoaddorupdateLayoutBinding.submit() {
        mTongzhijingbaoItemBean.xingming = xingmingEt.text.toString()
        mTongzhijingbaoItemBean.biaoti = biaotiEt.text.toString()
        mTongzhijingbaoItemBean.tixingneirong = tixingneirongEt.text.toString()
        addOrUpdate()

}
    private fun addOrUpdate(){/*更新或添加*/
        if (mTongzhijingbaoItemBean.id>0){
            UserRepository.update("tongzhijingbao",mTongzhijingbaoItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<TongzhijingbaoItemBean>("tongzhijingbao",mTongzhijingbaoItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }


    private fun TongzhijingbaoaddorupdateLayoutBinding.setData(){
        if (mTongzhijingbaoItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoBs.text =mTongzhijingbaoItemBean.zhanghao
        }
        if (mTongzhijingbaoItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mTongzhijingbaoItemBean.xingming.toString())
        }
        if (mTongzhijingbaoItemBean.biaoti.isNotNullOrEmpty()){
            biaotiEt.setText(mTongzhijingbaoItemBean.biaoti.toString())
        }
        if (mTongzhijingbaoItemBean.tixingneirong.isNotNullOrEmpty()){
            tixingneirongEt.setText(mTongzhijingbaoItemBean.tixingneirong.toString())
        }
    }
}