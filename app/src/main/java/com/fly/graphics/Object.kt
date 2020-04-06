package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.MotionEvent
import com.fly.animation.Animation
import com.fly.animation.Animator
import com.fly.physics.CollisionBox
import com.fly.physics.RigidBody

open class Object(scene: Scene? = null,var x:Float = 0f, var y:Float = 0f)//物体类
{
    var width:Float = 0f//物体宽
    var height:Float = 0f//物体高

    protected var width_ratio:Float = scene?.GetSceneWidthRatio() ?: 1f//宽比
    protected var height_ratio:Float = scene?.GetSceneHeightRatio() ?: 1f//高比

    private var click : (event: MotionEvent?) -> Unit = {}//物体点击事件
    private var up : (event: MotionEvent?) -> Unit = {}//物体放开事件
    private var move:(event: MotionEvent?)->Unit = {}//物体移动事件

    /*序列帧动画*/
    private var render_animation = false//物体是否渲染动画
    private var render_n:Int = 0//动画渲染次数
    private var render_index = 0//渲染动画索引
    private var render_last_begin:Int? = null//上次动画渲染开始索引，用于动画切换
    private var render_last_end:Int? = null//上次动画渲染结束索引，用于动画切换

    protected var sprite:Sprite? = null//精灵
    protected var animator:Animator? = null//动画控制器
    protected var rigid_body: RigidBody? = null//刚体
    protected var collision_box:CollisionBox? = null//碰撞盒

    /*设置各种物体事件*/
    fun SetClick(click:(event: MotionEvent?) ->Unit) { this.click = click }
    fun Click(event: MotionEvent?) { click(event) }
    fun SetUp(up:(event: MotionEvent?) ->Unit) { this.up = up }
    fun Up(event: MotionEvent?) { up(event) }
    fun SetMove(move:(event: MotionEvent?) ->Unit) { this.move = move }
    fun Move(event: MotionEvent?) { move(event) }

    /*设置物体精灵*/
    fun SetSprite(sprite: Sprite,init_w_h:Boolean = true,sprite_rect_continue:Boolean = false)
    {
        if (sprite_rect_continue)
            sprite.SetSrcRect(this.sprite?.GetSrcRect()!!)
        this.sprite = sprite
        if (init_w_h) { width = this.sprite!!.width;height = this.sprite!!.height }
    }
    fun SetSprite(path: String,asset_manager: AssetManager,init_w_h:Boolean = true,sprite_rect_continue:Boolean = false)
    {
        val sprite = Sprite(path,asset_manager)

        if (sprite_rect_continue)
            sprite.SetSrcRect(this.sprite?.GetSrcRect()!!)
        this.sprite = sprite

        if (init_w_h) { width = this.sprite!!.width;height = this.sprite!!.height }
    }

    /*设置物体动画控制器*/
    fun SetAnimator(animator: Animator) { this.animator = animator }

    /*设置刚体及碰撞盒*/
    fun SetRigidBody(rigid_body: RigidBody) { this.rigid_body = rigid_body }
    fun SetCollisionBox(collision_box: CollisionBox)
    {
        val box:CollisionBox = collision_box
        if (box.rect != null)
        {
            box.rect = RectF(box.rect!!.left * width_ratio,box.rect!!.top * height_ratio,
                box.rect!!.right * width_ratio,box.rect!!.bottom * height_ratio)
        }
        this.collision_box = box
    }

    /*获取物体成员*/
    fun GetSprite() : Sprite? { return sprite }
    fun GetAnimator() : Animator? { return animator }
    fun GetRigid() : RigidBody? { return rigid_body }
    fun GetCollisionBox() : CollisionBox? { return collision_box }

    /*给物体动画控制器添加动画*/
    fun AddAnimation(animation: Animation) { animator?.AddAnimation(animation) }

    /*给物体添加可碰撞物*/
    fun AddCollide(r:RectF)
    {
        val rect = RectF(r.left * width_ratio,r.top * height_ratio,r.right * width_ratio,r.bottom * height_ratio)
        collision_box?.AddCollide(rect)//碰撞盒添加可碰撞物
    }
    fun AddCollide(collision_box: CollisionBox)
    {
        val box:CollisionBox = collision_box
        if (box.rect != null)
            box.rect = RectF(box.rect!!.left * width_ratio,box.rect!!.top * height_ratio, box.rect!!.right * width_ratio,box.rect!!.bottom * height_ratio)
        this.collision_box?.AddCollide(box)
    }
    fun AddCollide(obj:Object) { collision_box?.AddCollide(obj.collision_box!!) }

    /*初始化序列帧动画...*/
    fun InitSrcRect(rect_list:ArrayList<Rect>) { sprite?.GetSrcRect()?.clear();sprite?.SetSrcRect(rect_list) }
    fun InitSpriteSrcRect(start_x:Int,start_y:Int,width:Int,height:Int,horizontal:Int,vertical:Int)
    { sprite?.InitSrcRect(start_x,start_y, width, height, horizontal, vertical) }

    /*渲染精灵序列帧动画*/
    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,begin_index:Int,end_index:Int,FPS:Int)
    { sprite?.RenderSpriteAnimation(canvas,renderer, x, y, width, height,begin_index, end_index, FPS) }
    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,width: Float,height: Float,begin_index:Int,end_index:Int,FPS:Int)
    { sprite?.RenderSpriteAnimation(canvas,renderer, x, y, width, height,begin_index, end_index, FPS) }

    /*渲染动画控制器中的动画*/
    fun RenderAnimation(canvas: Canvas, renderer: Renderer, frame:Int,index:Int) { animator?.RenderAnimation(canvas,renderer,frame,x * width_ratio, y * height_ratio, index) }
    fun RenderAnimation(canvas: Canvas, renderer: Renderer, width: Float,height: Float,frame:Int,index:Int)
    { animator?.RenderAnimation(canvas, renderer, frame, x * width_ratio, y * height_ratio, width * width_ratio, height * height_ratio, index) }

    /*渲染物体序列帧动画*/
    /*frame为渲染多少帧后切换下一帧*/
    fun RenderObjectAnimation(canvas: Canvas,renderer: Renderer,begin_index:Int = 0,end_index:Int = 0,frame:Int = 0)
    { RenderObjectAnimation(canvas, renderer, width, height, begin_index, end_index, frame) }
    fun RenderObjectAnimation(canvas: Canvas,renderer: Renderer,width: Float = this.width,height: Float = this.height,begin_index:Int = 0,end_index:Int = 0,frame:Int = 0)
    {
        /*序列帧动画*/
        if (!render_animation)//如果未渲染动画
        {
            render_index = begin_index;//渲染索引 = 动画开始索引
            render_last_begin = begin_index;//上次动画开始索引 = 动画开始索引
            render_last_end = end_index;//上次动画结束索引 = 动画结束索引
            render_animation = true//动画渲染 = 真
        }
        else//已渲染动画
        {
            if (render_last_begin != begin_index || render_last_end != end_index)//上次渲染动画索引是否与此次动画索引不一致
            {
                render_index = begin_index;//渲染索引 = 此次动画开始索引
                render_last_begin = begin_index;//上次动画开始索引 = 此次动画开始索引
                render_last_end = end_index//上次动画结束索引 = 此次动画结束索引
            }
        }
        renderer.DrawObject(canvas,this,width,height,render_index)//渲染物体
        if (render_n >= frame)//动画渲染次数  >= 切换帧所需次数
        {
            render_n = 0;//重置动画渲染次数
            render_index++//动画渲染索引++,切换下一帧
        }
        else if (render_n < frame)//未达到切换帧所需次数
        {
            render_n++//动画渲染次数++
        }
        if (render_index > end_index)//渲染索引  > 动画结束索引，动画渲染结束
            render_animation = false //动画渲染 = 假
    }

    /*渲染物体*/
    //fun Render(canvas: Canvas, renderer: Renderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: Renderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: Renderer,index: Int) { renderer.DrawObject(canvas,this,width, height,index) }
    fun Render(canvas: Canvas, renderer: Renderer, width: Float = this.width, height: Float = this.height, index: Int)
    { renderer.DrawObject(canvas,this,width,height,index) }
}
