package com.design.appproject.ui.gexinghuajianyi

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
import com.design.appproject.bean.GexinghuajianyiItemBean
import com.design.appproject.databinding.GexinghuajianyiaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 个性化建议新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_GEXINGHUAJIANYI)
class AddOrUpdateActivity:BaseBindingActivity<GexinghuajianyiaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mGexinghuajianyiItemBean = GexinghuajianyiItemBean()

    override fun initEvent() {
        setBarTitle("个性化建议")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mGexinghuajianyiItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mGexinghuajianyiItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mGexinghuajianyiItemBean,mRefid)
                }
            }
            if (mGexinghuajianyiItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mGexinghuajianyiItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mGexinghuajianyiItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mGexinghuajianyiItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mGexinghuajianyiItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mGexinghuajianyiItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun GexinghuajianyiaddorupdateLayoutBinding.initView(){
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
                    mGexinghuajianyiItemBean.zhanghao =content
                    UserRepository.yonghufollow("yonghu", "zhanghao", content).observeKt{
                        it.getOrNull()?.let {
                            xingmingEt.setText(it.data.xingming.toString())
                            xingmingEt.isFocusable = false
                            mGexinghuajianyiItemBean.xingming = it.data.xingming
                        }
                    }
                }
            })
        }
            mGexinghuajianyiItemBean.jianyishijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            jianyishijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mjianyishijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                jianyishijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mGexinghuajianyiItemBean.jianyishijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            jianyishijianTv.setOnClickListener {
            mjianyishijianPicker.show()
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
            HomeRepository.info<GexinghuajianyiItemBean>("gexinghuajianyi",mId).observeKt {
                it.getOrNull()?.let {
                    mGexinghuajianyiItemBean = it.data
                    mGexinghuajianyiItemBean.id = mId
                    binding.setData()
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun GexinghuajianyiaddorupdateLayoutBinding.submit() {
        mGexinghuajianyiItemBean.xingming = xingmingEt.text.toString()
        mGexinghuajianyiItemBean.jianyimingcheng = jianyimingchengEt.text.toString()
        mGexinghuajianyiItemBean.jianyineirong = jianyineirongEt.text.toString()
        addOrUpdate()

}
    private fun addOrUpdate(){/*更新或添加*/
        if (mGexinghuajianyiItemBean.id>0){
            UserRepository.update("gexinghuajianyi",mGexinghuajianyiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<GexinghuajianyiItemBean>("gexinghuajianyi",mGexinghuajianyiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }


    private fun GexinghuajianyiaddorupdateLayoutBinding.setData(){
        if (mGexinghuajianyiItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoBs.text =mGexinghuajianyiItemBean.zhanghao
        }
        if (mGexinghuajianyiItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mGexinghuajianyiItemBean.xingming.toString())
        }
        if (mGexinghuajianyiItemBean.jianyimingcheng.isNotNullOrEmpty()){
            jianyimingchengEt.setText(mGexinghuajianyiItemBean.jianyimingcheng.toString())
        }
        if (mGexinghuajianyiItemBean.jianyineirong.isNotNullOrEmpty()){
            jianyineirongEt.setText(mGexinghuajianyiItemBean.jianyineirong.toString())
        }
    }
}