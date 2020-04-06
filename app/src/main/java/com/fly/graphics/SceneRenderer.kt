package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.*
import android.util.Log

class SceneRenderer(scene: Scene? = null,private var camera: Camera = Camera()) : Renderer()//场景渲染器，继承渲染器
{
    /*场景宽高比*/
    private var width_ratio = scene?.GetSceneWidthRatio() ?: 1f
    private var height_ratio = scene?.GetSceneHeightRatio() ?: 1f

    /*设置场景宽高比及摄像机*/
    fun SetWidthRatio(width_ratio:Float){ this.width_ratio = width_ratio }
    fun SetHeightRatio(height_ratio:Float){ this.height_ratio = height_ratio }
    fun SetCamera(camera: Camera) { this.camera = camera }
    /*获取摄像机*/
    fun GetCamera() : Camera { return camera }

    /*场景渲染器绘制方法，绘制坐标皆与摄像机坐标相关*/
    override fun DrawPoint(canvas: Canvas, x:Float, y:Float){ canvas.drawPoint((x - camera.look_at_x) * width_ratio,(y - camera.look_at_y) * height_ratio,paint) }
    override fun DrawPoints(canvas: Canvas, pts:FloatArray)
    {
        val p = FloatArray(pts.size)
        for (i in 0 until pts.size step 2)
            p.set(i,(pts[i] - camera.look_at_x) * width_ratio)
        for (i in 1 until pts.size step 2)
            p.set(i,(pts[i] - camera.look_at_y) * height_ratio)

        canvas.drawPoints(p,paint)
    }

    override fun DrawLine(canvas: Canvas,start_x:Float,start_y:Float,stop_x:Float,stop_y:Float)
    { canvas.drawLine((start_x - camera.look_at_x) * width_ratio,(start_y - camera.look_at_y) * height_ratio,
        (stop_x - camera.look_at_x) * width_ratio,(stop_y - camera.look_at_y) * height_ratio,paint) }

    //override fun DrawPath(canvas: Canvas,path: Path) { canvas.drawPath(path,super.GetPaint()) }

    override fun DrawRect(canvas: Canvas, left:Float, top:Float, right:Float, bottom:Float)
    { canvas.drawRect((left - camera.look_at_x) * width_ratio, (top - camera.look_at_y) * height_ratio,
        (right - camera.look_at_x) * width_ratio, (bottom - camera.look_at_y) * height_ratio, paint) }
    override fun DrawRect(canvas: Canvas, rect: Rect)
    {
        canvas.drawRect(Rect(((rect.left - camera.look_at_x) * width_ratio).toInt(), ((rect.top - camera.look_at_y) * height_ratio).toInt(),
            ((rect.right - camera.look_at_x) * width_ratio).toInt(), ((rect.bottom - camera.look_at_y) * height_ratio).toInt()), paint)
    }
    override fun DrawRect(canvas: Canvas, rect: RectF)
    {
        canvas.drawRect(RectF((rect.left - camera.look_at_x) * width_ratio, (rect.top - camera.look_at_y) * height_ratio,
            (rect.right - camera.look_at_x) * width_ratio, (rect.bottom - camera.look_at_y) * height_ratio), paint)
    }
    override fun DrawRect(canvas: Canvas, x:Int, y:Int, width: Int, height: Int)
    {
        canvas.drawRect((x.toFloat() - camera.look_at_x) * width_ratio, (y.toFloat() - camera.look_at_y) * height_ratio,
            ((x + width).toFloat() - camera.look_at_x) * width_ratio, ((y + height).toFloat() - camera.look_at_y) * height_ratio,paint)
    }

    override fun DrawRegion(canvas: Canvas, region: Region)
    {
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect))
        {
            canvas.drawRect(Rect(((rect.left - camera.look_at_x) * width_ratio).toInt(), ((rect.top - camera.look_at_y) * height_ratio).toInt(),
                ((rect.right - camera.look_at_x) * width_ratio).toInt(), ((rect.bottom - camera.look_at_y) * height_ratio).toInt()), paint)
        }
    }

    /*绘制文本*/
    override fun DrawText(canvas: Canvas, text:String, x:Float, y:Float) { canvas.drawText(text,x - camera.look_at_x,y - camera.look_at_y,paint) }

    /*绘图方法*/
    //override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, matrix: Matrix) { canvas.drawBitmap(bitmap,matrix,super.GetPaint()) }
    override fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, src: Rect?, dst: Rect)
    {
        val d = Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(),((dst.top - camera.look_at_y) * height_ratio).toInt()
            ,((dst.right - camera.look_at_x) * width_ratio).toInt(),((dst.bottom - camera.look_at_y) * height_ratio).toInt())
        canvas.drawBitmap(bitmap,src,d,paint)

        /*
        super.DrawBitmap(canvas, bitmap, src,
                    Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
         */
    }
    override fun DrawBitmap(canvas: Canvas,bitmap: Bitmap,src: Rect?,dst: RectF)
    {
        val d = RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
            (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio)
        canvas.drawBitmap(bitmap,src,d,paint)

        /*
        super.DrawBitmap(canvas, bitmap, src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
         */
    }
    override fun DrawBitmap(canvas: Canvas,bitmap:Bitmap,left:Float,top:Float) { canvas.drawBitmap(bitmap,left - camera.look_at_x,top - camera.look_at_y,paint) }
    /*
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,matrix,super.GetPaint())
    }
    */
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, src: Rect?, dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        val d = Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
            ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt())
        canvas.drawBitmap(bitmap,src,d,paint)
        /*
        super.DrawBitmap(canvas, bitmap_path, src,
                Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
         */
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, src: Rect?, dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        val d = RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
            (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio)
        canvas.drawBitmap(bitmap,src,d,paint)
        /*
        super.DrawBitmap(canvas,bitmap_path,src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
         */
    }
    override fun DrawBitmap(canvas: Canvas,bitmap_path: String,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio,paint)
        //super.DrawBitmap(canvas,bitmap_path,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio)
    }
    /*
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,matrix,super.GetPaint())
    }
    */
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        val d = Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
            ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt())
        canvas.drawBitmap(bitmap,src,d,paint)
        /*
        super.DrawBitmap(canvas, bitmap_path, asset_manager, src,
                Rect(((dst.left - camera.look_at_x) * width_ratio).toInt(), ((dst.top - camera.look_at_y) * height_ratio).toInt(),
                    ((dst.right - camera.look_at_x) * width_ratio).toInt(), ((dst.bottom - camera.look_at_y) * height_ratio).toInt()))
         */
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, src: Rect?, dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        val d = RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
            (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio)
        canvas.drawBitmap(bitmap,src,d,paint)
        /*
        super.DrawBitmap(canvas, bitmap_path, asset_manager, src,
                RectF((dst.left - camera.look_at_x) * width_ratio, (dst.top - camera.look_at_y) * height_ratio,
                    (dst.right - camera.look_at_x) * width_ratio, (dst.bottom - camera.look_at_y) * height_ratio))
         */
    }
    override fun DrawBitmap(canvas: Canvas, bitmap_path: String, asset_manager: AssetManager, left:Float, top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio,paint)
        //super.DrawBitmap(canvas, bitmap_path,(left - camera.look_at_x) * width_ratio,(top - camera.look_at_y) * height_ratio)
    }

    /*绘制精灵*/
    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Float, height:Float, index:Int)
    {
        val dst:RectF = RectF((x - camera.look_at_x) * width_ratio,(y - camera.look_at_y) * height_ratio,
            (x - camera.look_at_x) * width_ratio + width * width_ratio,(y - camera.look_at_y) * height_ratio + height * height_ratio)
        sprite.GetBitmap()?.let { canvas.drawBitmap(it,sprite.GetSrcRect(index),dst,paint) }//绘制精灵bitmap
        /*
        super.DrawSprite(canvas,sprite,(x - camera.look_at_x) * width_ratio, (y - camera.look_at_y) * height_ratio,
            width * width_ratio, height * height_ratio, index)
         */
    }
    override fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float, y: Float, width:Float, height:Float, index:Int, flip_x:Float, flip_y: Float)
    {
        super.DrawSprite(canvas,sprite,(x - camera.look_at_x) * width_ratio, (y - camera.look_at_y) * height_ratio,
            width * width_ratio, height * height_ratio, index, flip_x, flip_y)
    }

    /*绘制物体*/
    override fun DrawObject(canvas: Canvas, obj: Object, width: Float, height: Float, index: Int)
    {
        /*
        val obj_x = obj.x * width_ratio
        val obj_y = obj.y * height_ratio
        val obj_width = width * width_ratio
        val obj_height = height * height_ratio
        obj_last_x = obj_x
        obj_last_y = obj_y

        if (obj.GetRigid() != null)
        {
            if (obj.GetCollisionBox() != null)
            {
                obj.GetCollisionBox()!!.SetRect(RectF(obj_x,obj_y,obj_x + obj_width,obj_y + obj_height))
                if (!obj.GetCollisionBox()!!.Collision())
                {
                    val will_y = obj_y + obj.GetRigid()!!.GetDropHeight(render_time)
                    obj.GetCollisionBox()!!.SetRect(RectF(obj_x,will_y,obj_x + obj_width,will_y + obj_height))
                    if (obj.GetCollisionBox()!!.Collision())
                    {
                        if (will_y + obj_height > obj.GetCollisionBox()!!.GetCollisionRect().top)
                        {
                            if (!(will_y + obj_height < obj.GetCollisionBox()!!.GetCollisionRect().top + 0.1f * height_ratio))
                                obj.y -= will_y + obj_height - obj.GetCollisionBox()!!.GetCollisionRect().top / height_ratio
                            else
                                obj_next_y = obj.GetCollisionBox()!!.GetCollisionRect().top / height_ratio - height
                        }
                    }
                    else
                    {
                        //obj.GetCollisionBox()!!.SetRect(RectF(obj_x,obj_y,obj_x + obj_width,obj_y + obj_height))
                        for (i in 0 until obj.GetCollisionBox()!!.GetAllCollisionRect().size)
                        {
                            if (obj_y >= obj.GetCollisionBox()!!.GetAllCollisionRect()[i].bottom // ||  obj_y > obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top
                                && obj_last_y!! + obj_height < obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top)
                            {
                                obj.y = obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top / height_ratio - height
                                return
                            }
                        }
                        obj.y += obj.GetRigid()!!.GetDropHeight(render_time) / height_ratio
                    }
                }
            }
            else
                obj.y += obj.GetRigid()!!.GetDropHeight(render_time) / height_ratio
        }
        */
        obj.GetSprite()?.let{ DrawSprite(canvas, it,obj.x,obj.y,width,height,index) }//绘制物体精灵
        /*
        if (obj_next_y != null)
            obj.y = obj_next_y!!
        obj_next_y = null
        */
    }

}
