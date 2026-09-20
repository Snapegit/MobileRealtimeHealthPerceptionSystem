package com.design.appproject.ui.gexinghuajianyi
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.GexinghuajianyiItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 个性化建议适配器列表
 */
class ListAdapter : LoadMoreAdapter<GexinghuajianyiItemBean>(R.layout.gexinghuajianyi_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: GexinghuajianyiItemBean) {
        holder.setText(R.id.zhanghao_tv, item.zhanghao.toString())
        holder.setText(R.id.xingming_tv, item.xingming.toString())
        holder.setText(R.id.jianyimingcheng_tv, item.jianyimingcheng.toString())
        holder.setText(R.id.jianyishijian_tv, item.jianyishijian.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("gexinghuajianyi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("gexinghuajianyi","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("gexinghuajianyi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("gexinghuajianyi","删除"))
        }
    }
}