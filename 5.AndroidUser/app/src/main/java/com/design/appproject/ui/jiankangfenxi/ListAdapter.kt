package com.design.appproject.ui.jiankangfenxi
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.JiankangfenxiItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 健康分析适配器列表
 */
class ListAdapter : LoadMoreAdapter<JiankangfenxiItemBean>(R.layout.jiankangfenxi_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: JiankangfenxiItemBean) {
        holder.setText(R.id.xingming_tv, item.xingming.toString())
        holder.setText(R.id.xingbie_tv, item.xingbie.toString())
        holder.setText(R.id.nianling_tv, item.nianling.toString())
        holder.setText(R.id.fenxiriqi_tv, item.fenxiriqi.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("jiankangfenxi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("jiankangfenxi","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("jiankangfenxi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("jiankangfenxi","删除"))
        }
    }
}