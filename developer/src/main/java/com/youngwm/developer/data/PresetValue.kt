package com.youngwm.developer.data

/**
 * 可选项 OptionKV 的预设值
 * @param name 预设值的名称
 * @param isCheck 是否选中，即：当前输入框的值全部与预设值相同则为true。todo 此功能待开发
 * @param canDel 是否可以删除，todo 此功能待开发
 **/
data class PresetValue(
    val name: String,
    var isCheck: Boolean = false,
    var canDel: Boolean = false
) {
    /**
     * 开发者选项的键值对列表，保存的value为预设值
     */
    var optionKVs: MutableList<OptionKV> = mutableListOf()

    /**
     * 开发者选项的键值对列表，映射为key-value的map对象
     */
    val dataMaps = mutableMapOf<String, String>()

    /**
     * 开发者选项的预设值项-点击item回调
     */
    var onItemClick: ((PresetValue) -> Unit?)? = null

    /**
     * todo 开发者选项的预设值项-点击删除回调，功能待开发
     */
    var onDelClick: ((PresetValue) -> Unit?)? = null

    /**
     * 添加开发者选项键值对-作为预设值
     */
    fun addOptionKvs(vararg optionKV: OptionKV): PresetValue {
        optionKV.forEach {
            optionKVs.add(it)
            dataMaps.putAll(it.getKVMap())
        }
        return this
    }


}
