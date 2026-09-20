package com.design.appproject.ui.jiankangxinxi
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.JiankangxinxiItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 健康信息适配器列表
 */
class ListAdapter : LoadMoreAdapter<JiankangxinxiItemBean>(R.layout.jiankangxinxi_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: JiankangxinxiItemBean) {
        holder.setText(R.id.shengao_tv, item.shengao.toString())
        holder.setText(R.id.tizhong_tv, item.tizhong.toString())
        holder.setText(R.id.xuexing_tv, item.xuexing.toString())
        holder.setText(R.id.lurushijian_tv, item.lurushijian.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("jiankangxinxi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("jiankangxinxi","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("jiankangxinxi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("jiankangxinxi","删除"))
        }
    }
}