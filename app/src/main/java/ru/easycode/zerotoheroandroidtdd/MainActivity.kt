package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var visibility: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.titleTextView)

        if (savedInstanceState != null) textView.visibility =
            savedInstanceState.getInt(VISIBILITY_KEY)



        findViewById<Button>(R.id.hideButton).setOnClickListener {
            textView.visibility = View.INVISIBLE
            visibility = textView.visibility
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(VISIBILITY_KEY, visibility)
    }

    companion object {
        private const val VISIBILITY_KEY = "KEY"
    }
}