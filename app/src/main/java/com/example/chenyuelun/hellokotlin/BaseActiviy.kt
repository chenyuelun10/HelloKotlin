package com.example.chenyuelun.hellokotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.utils.LogUtils


/**
 *　　　　　　　 ┏┓　 ┏┓+ +
 *　　　　　　　┏┛┻━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　┃ 　
 *　　　　　　　┃　　　━　　 ┃ ++ + + +
 *　　　　　　 ████━████  ┃+
 *　　　　　　　┃　　　　　　　┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　┃ + +
 *　　　　　　　┗━┓　　　┏━┛
 *　　　　　　　　 ┃　　　┃　　　　　　　　　　　
 *　　　　　　　　 ┃　　　┃ + + + +
 *　　　　　　　　 ┃　　　┃　　　　Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　　 ┃　　　┃ + 　　　　神兽保佑,代码无bug　　
 *　　　　　　　　 ┃　　　┃
 *　　　　　　　　 ┃　　　┃　　+　　　　　　　　　
 *　　　　　　　　 ┃　 　 ┗━━━┓ + +
 *　　　　　　　　 ┃ 　　　　   ┣┓
 *　　　　　　　　 ┃ 　　　　　 ┏┛
 *　　　　　　　　 ┗┓┓┏━┳┓┏┛ + + + +
 *　　　　　　　　  ┃┫┫ ┃┫┫
 *　　　　　　　　  ┗┻┛ ┗┻┛+ + + +
 *
 * Created by ChenYuelun on 2017/12/2.
 *
 * 说明：
 */
open class BaseActiviy(var mContext: Context) : AppCompatActivity() {
    val firstEnterInto = 0
    val noNetMaskOpen = 1
    val noNetMaskClose = 2
    val firstSendHttpNoBack = 3

    open var isNetNecessary = true //是否需要联网 在super.onCreate()之前调用

    open var mPagename = ""

    private lateinit var noDataView : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mPagename = this::class.simpleName!!//当前类名
        LogUtils.d("thisPageName",mPagename)

        //初始化无网视图
        initNetInvalidView()
        //初始化正在加载视图
        initLoadingView()
        //初始化无数据页面
        initNoDataView()
        //需要联网获取数据
        if (isNetNecessary) {
            showOrHideLoadingView(true)
        }
    }

    fun initNetInvalidView(){
        noDataView = View.inflate(this, R.layout.view_no_data, null)
//        noDataView.setLayoutParams(getLayoutParams())
    }

    fun initLoadingView(){

    }

    fun initNoDataView(){

    }

    fun showOrHideLoadingView(showLoding :Boolean){

    }



}