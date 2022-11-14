package com.youngwm.developer.data

/**
 * 说明：开发者选项。包含：
 * 1、标签（label）；
 * 2、键值对（OptionKV）列表；
 * 3、可选预设项（PresetValue）列表（对序号2的键值对的预设值）；
 * 4、保存按键；
 * 5、重置按键；
 *
 * 举例：如下服务器地址开发者选项，开发过程中经常需要切换正式环境、测试环境，有时还需要连接开发人员环境，
 * 需要编辑ip和端口号。
 *

!—————————————————————————————————————————!
! 【1】服务器地址                            !
!  host（key_host）             copy       !
!  !————————————————————————————————!     !
!  ! http://192.168.168.1:8080      !     !
!  !————————————————————————————————!     !
!  渠道号（key_channel）          copy      !
!  !————————————————————————————————!     !
!  ! 11                             !     !
!  !————————————————————————————————!     !
!   生产  开发               重置  保存      !
!—————————————————————————————————————————!


 * ***********************  java code start ******************************************
 *
 *    // 创建一个开发者选项
 *    DeveloperOption host = new DeveloperOption("服务器地址");
 *
 *    // 开发者选项 添加初始/默认参数
 *    String keyHost = "key_host";
 *    String keyChannel = "key_channel";
 *    OptionKV kv1 = new OptionKV("host", keyHost, "http://192.168.168.1:8080");
 *    OptionKV kv2 = new OptionKV("渠道号", keyChannel, "11", InputType.TYPE_CLASS_NUMBER);
 *    // 添加显示默认值
 *    host.addDefaultOption(kv1, kv2);
 *
 *    // 可选项1，开发环境对应的预设值
 *    PresetValue dev = new PresetValue("开发", false, false)
 *    .addOptionKvs(
 *    kv1.copy("http://192.168.168.5:9091"),
 *    kv2.copy("1")
 *    );
 *    // 添加可选项1
 *    host.addOptionalValue(dev);
 *
 *    // 可选项2，正式环境对应的预设值
 *    PresetValue release = new PresetValue("生产", false, false)
 *    .addOptionKvs(
 *    kv1.copy("https://www.release.com"),
 *    kv2.copy("2")
 *    );
 *    // 添加可选项2
 *    host.addOptionalValue(release);
 *
 *    // 保存点击回调，仅当数值不为空时才回调
 *    host.setOnSaveClickListenerFilterEmpty(map -> {
 *    String url = map.get(keyHost);
 *    String channel = map.get(keyChannel);
 *    // saveEnvConfig(url, channel);
 *    return null;
 *    });
 *    // 重置事件回调，默认将执行2个操作：重置为默认值，并回调点击事件
 *    host.setOnResetClick(map -> {
 *    return null;
 *    });
 *
 *    // 开发者选项列表添加选项
 *    developerOptionList.addItem(host);
 *
 * ***********************  java code end ******************************************
 *
 * @author  youngwm
 * @param label 开发者选项的标签，作为开发者选项的唯一id
 * @date  2022/9/4 15:04
 **/
open class DeveloperOption(
    val label: String
    ) {

    /**
     * 默认值列表
     * */
    var defaultValueList: MutableList<OptionKV> = mutableListOf()

    /**
     * 默认值列表映射的map对象
     * */
    var defaultValueMaps = mutableMapOf<String, String>()

    /**
     * 可选项列表
     * */
    var presetValueList: MutableList<PresetValue> = mutableListOf()

    /**
     * 保存按键回调
     * */
    var onSaveClick: ((Map<String, String>) -> Unit)? = null

    /**
     * 重置按键回调，点击重置，将使用 defaultValueList 中数值，并调用 onSaveClick（如果有实现的话）
     **/
    var onResetClick: ((Map<String, String>) -> Unit)? = null

    /*
    * 保存按键回调
    * */
    var onInitDataChange: ((List<OptionKV>) -> Unit)? = null

    /**
     * 保存按键回调事件是否处过滤值为空的情况，默认为 false，若设置为true，开发者选项若存在为空情况，
     * 不会回调保存监听事件，同时提示toast提示对应参数值为空
     * */
    var filterEmpty: Boolean = false

    /*
    * 添加显示默认值
    * */
    fun addDefaultOption(vararg kvs: OptionKV): DeveloperOption {
        kvs.forEach {
            defaultValueMaps[it.key] = it.value
            defaultValueList.add(it)
        }
        return this
    }

    /**
     * 添加选项可选值/预设值
     * */
    fun addOptionalValue(value: PresetValue): DeveloperOption {
        presetValueList.add(value)
        return this
    }

    /**
     * 添加选项可选值/预设值
     * */
    fun addOptionalValues(vararg values: PresetValue): DeveloperOption {
        values.forEach {
            addOptionalValue(it)
        }
        return this
    }

    fun setOnSaveClickListenerFilterEmpty(
        onClick: (values: Map<String, String>) -> Unit
    ) {
        filterEmpty = true
        onSaveClick = onClick
    }

    /**
     * 更新初始值，有些情况下，例如：展示第三方消息推送（Google FCM）的token，
     * 参数值是从服务端获取，初始化界面时还未获取到，需等待加载成功后再设置。
     * 本方法适用于此类情况。
     *
     * */
    fun updateInitData(key: String, valueNew: String) {
        defaultValueMaps[key] = valueNew
        defaultValueList.forEach {
            if (it.key == key) it.value = valueNew
        }
        onInitDataChange?.let { it(defaultValueList) }
    }

}
