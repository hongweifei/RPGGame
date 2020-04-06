package com.fly.animation

import android.graphics.Canvas
import com.fly.graphic.Renderer

class Animator//动画控制器
{
    private var animation_list:ArrayList<Animation> = ArrayList<Animation>()//动画列表

    /*添加动画*/
    fun AddAnimation(animation: Animation) { animation_list.add(animation) }

    /*渲染动画*/
    fun RenderAnimation(canvas: Canvas, renderer: Renderer, FPS:Int, x:Float, y:Float,index:Int) { animation_list[index].Render(canvas, renderer, FPS, x, y) }
    fun RenderAnimation(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float,width:Float,height:Float,index: Int)
    { animation_list[index].Render(canvas, renderer, FPS, x, y,width, height) }
}
