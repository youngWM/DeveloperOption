package com.youngwm.developer.adapter

import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youngwm.developer.R
import com.youngwm.developer.data.DeveloperOption

/**
 * 说明：
 * @author  youngwm
 * @date  2022/9/4 15:03
 **/
class DeveloperOptionAdapter :
    BaseQuickAdapter<DeveloperOption, BaseViewHolder>(R.layout.item_develper_option) {

    override fun convert(holder: BaseViewHolder, item: DeveloperOption) {

        // 序号
        holder.getView<TextView>(R.id.num).text = "".plus(holder.layoutPosition + 1)
        // 开发项名称
        holder.getView<TextView>(R.id.name).text = item.label

        // 显示项key-value列表
        val kvRv = holder.getView<RecyclerView>(R.id.kvRv)
        val kvAdapter = OptionKVAdapter()
        kvRv.adapter = kvAdapter
        // 设置 显示项默认值
        kvAdapter.setNewInstance(item.defaultValueList)
        kvRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        // 初始值更新时，更新显示
        item.onInitDataChange = {
            kvAdapter.setNewInstance(it.toMutableList())
        }

        // 设置 预设值 列表
        val presetValueRv = holder.getView<RecyclerView>(R.id.valueRv)
        val presetValueAdapter = PresetValueAdapter()
        presetValueRv.adapter = presetValueAdapter
        presetValueRv.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        presetValueAdapter.setNewInstance(item.presetValueList)

        // 预设值选项点击事件
        item.presetValueList.forEach { presetValue ->
            presetValue.onItemClick = {
                val newData = it.optionKVs.toMutableList()
                // 更新界面显示数据未预设值
                kvAdapter.setNewInstance(newData)
                // 回调保存事件
                item.onSaveClick?.let { it(presetValue.dataMaps) }
            }
        }

        // 保存
        val saveBtn = holder.getView<TextView>(R.id.save)
        saveBtn.setOnClickListener {
            item.onSaveClick?.let { onSaveClick ->
                val kvMap = kvAdapter.getKVMaps()
                // 如果设置过滤空值，且存在空值项，则toast提示空值项，同时不回调保存事件
                if (item.filterEmpty) {
                    item.defaultValueList.forEach {
                        if (kvMap[it.key].isNullOrEmpty()) {
                            Toast.makeText(context, "${it.des}不能为空!", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                }
                onSaveClick(kvMap)
            }
        }

        // 重置 = 恢复初始值 + 回调保存功能
        val resetBtn = holder.getView<TextView>(R.id.reset)
        resetBtn.setOnClickListener {
            // 若不请求焦点，点击充值按键，recyclerView将滚动到有焦点的编辑框中
            resetBtn.requestFocus()
            kvAdapter.setNewInstance(item.defaultValueList)
            // adapter 中list data 保持着初始传入数值，刷新即可恢复默认值
//            kvAdapter.notifyDataSetChanged()
            item.onSaveClick?.let { it(item.defaultValueMaps) }
            item.onResetClick?.let { it(item.defaultValueMaps) }
        }

    }

}
