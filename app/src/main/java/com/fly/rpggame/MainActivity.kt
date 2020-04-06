package com.fly.rpggame

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fly.game.Game
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("Registered")
class MainActivity : AppCompatActivity(), View.OnClickListener
{
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val bitmap = BitmapFactory.decodeStream(assets.open("title/WorldMap.png"))
        ImageView1.setImageBitmap(bitmap);

        StartButton.setOnClickListener(this)
        ExitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        if (v != null)
        {
            when (v.id)
            {
                R.id.StartButton -> StartGame()
                R.id.ExitButton -> ExitGame()
            }
        }
    }

    fun StartGame()
    {
        val intent = Intent(this, Game::class.java)
        startActivity(intent);  //开始跳转
    }

    fun ExitGame()
    {
        this.finish();
    }
}


