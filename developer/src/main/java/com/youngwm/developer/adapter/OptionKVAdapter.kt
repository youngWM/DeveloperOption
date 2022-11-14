package com.youngwm.developer.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youngwm.developer.R
import com.youngwm.developer.data.OptionKV
import com.youngwm.developer.utils.ClipboardUtils

/**
 * 说明：
 * @author  youngwm
 * @date  2022/9/4 15:31
 **/
class OptionKVAdapter
    : BaseQuickAdapter<OptionKV, BaseViewHolder>(R.layout.item_develper_option_child_kv) {

    private val kvMaps = mutableMapOf<String, String>()

    override fun convert(holder: BaseViewHolder, item: OptionKV) {
        holder.getView<TextView>(R.id.keyDes).text = item.des
        holder.getView<TextView>(R.id.key).text = "（${item.key}）"

        val value = holder.getView<EditText>(R.id.value).apply {
            setText(item.value)
            item.inputType?.let { inputType = it }
            kvMaps[item.key] = item.value
            doOnTextChanged { text, _, _, _ ->
                kvMaps[item.key] = text.toString()
            }
        }


        holder.getView<TextView>(R.id.copy).setOnClickListener {
            if (value.text.isNotEmpty()) {
                ClipboardUtils.copyText(it.context, value.text)
                Toast.makeText(it.context, "复制成功！", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(it.context, "输入框不能为空!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun getKVMaps(): Map<String, String> {
        return kvMaps
    }


}
