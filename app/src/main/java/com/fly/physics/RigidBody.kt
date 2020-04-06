package com.fly.physics

class RigidBody(m:Float = 0f, g:Float = 9.8f, f:Float = 0f, F:Float = 0f)
{
    var mass : Float = m          //质量
    var gravity : Float = g       //重力
    var friction : Float = f      //摩擦力,F=μ×Fn
    var elastic_force : Float = F //弹力,FN=KX

    fun GetDropVelocity(initial_velocity:Float/*单位为米*/,time:Float/*单位为秒*/) : Float
    { val velocity:Float = initial_velocity + gravity * time * time;return velocity }

    fun GetDropHeight(time: Float/*单位为秒*/) : Float { val height:Float = gravity * time * time / 2;return height }
    fun GetDropHeight(time: Long/*单位为毫秒*/) : Float { val height:Float = gravity * time / 1000 * time / 1000 / 2;return height }
    fun GetDropHeight(initial_velocity/*单位为米*/: Float,time:Float/*单位为秒*/) : Float { return GetDropVelocity(initial_velocity,time) / 2 }
}
