package com.fly.animation

import android.content.res.AssetManager
import android.graphics.*
import com.fly.graphic.Renderer

class Animation//动画类
{
    private var bitmap:ArrayList<Bitmap> = ArrayList<Bitmap>()//动画bitmap列表
    private var frame:Int = 0//动画帧数量
    private var index:Int = 0//动画渲染帧索引
    private var draw_n:Int = 0//动画绘制次数

    /*给动画添加帧*/
    fun AddFrame(bitmap: Bitmap) { this.bitmap.add(bitmap);frame++ }//添加bitmap
    fun AddFrame(bitmap: Bitmap,index:Int) { this.bitmap.add(index,bitmap);frame++ }//插入bitmap
    fun AddFrame(path:String) { val bitmap:Bitmap = BitmapFactory.decodeFile(path);this.bitmap.add(bitmap);frame++ }//加载并添加手机存储中的图片
    fun AddFrame(path:String,index: Int) { val bitmap:Bitmap = BitmapFactory.decodeFile(path);this.bitmap.add(index,bitmap);frame++ }//加载并插入手机存储中的图片
    fun AddFrame(path:String,asset_manager:AssetManager) { val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path));this.bitmap.add(bitmap);frame++ }//加载并添加assets中的图片
    fun AddFrame(path:String,asset_manager:AssetManager,index: Int) { val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path));this.bitmap.add(index,bitmap);frame++ }//加载并插入assets中的图片

    /*给动画添加帧，剪切后的图片*/
    fun AddFrame(bitmap: Bitmap,src: Rect)
    {
        val b = Bitmap.createBitmap(bitmap,src.left,src.top,src.right - src.left,src.bottom - src.top,null,false)
        this.bitmap.add(b)
        frame++
    }
    fun AddFrame(bitmap: Bitmap,x:Int,y:Int,width:Int,height:Int)
    {
        val b = Bitmap.createBitmap(bitmap,x, y, width, height,null,false)
        this.bitmap.add(b);
        frame++
    }
    fun AddFrame(path:String,asset_manager:AssetManager,src:Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path))
        val b = Bitmap.createBitmap(bitmap,src.left,src.top,src.right - src.left,src.bottom - src.top,null,false)
        this.bitmap.add(b)
        frame++
    }
    fun AddFrame(path:String,asset_manager:AssetManager,x:Int,y:Int,width:Int,height:Int)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path))
        val b = Bitmap.createBitmap(bitmap,x, y, width, height,null,false)
        this.bitmap.add(b)
        frame++
    }

    /*渲染动画*/
    fun Render(canvas: Canvas,renderer: Renderer,frame:Int,x:Float,y:Float)
    {
        if (index > this.frame)//若动画帧索引 > 动画帧数量
            index = 0//动画帧索引=0，重新开始动画
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,x + bitmap[index].width.toFloat(),y + bitmap[index].height.toFloat()));//绘制图片
        if (draw_n >= frame) { draw_n = 0;index++ }//绘制次数 >= 切换帧所需绘制次数，切下一张图，重置绘制次数
        else if(draw_n < frame) { draw_n++ }//否则，绘制次数++
    }
    fun Render(canvas: Canvas, renderer: Renderer, frame:Int, x:Float, y:Float, width:Float, height:Float)
    {
        if (index > this.frame)//若动画帧索引 > 动画帧数量
            index = 0//动画帧索引=0，重新开始动画
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,x + width,y + height));//绘制图片
        if (draw_n >= frame) { draw_n = 0;index++ }//绘制次数 >= 切换帧所需绘制次数，切下一张图，重置绘制次数
        else if(draw_n < frame) { draw_n++ }//否则，绘制次数++
    }
}
