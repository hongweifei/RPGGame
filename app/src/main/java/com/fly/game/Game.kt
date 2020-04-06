package com.fly.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.fly.graphic.Camera
import com.fly.graphic.SceneRenderer
import com.fly.rpggame.R
import kotlinx.android.synthetic.main.game.*


class Game : Activity() , View.OnTouchListener
{
    var camera: Camera = Camera(0f,0f)
    var renderer : SceneRenderer = SceneRenderer(null,camera)

    var width:Float = 0f
    var height:Float = 0f

    var touch_x:Float = 0f
    var touch_y:Float = 0f

    val dm = DisplayMetrics()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        //width = dm.widthPixels // 屏幕宽度（像素）
        //height = dm.heightPixels // 屏幕高度（像素）

        //val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        //val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）

        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //width = (width / density).toInt() // 屏幕宽度(dp)
        //height = (height / density).toInt() // 屏幕高度(dp)

        setContentView(R.layout.game)
        InitGame()
        Game()
    }

    @SuppressLint("SetTextI18n")
    fun InitGame()
    {
        width = 16f
        height = 9f

        scene.SetSceneRenderer(renderer);
        renderer.SetDisplay {
        }
    }

    fun Game()
    {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean
    {
        if (event?.action == MotionEvent.ACTION_DOWN)
        {
            touch_x = event.rawX
            touch_y = event.rawY
            when (v?.id)
            {

            }
        }
        if (event?.action == MotionEvent.ACTION_UP)
        {
            when(v?.id)
            {

            }
        }

        return true
    }

}
