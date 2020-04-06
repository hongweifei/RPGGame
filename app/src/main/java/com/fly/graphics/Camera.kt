package com.fly.graphic


class Camera(var look_at_x : Float = 0f,var look_at_y : Float = 0f)//摄像机坐标
{

    init
    {

    }

    fun Move(x:Float,y:Float) { look_at_x = x;look_at_y = y }//移动摄像机

}
