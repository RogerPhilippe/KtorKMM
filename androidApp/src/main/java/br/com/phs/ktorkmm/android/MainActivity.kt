package br.com.phs.ktorkmm.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.phs.ktorkmm.Greeting
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val greeting = Greeting()
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "Loading..."

        mainScope.launch {
            kotlin.runCatching {
                greeting.greeting()
            }.onSuccess {
                tv.text = it
            }.onFailure {
                tv.text = "Error: ${it.localizedMessage}"
            }
        }

    }
}
