package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var state: UiState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.incrementButton)
        textView = findViewById(R.id.countTextView)

        val count = Count.Base(2, 4)
        button.setOnClickListener {
            state = count.increment(textView.text.toString())
            textView.text = state.showResult()
            state.isMax(button)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getSerializable(KEY) as UiState
        textView.text = state.showResult()
        state.isMax(button)
    }

    companion object {
        private const val KEY = "Key"
    }
}


interface Count {
    fun increment(number: String): UiState
    class Base(private val step: Int, private val max: Int) : Count {

        init {
            if (step <= 0) throw StepZeroOrNegativeException(step)
            else if (max <= 0) throw MaxZeroOrNegativeException(max)
            else if (step > max) throw MaxLessThanStepException()
        }

        override fun increment(number: String): UiState {
            val value = number.toInt() + step
            return if ((max - value) < step) {
                UiState.Max(value.toString())
            } else {
                UiState.Base(value.toString())
            }
        }
    }
}

interface UiState : Serializable {
    fun showResult(): String
    fun isMax(view: View)
    abstract class Abstract(protected open val text: String) : UiState {
        override fun showResult(): String {
            return this.text
        }
    }

    data class Base(override val text: String) : Abstract(text) {
        override fun isMax(view: View) = Unit
    }

    data class Max(override val text: String) : Abstract(text) {
        override fun isMax(view: View) {
            view.isEnabled = false
        }
    }
}

class StepZeroOrNegativeException(step: Int) : IllegalStateException() {
    override val message: String = "step should be positive, but was $step"
}

class MaxZeroOrNegativeException(max: Int) : IllegalStateException() {
    override val message: String = "max should be positive, but was $max"
}

class MaxLessThanStepException() : IllegalStateException() {
    override val message: String = "max should be more than step"
}
