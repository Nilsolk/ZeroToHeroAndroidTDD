package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.countTextView)
        button = findViewById(R.id.incrementButton)

        val count = Count.Base(2)

        button.setOnClickListener {
            textView.text = count.increment(textView.text.toString())
        }
    }
}

interface Count {
    fun increment(number: String): String
    class Base(private val step: Int) : Count {

        init {
            if (step <= 0)
                throw CustomException("step should be positive, but was $step")
        }

        override fun increment(number: String) =
            (number.toInt() + step).toString()
    }
}

class CustomException(mess: String) : java.lang.IllegalStateException() {
    override val message: String = mess
}
