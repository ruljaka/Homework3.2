package com.ruslangrigoriev.homework32

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val loadBtn = findViewById<Button>(R.id.btn_load)
        val editText = findViewById<EditText>(R.id.editText_url)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        loadBtn.setOnClickListener {
            progressBar.isVisible = true
            executor.execute {
                val imageURL = editText.text.toString()
                try {
                    val stream = URL(imageURL).openStream()
                    val image = BitmapFactory.decodeStream(stream)
                    handler.post {
                        progressBar.isVisible = false
                        imageView.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    handler.post {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}