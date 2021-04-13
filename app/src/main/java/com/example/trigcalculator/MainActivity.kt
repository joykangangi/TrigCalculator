package com.example.trigcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.trigcalculator.databinding.ActivityMainBinding
import kotlin.math.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

    binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { calculateAns() }
        binding.etBaseEditText.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculateAns() {
        val editableDegree = binding.etDegreeEditText.text
        val answer = editableDegree.toString().toDoubleOrNull()
        val editableBase = binding.etBaseEditText.text
        val baseAns = editableBase.toString().toDoubleOrNull()

        if (  (answer == null || answer == 0.0) || (  (baseAns == null || baseAns == 0.0) && (answer == 0.0) ) ) {
            binding.result.text = ""
            0.0.displayAns()
            return
        }

        val trigChoice = when (binding.calcOptions.checkedRadioButtonId) {
            R.id.fact_option -> factorial(answer)
            R.id.sin_option -> sin(answer)
            R.id.tan_option -> tan(answer)
            R.id.cos_option -> cos(answer)
           else -> baseAns?.let { log(answer, it) }
        }

        var finAns = trigChoice
        if ( binding.roundUpSwitch.isChecked ) finAns = finAns?.let { ceil(it) }

        binding.result.text = getString(R.string.trig_ans, finAns)

    }

   private fun Double.displayAns() {

        binding.result.text = getString(R.string.trig_ans, this)
    }

private fun factorial(a: Double): Double {
    return if (a < 2){
        1.0
    } else{
        a * factorial(a - 1)
    }
}

    private fun handleKeyEvent(view:View, keyCode: Int): Boolean {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            //hide keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}