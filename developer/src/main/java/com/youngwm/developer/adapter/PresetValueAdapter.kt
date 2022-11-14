package com.youngwm.developer.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youngwm.developer.data.PresetValue
import com.youngwm.developer.R

/**
 * 说明：
 * @author  youngwm
 * @date  2022/9/4 15:33
 **/
class PresetValueAdapter :
    BaseQuickAdapter<PresetValue, BaseViewHolder>(R.layout.item_develper_option_child_option_value) {

    override fun convert(holder: BaseViewHolder, item: PresetValue) {
        val name = holder.getView<TextView>(R.id.name)
        name.text = item.name
        name.alpha = if (item.isCheck) 1f else 0.8f
        name.setOnClickListener {
            item.onItemClick?.let { it(item) }
        }

        val closeBtn = holder.getView<Button>(R.id.close)
        closeBtn.visibility = if (item.canDel) View.VISIBLE else View.GONE
        closeBtn.setOnClickListener {
            item.onDelClick?.let { it1 -> it1(item) }
            Toast.makeText(it.context, "点击删除按键", Toast.LENGTH_SHORT).show()
        }
    }



}
