package com.youngwm.developer

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youngwm.developer.adapter.DeveloperOptionAdapter
import com.youngwm.developer.data.DeveloperOption

/**
 * 开发者选项列表
 * 说明：开发过程中需要查看一些本地存储参数，
 * 如：服务器IP+端口，切换正式环境和测试环境；
 *    查看小说阅读时长，更改阅读时长；
 *    获取账号token兵复制；
 *    开发过程中绘制布局作为重复性工作耗时无意义，所以提取常用测试环境中的操作，作为开发者选项，
 *    只需要聚集代码逻辑
 * @author  youngwm
 * @date  2022/9/4 15:02
 **/
class DeveloperOptionList : RecyclerView {

    private val dataMap = mutableMapOf<String, DeveloperOption>()
    private val developerOptionAdapter by lazy { DeveloperOptionAdapter() }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        adapter = developerOptionAdapter
    }

    /**
     * 添加一个开发者选项
     **/
    fun addItem(@NonNull developerOption: DeveloperOption) {
        // 添加重复性标签项，将覆盖旧的开发者选项
        if (dataMap.containsKey(developerOption.label)) {
            dataMap[developerOption.label] = developerOption
            developerOptionAdapter.setList(dataMap.values)
        } else {
            dataMap[developerOption.label] = developerOption
            developerOptionAdapter.addData(developerOption)
        }
    }

    /**
     * 添加开发者选项列表
     **/
    fun addItems(@NonNull list: List<DeveloperOption>) {
        list.forEach {
            addItem(it)
        }
    }

    /**
     * 添加可变数量开发者选项
     **/
    fun addItems(@NonNull vararg list: DeveloperOption) {
        list.forEach {
            addItem(it)
        }
    }

}
