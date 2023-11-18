package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state: State = State.Initial
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.titleTextView)

        findViewById<Button>(R.id.removeButton).setOnClickListener {
            state = State.Removed
            state.remove(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, State::class.java) as State
        } else {
            savedInstanceState.getSerializable(KEY) as State
        }
        state.remove(textView)
    }

    companion object {
        private const val KEY = "key"
    }

}

interface State : Serializable {
    fun remove(view: View)

    object Initial : State {
        override fun remove(view: View) = Unit

    }

    object Removed : State {
        override fun remove(view: View) {
            (view.parent as ViewGroup).removeView(view)
        }
    }
}