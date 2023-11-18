package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: TextView

    private var state: State = State.Initial()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        button = findViewById(R.id.removeButton)

        button.setOnClickListener {
            state = State.Mutable()
            state.remove(textView)
            state.enable(button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_KEY, state)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(STATE_KEY, State::class.java) as State
        } else {
            savedInstanceState.getSerializable(STATE_KEY) as State
        }
        state.remove(textView)
        state.enable(button)
    }

    companion object {
        private const val STATE_KEY = "key"
    }
}


interface State : Remove, Enable, Serializable {

    class Initial : State {
        override fun remove(view: View) = Unit

        override fun enable(view: View) = Unit
    }

    class Mutable : State {
        override fun remove(view: View) {
            (view.parent as ViewGroup).removeView(view)
        }

        override fun enable(view: View) {
            view.isEnabled = false
        }

    }
}

interface Remove {
    fun remove(view: View)
}

interface Enable {
    fun enable(view: View)
}