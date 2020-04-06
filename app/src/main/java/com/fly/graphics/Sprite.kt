package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.*

open class Sprite(path:String? = null, asset_manager: AssetManager? = null)//精灵类
{
    private var bitmap: Bitmap? = null//精灵bitmap

    var width:Float = 0f//精灵宽
    var height:Float = 0f//精灵高

    /*序列帧动画*/
    private var render_animation = false//精灵动画是否渲染
    private var render_n:Int = 0//动画渲染次数
    private var render_index = 0//动画渲染索引
    private var rect_list:ArrayList<Rect> = ArrayList<Rect>()//序列帧动画分割坐标宽高？

    init
    {
        /*设置精灵bitmap*/
        if(path != null)
        {
            if (asset_manager == null)
                SetBitmap(path)
            else
                SetBitmap(path,asset_manager)
        }
    }

    /*设置精灵bitmap*/
    fun SetBitmap(bitmap: Bitmap) { this.bitmap = bitmap }
    fun SetBitmap(bitmap_path: String)
    {
        /*加载手机存储的图片*/
        val bitmap = BitmapFactory.decodeFile(bitmap_path)
        this.bitmap = bitmap

        width = bitmap.width.toFloat()
        height = bitmap.height.toFloat()

        /*重置RectList*/
        rect_list.clear()
        rect_list.add(Rect(0,0, width.toInt(), height.toInt()))
    }
    fun SetBitmap(bitmap_path: String,asset_manager:AssetManager)
    {
        /*加载assets的图片*/
        val bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        this.bitmap = bitmap

        width = bitmap.width.toFloat()
        height = bitmap.height.toFloat()

        /*重置RectList*/
        rect_list.clear()
        rect_list.add(Rect(0,0, width.toInt(), height.toInt()))
    }

    /*设置RectList*/
    fun SetSrcRect(rect_list: ArrayList<Rect>) { this.rect_list = rect_list }

    /*获取精灵的各项成员*/
    fun GetBitmap() : Bitmap? { return bitmap }
    fun GetSrcRect() : ArrayList<Rect> { return rect_list }
    fun GetSrcRect(index:Int) : Rect { return rect_list[index] }
    fun GetSrcRectF(index:Int) : RectF { val rectf:RectF = RectF(rect_list[index]);return rectf }

    /*初始化SrcRect即RectList*/
    fun InitSrcRect(rect_list:ArrayList<Rect>) { this.rect_list.clear();this.rect_list = rect_list }
    fun InitSrcRect(start_x:Int,start_y:Int,width:Int,height:Int,horizontal:Int,vertical:Int)
    {
        this.width = width.toFloat()
        this.height = height.toFloat()

        rect_list.clear()

        for (i in 0 until vertical)
        {
            for (j in 0 until horizontal)
            {
                rect_list.add(Rect(start_x + width * j,start_y + height * i,start_x + width * j + width,start_y + height * i + height))
            }
        }
    }

    /*精灵渲染函数*/
    //fun render(canvas: Canvas, renderer:Renderer) { renderer.DrawSprite(canvas,this) }
    fun Render(canvas: Canvas, renderer:Renderer) { renderer.DrawSprite(canvas,this) }
    fun Render(canvas: Canvas, renderer: Renderer,x:Float,y:Float,width: Float = this.width,height: Float = this.height,index:Int = 0)
    { bitmap?.let { renderer.DrawSprite(canvas,this,x, y, width, height, index) } }
    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,x:Float,y:Float,begin_index:Int = 0,end_index:Int = 0,FPS:Int = 0)
    {
        if (!render_animation) { render_index = begin_index;render_animation = true }
        Render(canvas,renderer,x,y,width,height,render_index)
        if (render_n >= FPS) { render_n = 0;render_index++ }
        else if (render_n < FPS) { render_n++ }
        if (render_index > end_index)
            render_animation = false
    }
    /*渲染精灵动画*/
    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,x: Float,y: Float,width: Float = this.width,height: Float = this.height,begin_index:Int = 0,end_index:Int = 0,FPS:Int = 0)
    {
        if (!render_animation) { render_index = begin_index;render_animation = true }
        Render(canvas,renderer,x,y,width,height,render_index)
        if (render_n >= FPS) { render_n = 0;render_index++ }
        else if (render_n < FPS) { render_n++ }
        if (render_index > end_index)
            render_animation = false
    }
}
