package com.design.appproject.ui.tongzhijingbao
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.TongzhijingbaoItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 通知警报适配器列表
 */
class ListAdapter : LoadMoreAdapter<TongzhijingbaoItemBean>(R.layout.tongzhijingbao_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: TongzhijingbaoItemBean) {
        holder.setText(R.id.zhanghao_tv, item.zhanghao.toString())
        holder.setText(R.id.xingming_tv, item.xingming.toString())
        holder.setText(R.id.biaoti_tv, item.biaoti.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("tongzhijingbao","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("tongzhijingbao","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("tongzhijingbao","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("tongzhijingbao","删除"))
        }
    }
}