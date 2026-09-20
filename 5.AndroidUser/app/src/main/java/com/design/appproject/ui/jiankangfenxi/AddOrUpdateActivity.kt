package com.design.appproject.ui.jiankangfenxi

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
import com.design.appproject.bean.JiankangfenxiItemBean
import com.design.appproject.ext.afterTextChanged
import com.design.appproject.bean.JiankangxinxiItemBean
import com.design.appproject.databinding.JiankangfenxiaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 健康分析新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_JIANKANGFENXI)
class AddOrUpdateActivity:BaseBindingActivity<JiankangfenxiaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mCrossTable: String = "" /*跨表表名*/

    @JvmField
    @Autowired
    var mCrossObj: JiankangxinxiItemBean = JiankangxinxiItemBean() /*跨表表内容*/

    @JvmField
    @Autowired
    var mStatusColumnName: String = "" /*列名*/

    @JvmField
    @Autowired
    var mStatusColumnValue: String = "" /*列值*/

    @JvmField
    @Autowired
    var mTips: String = "" /*提示*/
    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mJiankangfenxiItemBean = JiankangfenxiItemBean()

    override fun initEvent() {
        setBarTitle("健康分析")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mJiankangfenxiItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mJiankangfenxiItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mJiankangfenxiItemBean,mRefid)
                }
            }
            if (mJiankangfenxiItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mJiankangfenxiItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mJiankangfenxiItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mJiankangfenxiItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mJiankangfenxiItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mJiankangfenxiItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun JiankangfenxiaddorupdateLayoutBinding.initView(){
             fenxituLl.setOnClickListener {
            SmartPictureSelector.openPicture(this@AddOrUpdateActivity) {
                val path = it[0]
                showLoading("上传中...")
                UserRepository.upload(File(path), "fenxitu").observeKt{
                    it.getOrNull()?.let {
                        fenxituIfv.load(this@AddOrUpdateActivity, "file/"+it.file)
                        mJiankangfenxiItemBean.fenxitu = "file/" + it.file
                    }
                }
            }
        }
            jiankangbaogaoTv.setOnClickListener {
                startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).apply {
                   addCategory(Intent.CATEGORY_OPENABLE)
                   type =  "*/*" }, "请选择文件"), 1005 )
        }
            mJiankangfenxiItemBean.fenxiriqi = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd"))
            fenxiriqiTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd"))
            val mfenxiriqiPicker = DatePicker(this@AddOrUpdateActivity).apply {
                wheelLayout.setDateFormatter(BirthdayFormatter())
                wheelLayout.setRange(DateEntity.target(1923, 1, 1),DateEntity.target(2050, 12, 31), DateEntity.today())
                setOnDatePickedListener { year, month, day ->
                    fenxiriqiTv.text = "$year-$month-$day"
                    mJiankangfenxiItemBean.fenxiriqi="$year-$month-$day"
                }
        }
            fenxiriqiTv.setOnClickListener {
            mfenxiriqiPicker.show()
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
            HomeRepository.info<JiankangfenxiItemBean>("jiankangfenxi",mId).observeKt {
                it.getOrNull()?.let {
                    mJiankangfenxiItemBean = it.data
                    mJiankangfenxiItemBean.id = mId
                    binding.setData()
                }
            }
        }
        if (mCrossTable.isNotNullOrEmpty()){/*跨表*/
            mCrossObj.javaClass.declaredFields.any{it.name == "zhanghao"}.yes {
                mJiankangfenxiItemBean.zhanghao = mCrossObj.javaClass.getDeclaredField("zhanghao").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "xingming"}.yes {
                mJiankangfenxiItemBean.xingming = mCrossObj.javaClass.getDeclaredField("xingming").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "xingbie"}.yes {
                mJiankangfenxiItemBean.xingbie = mCrossObj.javaClass.getDeclaredField("xingbie").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "nianling"}.yes {
                mJiankangfenxiItemBean.nianling = mCrossObj.javaClass.getDeclaredField("nianling").also { it.isAccessible=true }.get(mCrossObj) as Int
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "fenxitu"}.yes {
                mJiankangfenxiItemBean.fenxitu = mCrossObj.javaClass.getDeclaredField("fenxitu").also { it.isAccessible=true }.get(mCrossObj).toString().split(",")[0]
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "yichangjiance"}.yes {
                mJiankangfenxiItemBean.yichangjiance = mCrossObj.javaClass.getDeclaredField("yichangjiance").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "jiankangbaogao"}.yes {
                mJiankangfenxiItemBean.jiankangbaogao = mCrossObj.javaClass.getDeclaredField("jiankangbaogao").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "fenxineirong"}.yes {
                mJiankangfenxiItemBean.fenxineirong = mCrossObj.javaClass.getDeclaredField("fenxineirong").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
            mCrossObj.javaClass.declaredFields.any{it.name == "fenxiriqi"}.yes {
                mJiankangfenxiItemBean.fenxiriqi = mCrossObj.javaClass.getDeclaredField("fenxiriqi").also { it.isAccessible=true }.get(mCrossObj) as  String
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun JiankangfenxiaddorupdateLayoutBinding.submit() {
        mJiankangfenxiItemBean.zhanghao = zhanghaoEt.text.toString()
        mJiankangfenxiItemBean.xingming = xingmingEt.text.toString()
        mJiankangfenxiItemBean.xingbie = xingbieEt.text.toString()
        nianlingEt.inputType = InputType.TYPE_CLASS_NUMBER
        mJiankangfenxiItemBean.nianling = nianlingEt.text.toString().toInt()
        mJiankangfenxiItemBean.yichangjiance = yichangjianceEt.text.toString()
        mJiankangfenxiItemBean.fenxineirong = fenxineirongEt.text.toString()
        if(mJiankangfenxiItemBean.fenxineirong.isNullOrEmpty()){
            "分析内容不能为空".showToast()
            return
        }
        var crossuserid:Long = 0
        var crossrefid:Long = 0
        var crossoptnum:Int = 0
        if (mStatusColumnName.isNotNullOrEmpty()){
            if (!mStatusColumnName.startsWith("[")){
                mCrossObj.javaClass.declaredFields.any{it.name == mStatusColumnName}.yes {
                    mCrossObj.javaClass.getDeclaredField(mStatusColumnName).also { it.isAccessible=true }.set(mCrossObj,mStatusColumnValue)
                    UserRepository.update(mCrossTable,mCrossObj).observeForever {  }
                }
            }else{
                crossuserid = Utils.getUserId()
                mCrossObj.javaClass.declaredFields.any{it.name == "id"}.yes {
                    crossrefid =mCrossObj.javaClass.getDeclaredField("id").also { it.isAccessible=true }.get(mCrossObj).toString().toLong()
                }
                crossoptnum = mStatusColumnName.replace("[","").replace("]","").toIntOrNull()?:0
            }
        }

        if (crossuserid>0 && crossrefid>0){
            mJiankangfenxiItemBean.javaClass.declaredFields.any{it.name == "crossuserid"}.yes {
                mJiankangfenxiItemBean.javaClass.getDeclaredField("crossuserid").also { it.isAccessible=true }.set(mJiankangfenxiItemBean,crossuserid)
            }
            mJiankangfenxiItemBean.javaClass.declaredFields.any{it.name == "crossrefid"}.yes {
                mJiankangfenxiItemBean.javaClass.getDeclaredField("crossrefid").also { it.isAccessible=true }.set(mJiankangfenxiItemBean,crossrefid)
            }
            HomeRepository.list<JiankangfenxiItemBean>("jiankangfenxi", mapOf("page" to "1","limit" to "10","crossuserid" to crossuserid.toString(),"crossrefid" to crossrefid.toString())).observeKt{
                it.getOrNull()?.let {
                    if (it.data.list.size>=crossoptnum){
                        mTips.showToast()
                    }else{
                        crossCal()
                    }
                }
            }
        }else{
            crossCal()
        }

}
    private fun crossCal(){/*更新跨表数据*/
        addOrUpdate()
    }
    private fun addOrUpdate(){/*更新或添加*/
        if (mJiankangfenxiItemBean.id>0){
            UserRepository.update("jiankangfenxi",mJiankangfenxiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<JiankangfenxiItemBean>("jiankangfenxi",mJiankangfenxiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && data!=null){
            if (requestCode==1005){
                showLoading()
                UserRepository.upload(UriUtils.uri2File(data.data), "jiankangbaogao").observeKt{
                    it.getOrNull()?.let {
                        binding.jiankangbaogaoTv.text = "file/"+ it.file
                        mJiankangfenxiItemBean.jiankangbaogao = "file/" + it.file
                    }
                }
                return
            }
        }
    }

    private fun JiankangfenxiaddorupdateLayoutBinding.setData(){
        if (mJiankangfenxiItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoEt.setText(mJiankangfenxiItemBean.zhanghao.toString())
        }
        if (mJiankangfenxiItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mJiankangfenxiItemBean.xingming.toString())
        }
        if (mJiankangfenxiItemBean.xingbie.isNotNullOrEmpty()){
            xingbieEt.setText(mJiankangfenxiItemBean.xingbie.toString())
        }
        if (mJiankangfenxiItemBean.nianling>=0){
            nianlingEt.setText(mJiankangfenxiItemBean.nianling.toString())
        }
        if (mJiankangfenxiItemBean.fenxitu.isNotNullOrEmpty()){
            fenxituIfv.load(this@AddOrUpdateActivity, mJiankangfenxiItemBean.fenxitu)
        }
        if (mJiankangfenxiItemBean.yichangjiance.isNotNullOrEmpty()){
            yichangjianceEt.setText(mJiankangfenxiItemBean.yichangjiance.toString())
        }
        if (mJiankangfenxiItemBean.jiankangbaogao.isNotNullOrEmpty()){
            jiankangbaogaoTv.text =mJiankangfenxiItemBean.jiankangbaogao
        }
        if (mJiankangfenxiItemBean.fenxineirong.isNotNullOrEmpty()){
            fenxineirongEt.setText(mJiankangfenxiItemBean.fenxineirong.toString())
        }
    }
}