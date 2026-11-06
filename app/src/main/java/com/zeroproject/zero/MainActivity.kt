
package com.zeroproject.zero

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*

class MainActivity : Activity() {

    private lateinit var messages: LinearLayout
    private lateinit var input: EditText
    private lateinit var scroll: ScrollView

    private val background = Color.rgb(15, 16, 21)
    private val panel = Color.rgb(27, 29, 37)
    private val gold = Color.rgb(205, 169, 105)
    private val white = Color.rgb(240, 240, 245)

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = background
        window.navigationBarColor = background
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(background)
            setPadding(dp(16), dp(12), dp(16), dp(12))
        }

        // Верхняя панель

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), dp(10), dp(4), dp(18))
        }

        val titleBox = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val title = TextView(this).apply {
            text = "ZERO"
            textSize = 27f
            setTextColor(white)
            typeface = Typeface.DEFAULT_BOLD
            letterSpacing = 0.12f
        }

        val subtitle = TextView(this).apply {
            text = "PERSONAL AI ASSISTANT"
            textSize = 10f
            setTextColor(gold)
            letterSpacing = 0.15f
        }

        titleBox.addView(title)
        titleBox.addView(subtitle)

        val settings = Button(this).apply {
            text = "⚙"
            textSize = 23f
            setTextColor(gold)
            setBackgroundColor(Color.TRANSPARENT)
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Настройки появятся в следующей версии",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        header.addView(
            titleBox,
            LinearLayout.LayoutParams(0, -2, 1f)
        )
        header.addView(settings)

        // Статус Элис

        val statusPanel = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), dp(12), dp(14), dp(12))
            setBackgroundColor(panel)
        }

        val statusDot = TextView(this).apply {
            text = "●"
            textSize = 14f
            setTextColor(Color.rgb(100, 190, 130))
        }

        val statusText = TextView(this).apply {
            text = "Элис · Интерфейс запущен"
            textSize = 13f
            setTextColor(white)
            setPadding(dp(9), 0, 0, 0)
        }

        statusPanel.addView(statusDot)
        statusPanel.addView(statusText)

        // Переписка

        scroll = ScrollView(this).apply {
            isFillViewport = true
            clipToPadding = false
        }

        messages = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(14), 0, dp(14))
        }

        scroll.addView(
            messages,
            ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        // Поле ввода

        val inputPanel = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(8), dp(6), dp(8), dp(6))
            setBackgroundColor(panel)
        }

        input = EditText(this).apply {
            hint = "Напиши Элис..."
            setHintTextColor(Color.GRAY)
            setTextColor(white)
            textSize = 15f
            setPadding(dp(12), dp(10), dp(12), dp(10))
            setBackgroundColor(Color.TRANSPARENT)
            minLines = 1
            maxLines = 4
            gravity = Gravity.CENTER_VERTICAL
        }

        val send = Button(this).apply {
            text = "➤"
            textSize = 21f
            setTextColor(Color.BLACK)
            setBackgroundColor(gold)
            isAllCaps = false
            setOnClickListener {
                sendMessage()
            }
        }

        inputPanel.addView(
            input,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        inputPanel.addView(
            send,
            LinearLayout.LayoutParams(dp(54), dp(50))
        )

        // Сборка экрана

        root.addView(header)

        root.addView(
            statusPanel,
            LinearLayout.LayoutParams(-1, -2).apply {
                bottomMargin = dp(8)
            }
        )

        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        root.addView(
            inputPanel,
            LinearLayout.LayoutParams(-1, -2)
        )

        setContentView(root)

        addMessage(
            "Элис",
            "Привет! Я Элис. ✨\n\n" +
            "Добро пожаловать в ZERO Assistant!\n\n" +
            "Сейчас я работаю в демонстрационном режиме. " +
            "Скоро мы подключим мою локальную нейросеть."
        )
    }

    private fun sendMessage() {
        val text = input.text.toString().trim()

        if (text.isEmpty()) return

        addMessage("Ты", text)
        input.text.clear()

        addMessage(
            "Элис",
            "Я получила твоё сообщение!\n\n" +
            "Пока это тест интерфейса. " +
            "Подключение нейросети будет следующим этапом."
        )
    }

    private fun addMessage(sender: String, text: String) {

        val message = TextView(this).apply {
            this.text = "$sender\n\n$text"
            textSize = 15f
            setTextColor(
                if (sender == "Элис") white else gold
            )
            setPadding(
                dp(15), dp(12), dp(15), dp(12)
            )
            setBackgroundColor(
                if (sender == "Элис") panel
                else Color.rgb(36, 34, 30)
            )
        }

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, dp(5), 0, dp(5))
        }

        messages.addView(message, params)

        scroll.post {
            scroll.fullScroll(View.FOCUS_DOWN)
        }
    }
}
