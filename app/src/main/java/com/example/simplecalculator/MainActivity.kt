package com.example.simplecalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    val numbers = Vector<Double>()
    val actions = Vector<String>()
    var isEnd = false
    var text = ""
    fun printtmpcalculate() {
        val textView = findViewById<TextView>(R.id.calculationTextView)
        textView.setText(text)
    }
    var fullnumber = ""
    fun numberButton(number:String) {
        if(fullnumber == "0") {
            text.dropLast(1)
            fullnumber = ""
        }
        text += number
        fullnumber += number
        printtmpcalculate()
    }
    fun specialMinusButton() {
        if(fullnumber == "" && actions.size == 0) {
            numberButton("-")
        } else {
            mathActionButton("-")
        }
    }
    fun braceButton(brace:String) {
        text+=brace
        printtmpcalculate()
    }
    fun mathActionButton(action:String) {
        if (fullnumber == "" && actions.size > 0) {
            actions[actions.size - 1] = action
            text = text.dropLast(1)
            text+=action
        } else
        if (fullnumber != "" && fullnumber != "-"){
            fullnumber = ""
            actions.add(action)
            text+=action
        }
        printtmpcalculate()
    }

    var criterror = false
    fun dividezero() {
        criterror = true
        val textView = findViewById<TextView>(R.id.calculationTextView)
        textView.setText("Err")
    }
    fun calculate(actions:Vector<String>,numbers:Vector<Double>):String {
        for(i in 0..actions.size - 1) {
            if(actions[i] == "*" ) {
                val a = numbers[i]
                val b = numbers[i + 1]
                numbers[i + 1] = a * b
                numbers[i] = 0.0
            }
            if(actions[i] == ":" ) {
                val a = numbers[i]
                val b = numbers[i + 1]
                if(b == 0.0) {
                    dividezero()
                    actions.clear()
                    numbers.clear()
                    return "Err"
                }
                numbers[i + 1] = a / b
                numbers[i] = 0.0
            }
        }
        var lastaction = "+"
        var ans = 0.0
        for(i in 0..actions.size - 1) {
            if(lastaction == "+")
                ans += numbers[i]
            else
                ans -= numbers[i]
            if (actions[i] == "+" || actions[i] == "-") {
                lastaction = actions[i]
            }
        }
        if(lastaction == "+")
            ans += numbers[actions.size]
        else
            ans -= numbers[actions.size]
        if(ans - Math.floor(ans) != 0.0)
            fullnumber = ans.toString()
        else
            fullnumber = Math.floor(ans).toInt().toString()
        return ans.toString()
    }
    fun init(text:String):String {
        val numbers = Vector<Double>()
        val actions = Vector<String>()
        var fullnumber = ""

        for(i in text.indices) {
            if(text[i] in '0'..'9' || text[i] == '.'){
                if(fullnumber == "0")
                    fullnumber = ""
                fullnumber += text[i]
            }
            else {
                if (fullnumber == "" && actions.size > 0) {
                    actions[actions.size - 1] = text[i].toString()
                } else
                    if (fullnumber != "" && fullnumber != "-"){
                        numbers.add(fullnumber.toDouble())
                        fullnumber = ""
                        actions.add(text[i].toString())
                    }
            }
        }
        numbers.add(fullnumber.toDouble())
        return calculate(actions,numbers)
    }
    var calculationtext = ""
    fun calculatebraces():String {
        calculationtext = text
        var i = 0
        while(i < calculationtext.length) {
            if(calculationtext[i] == ')') {
                var j = i
                while(j >= 0) {
                    if(calculationtext[j] == '(') {
                        val string = init(calculationtext.subSequence(j,i) as String)
                        calculationtext = calculationtext.take(j) + string + calculationtext.takeLast(calculationtext.length - i - 1)
                        i = 0
                        j = 0
                    }
                    j--
                }
            }
            i++
        }
        return init(calculationtext)
    }
    var removeequalsign = false
    fun equalButton() {

        removeequalsign = true
        isEnd = true
        numbers.add(fullnumber.toDouble())
        fullnumber = ""
        printtmpcalculate()

        val tmp = calculatebraces()
        var ans = 0.0
        if(tmp == "Err")
            return
        else
            ans = tmp.toDouble()
        val textView = findViewById<TextView>(R.id.calculationTextView)
        text+="="
        if(ans - Math.floor(ans) != 0.0)
            text += ans.toString()
        else
            text += Math.floor(ans).toInt().toString()
        textView.setText(text)
        actions.clear()
        numbers.clear()
        if(ans - Math.floor(ans) != 0.0)
            fullnumber = ans.toString()
        else
            fullnumber = Math.floor(ans).toInt().toString()
        text = fullnumber
    }

    fun deleteButton() {
        val textView = findViewById<TextView>(R.id.calculationTextView)
        textView.setText("0")
        fullnumber = ""
        text = ""
        actions.clear()
        numbers.clear()
    }
    fun removeButton() {
        if (removeequalsign == true)
            printtmpcalculate()
        else {
            if (fullnumber != "") {
                fullnumber = fullnumber.dropLast(1)
            }
            text = text.dropLast(1)
            printtmpcalculate()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val button0 = findViewById<Button>(R.id.button0)
        val buttonplus = findViewById<Button>(R.id.buttonplus)
        val buttonminus = findViewById<Button>(R.id.buttonminus)
        val buttonmultiply = findViewById<Button>(R.id.buttonmultiply)
        val buttondivide = findViewById<Button>(R.id.buttondivide)
        val buttonequal = findViewById<Button>(R.id.buttonequal)
        val buttondelete = findViewById<Button>(R.id.buttondelete)
        val buttonremove = findViewById<Button>(R.id.buttonremove)
        val buttoncomma = findViewById<Button>(R.id.buttoncomma)
        val buttonleftbrace = findViewById<Button>(R.id.buttonleftbrace)
        val buttonrightbrace = findViewById<Button>(R.id.buttonrightbrace)
        buttonleftbrace.setOnClickListener(View.OnClickListener {
            braceButton("(")
        })
        buttonrightbrace.setOnClickListener(View.OnClickListener {
            braceButton(")")
        })
        buttoncomma.setOnClickListener(View.OnClickListener {
            numberButton(".")
        })
        button0.setOnClickListener(View.OnClickListener {
            numberButton("0")
        })
        button1.setOnClickListener(View.OnClickListener {
            numberButton("1")
        })
        button2.setOnClickListener(View.OnClickListener {
            numberButton("2")
        })
        button3.setOnClickListener(View.OnClickListener {
            numberButton("3")
        })
        button4.setOnClickListener(View.OnClickListener {
            numberButton("4")
        })
        button5.setOnClickListener(View.OnClickListener {
            numberButton("5")
        })
        button6.setOnClickListener(View.OnClickListener {
            numberButton("6")
        })
        button7.setOnClickListener(View.OnClickListener {
            numberButton("7")
        })
        button8.setOnClickListener(View.OnClickListener {
            numberButton("8")
        })
        button9.setOnClickListener(View.OnClickListener {
            numberButton("9")
        })
        buttonplus.setOnClickListener(View.OnClickListener {
            mathActionButton("+")
        })
        buttonminus.setOnClickListener(View.OnClickListener {
            specialMinusButton()
        })
        buttonmultiply.setOnClickListener(View.OnClickListener {
            mathActionButton("*")
        })
        buttondivide.setOnClickListener(View.OnClickListener {
            mathActionButton(":")
        })
        buttonremove.setOnClickListener(View.OnClickListener {
            removeButton()
        })
        buttondelete.setOnClickListener(View.OnClickListener {
            deleteButton()
        })
        buttonequal.setOnClickListener(View.OnClickListener {
            equalButton()
        })

    }
}