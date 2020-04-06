package com.fly.graphic


import android.content.res.AssetManager
import android.graphics.*
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.View.LAYER_TYPE_SOFTWARE


open class Renderer()//渲染器
{
    private var render : (canvas : Canvas) -> Unit = {};//渲染器渲染事件
    protected var render_time:Long = 0//渲染时间
    protected var paint : Paint = Paint()//画笔
    private var layer_type = LAYER_TYPE_HARDWARE

    /*
    protected var obj_last_x:Float? = null//物体上次X
    protected var obj_last_y:Float? = null//物体上次Y
    protected var obj_next_y:Float? = null//物体下次Y
    */

    init
    {
        paint.strokeWidth = 2f//画笔宽度
        paint.isAntiAlias = true//画笔抗锯齿
        paint.color = Color.BLACK//画笔颜色
        paint.style = Paint.Style.FILL_AND_STROKE;//画笔样式

        paint.textAlign = Paint.Align.LEFT;//设置文字对齐方式  取值：align.CENTER、align.LEFT或align.RIGHT 默认align.LEFT
        paint.textSize = 12F;//设置文字大小
        paint.isFakeBoldText = false;//设置是否为粗体文字
        paint.isUnderlineText = false;//设置下划线
        paint.textSkewX = 0f;//设置字体水平倾斜度  普通斜体字是-0.25
        paint.textScaleX = 1f;//只会将水平方向拉伸  高度不会变
    }

    /*设置渲染器渲染事件*/
    fun SetDisplay(Display: (canvas : Canvas) -> Unit) { render = Display }
    /*渲染器渲染事件*/
    fun Display(canvas: Canvas)
    {
        val begin_time = System.currentTimeMillis();
        render(canvas);
        render_time = System.currentTimeMillis() - begin_time
    }

    /*设置画笔各项属性*/
    fun SetStrokeWidth(stroke_width:Float) { paint.strokeWidth = stroke_width }
    fun SetAntiAlias(aa:Boolean) { paint.isAntiAlias = aa }
    fun SetColor(color:Int) { paint.setColor(color) }
    fun SetAlpha(a:Int) { paint.alpha = a }//设置透明程度
    fun SetStyle(style:Paint.Style) { paint.style = style }
    fun SetShader(shader: Shader) { paint.setShader(shader) }
    fun SetShadowLayer(radius:Float,dx:Float,dy:Float,shadow_color:Int) { paint.setShadowLayer(radius,dx,dy,shadow_color);layer_type = LAYER_TYPE_SOFTWARE }

    /*获取渲染器成员*/
    fun GetHeight(canvas: Canvas) : Int { return canvas.height }
    fun GetWidth(canvas: Canvas) : Int { return canvas.width }
    fun GetPaint() : Paint { return paint }
    fun GetLayerType() : Int { return layer_type }
    fun GetRenderTime() : Long { return render_time }

    /*各种绘制函数*/
    open fun DrawPoint(canvas: Canvas, x:Float, y:Float){ canvas.drawPoint(x,y,paint) }
    open fun DrawPoints(canvas: Canvas, pts:FloatArray) { canvas.drawPoints(pts,paint) }

    open fun DrawLine(canvas: Canvas, start_x:Float, start_y:Float, stop_x:Float, stop_y:Float) { canvas.drawLine(start_x,start_y,stop_x,stop_y,paint) }
    open fun DrawPath(canvas: Canvas, path:Path) { canvas.drawPath(path,paint) }

    open fun DrawRect(canvas: Canvas, left:Float, top:Float, right:Float, bottom:Float) { canvas.drawRect(left, top, right, bottom, paint) }
    open fun DrawRect(canvas: Canvas, rect:Rect) { canvas.drawRect(rect, paint) }
    open fun DrawRect(canvas: Canvas, rect:RectF) { canvas.drawRect(rect, paint) }
    open fun DrawRect(canvas: Canvas, x:Int, y:Int, width: Int, height: Int) { canvas.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(),paint) }

    open fun DrawRegion(canvas: Canvas, region:Region)
    {
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect)) { canvas.drawRect(rect, paint) }
    }

    /*绘制文本*/
    open fun DrawText(canvas: Canvas, text:String, x:Float, y:Float) { canvas.drawText(text,x,y,paint) }

    /*绘图函数*/
    open fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, matrix: Matrix) { canvas.drawBitmap(bitmap,matrix,paint) }
    open fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, src: Rect? = null, dst: Rect) { canvas.drawBitmap(bitmap,src,dst,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap: Bitmap,src: Rect? = null,dst: RectF) { canvas.drawBitmap(bitmap,src,dst,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap:Bitmap,left:Float,top:Float) { canvas.drawBitmap(bitmap,left,top,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,matrix,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,src: Rect? = null,dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,src: Rect? = null,dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,left,top,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,matrix,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,src: Rect? = null,dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,src: Rect? = null,dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,left,top,paint)
    }

    /*绘制精灵*/
    open fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Float = sprite.width, height:Float = sprite.height, index:Int = 0)
    {
        val dst:RectF = RectF(x,y,x + width,y + height)//精灵绘制坐标及宽高
        sprite.GetBitmap()?.let { canvas.drawBitmap(it,sprite.GetSrcRect(index),dst,paint) }//绘制精灵bitmap
    }
    open fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Float = sprite.width, height:Float = sprite.height, index:Int = 0, flip_x:Float = 1f, flip_y: Float = 1f)
    {
        val dst:RectF = RectF(x,y,x + width,y + height)
        val matrix:Matrix = Matrix()

        matrix.setScale(flip_x,flip_y)
        matrix.postTranslate(x, y)
        matrix.setRectToRect(sprite.GetSrcRectF(index),dst,null)

        sprite.GetBitmap()?.let { canvas.drawBitmap(it,matrix,paint) }
    }

    /*绘制物体*/
    open fun DrawObject(canvas: Canvas, obj: Object, width: Float = obj.width, height: Float = obj.height, index: Int = 0)
    {
        //obj_last_x = obj.x
        //obj_last_y = obj.y
        /*
        if (obj.GetRigid() != null)
        {
            if (obj.GetCollisionBox() != null)
            {
                obj.GetCollisionBox()!!.SetRect(RectF(obj.x,obj.y,obj.x + width,obj.y + height))
                if (!obj.GetCollisionBox()!!.Collision())
                {
                    val will_y = obj.y + obj.GetRigid()!!.GetDropHeight(render_time)
                    obj.GetCollisionBox()!!.SetRect(RectF(obj.x,will_y,obj.x + width,will_y + height))
                    if (obj.GetCollisionBox()!!.Collision())
                    {
                        if (will_y + height > obj.GetCollisionBox()!!.GetCollisionRect().top)
                        {
                            if (!(will_y + height < obj.GetCollisionBox()!!.GetCollisionRect().top + 10f))
                                obj.y -= will_y + height - obj.GetCollisionBox()!!.GetCollisionRect().top
                            else
                                obj_next_y = obj.GetCollisionBox()!!.GetCollisionRect().top - height
                        }
                    }
                    else
                    {
                        //obj.GetCollisionBox()!!.SetRect(RectF(obj.x,obj.y,obj.x + width,obj.y + height))
                        for (i in 0 until obj.GetCollisionBox()!!.GetAllCollisionRect().size)
                        {
                            if (obj.y > obj.GetCollisionBox()!!.GetAllCollisionRect()[i].bottom && obj_last_y!! + height < obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top)
                            {
                                obj.y = obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top - height
                                return
                            }
                        }
                        obj.y += obj.GetRigid()!!.GetDropHeight(render_time)
                    }
                }
            }
            else
                obj.y += obj.GetRigid()!!.GetDropHeight(render_time)
        }
        */
        obj.GetSprite()?.let { DrawSprite(canvas, it,obj.x,obj.y,width,height,index) }//绘制物体精灵
        /*
        if (obj_next_y != null)
            obj.y = obj_next_y!!
        obj_next_y = null
        */
    }

}
