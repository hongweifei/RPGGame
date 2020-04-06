package com.fly.physics

import android.graphics.RectF

class CollisionBox(r:RectF? = null)
{
    var rect: RectF? = r
    private var collide_rect:ArrayList<RectF> = ArrayList<RectF>()
    private var collide_index:ArrayList<Int>? = null

    fun SetRect(r: RectF) : Boolean { rect = r;if (rect == r){return true}; return false }
    fun SetRect(left:Float,top:Float,right:Float,bottom:Float) { rect = RectF(left,top, right, bottom) }

    fun GetCollideRect(index:Int = 0) : RectF { return collide_rect[this.collide_index?.get(index)!!] }
    fun GetAllCollideRect() : ArrayList<RectF>
    {
        val r = ArrayList<RectF>()
        if (collide_index != null)
        {
            for (i in 0 until collide_index?.size!!)
                r.add(collide_rect[collide_index?.get(i)!!])
        }
        return r
    }

    fun GetCollisionRect(index: Int) : RectF { return collide_rect[index] }
    fun GetAllCollisionRect() : ArrayList<RectF> { return collide_rect }

    fun AddCollide(r: RectF) { collide_rect.add(r) }
    fun AddCollide(collision_box:CollisionBox) { if (collision_box.rect != null) { collide_rect.add(collision_box.rect!!) } }

    fun Collision() : Boolean
    {
        var collision_n = 0
        collide_index = null
        for (i in 0 until collide_rect.size)
        {
            if (Collision(collide_rect[i]))
            {
                collide_index?.add(i)
                collision_n++
            }
        }

        return collision_n >= 1
    }
    fun Collision(r:RectF) : Boolean
    { if (rect != null) { if (rect!!.left <= r.right && rect!!.right >= r.left && rect!!.bottom >= r.top && rect!!.top <= r.bottom) { return true } };return false }
    fun Collision(x:Float,y:Float,width:Float,height:Float) : Boolean
    { if (rect != null) { if (rect!!.left <= x + width && rect!!.right >= x && rect!!.bottom >= y && rect!!.top <= y + height) { return true } };return false }
}
