package com.youngwm.developeroption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import com.youngwm.developer.DeveloperOptionList
import com.youngwm.developer.data.DeveloperOption
import com.youngwm.developer.data.OptionKV
import com.youngwm.developer.data.PresetValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDeveloperOptions()
    }

    private fun initDeveloperOptions() {
        val developerOptionList = findViewById<DeveloperOptionList>(R.id.developerList)
        // 创建一个开发者选项
        val host = DeveloperOption("服务器地址")

        // 开发者选项 添加初始/默认参数
        val keyHost = "key_host"
        val keyChannel = "key_channel"
        val kv1 = OptionKV("host", keyHost, "http://192.168.168.1:8080")
        val kv2 = OptionKV("渠道号", keyChannel, "11", InputType.TYPE_CLASS_NUMBER)
        // 添加显示默认值
        host.addDefaultOption(kv1, kv2)

        // 可选项1，开发环境对应的预设值
        val dev = PresetValue("开发", false, false)
            .addOptionKvs(
                kv1.copy("http://192.168.168.5:9091"),
                kv2.copy("1")
            )
        // 添加可选项1
        host.addOptionalValue(dev)

        // 可选项2，正式环境对应的预设值
        val release = PresetValue("生产", false, false)
            .addOptionKvs(
                kv1.copy("https://www.release.com"),
                kv2.copy("2")
            )
        // 添加可选项2
        host.addOptionalValue(release)

        // 保存点击回调，仅当数值不为空时才回调
        host.setOnSaveClickListenerFilterEmpty { map ->
            val url = map[keyHost]
            val channel = map[keyChannel]
            Toast.makeText(this, "当前数值为：\n$url \n$channel", Toast.LENGTH_SHORT).show()
            // saveEnvConfig(url, channel)
        }
        // 重置事件回调，默认将执行2个操作：重置为默认值，并回调点击事件
        host.onResetClick = {
            Toast.makeText(this, "重置成功", Toast.LENGTH_SHORT).show()
        }

        // 开发者选项列表添加选项
        developerOptionList.addItem(host)
    }
}
