package com.youngwm.developer.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * 说明：
 * @author  youngwm
 * @date  2022/11/14 10:28
 **/
object ClipboardUtils {

    fun copyText(context: Context, text: CharSequence?) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(context.packageName, text))
    }

}
