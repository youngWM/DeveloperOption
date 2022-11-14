package com.youngwm.developer.data

/**
 * 开发者选项包含的键值对
 * @param des 键值对释义
 * @param key
 * @param value
 * @param inputType 设置编辑value的输入类型
 */
data class OptionKV(
    /*键值对描述*/
    val des: String,
    val key: String,
    var value: String,
    val inputType: Int? = null
) {


    constructor(des: String, key: String, value: String) : this(des, key, value, null)


    fun getKVMap(): Map<String, String> {
        return mapOf(pair = Pair(key, value))
    }

    fun copy(newValue: String): OptionKV {
        return OptionKV(des, key, value = newValue, inputType)
    }
}
