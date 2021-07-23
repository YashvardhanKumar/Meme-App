package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class NewsOnClick : AppCompatActivity() {
    private var title: String? = null
    private var desc: String? = null
    private var content: String? = null
    private var imageUrl: String? = null
    private var url: String? = null
    private lateinit var titleText: TextView
    private lateinit var subdesc: TextView
    private lateinit var contentText: TextView
    private lateinit var readNewsButton: Button
    override fun onDestroy() {
        super.onDestroy()
        try {
            cacheDir.delete()
            cacheDir.deleteRecursively()
            cacheDir.deleteOnExit()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_on_click)
        title = intent.getStringExtra("title")
        desc = intent.getStringExtra("desc")
        content = intent.getStringExtra("content")
        imageUrl = intent.getStringExtra("image")
        url = intent.getStringExtra("url")
        titleText = findViewById(R.id.newsContent)
        contentText = findViewById(R.id.newsContentDescription)
        val newsimg = findViewById<ImageView>(R.id.newsContentImage)
        subdesc = findViewById(R.id.newsContentDetail)
        readNewsButton = findViewById(R.id.newsContentReadNews)
        titleText.text = title
        subdesc.text = desc
        contentText.text = content
        Picasso.get().load(imageUrl).into(newsimg)
        readNewsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}