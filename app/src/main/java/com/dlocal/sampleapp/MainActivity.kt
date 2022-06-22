package com.dlocal.sampleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dlocal.webdropin.DLTokenizeContract
import com.dlocal.webdropin.DLTokenizeInput
import com.dlocal.webdropin.DLTokenizeResponse

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val inputOptions = DLTokenizeInput(
            apiKey = "API KEY",
            country = "UY",
            locale = "ES",
            testMode = true
        )

        findViewById<Button>(R.id.startButton)?.setOnClickListener {
            // Launch the Activity
            tokenizeCardActivity.launch(inputOptions)
        }
    }

    @SuppressLint("SetTextI18n")
    private val tokenizeCardActivity = registerForActivityResult(DLTokenizeContract()) { result ->
        when (result) {
            is DLTokenizeResponse.Success -> {
                // Called when the tokenization process has successfully completed
                // Parameter will contain data about the card (including token) and installments data (if required)
                resultTextView.text = "Holder name: ${result.card.holderName}\n" +
                        "Card token: ${result.card.token}\n" +
                        "BIN: ${result.card.bin}\n" +
                        "Brand: ${result.card.brand}\n" +
                        "Type: ${result.card.type}\n" +
                        "Issuer: ${result.card.issuer}\n" +
                        "Country: ${result.card.country}"
            }
            is DLTokenizeResponse.Error -> {
                // Called when the tokenization process ends with an error
                // You are in charge of dealing with this error and presenting it to the user
                resultTextView.text = result.message
            }
        }
    }
}