package com.pslyp.covid19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.pslyp.covid19.models.Covid
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var confirmedTextView: TextView
    private lateinit var hospitalizedTextView: TextView
    private lateinit var recoveredTextView: TextView
    private lateinit var deathsTextView: TextView
    private lateinit var newConfirmedTextView: TextView
    private lateinit var newHospitalizedTextView: TextView
    private lateinit var newRecoveredTextView: TextView
    private lateinit var newDeathsTextView: TextView
    private lateinit var updateDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInstance()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun initInstance() {
        confirmedTextView = findViewById(R.id.confirmed_text_view)
        hospitalizedTextView = findViewById(R.id.hospitalized_text_view)
        recoveredTextView = findViewById(R.id.recovered_text_view)
        deathsTextView = findViewById(R.id.deaths_text_view)
        newConfirmedTextView = findViewById(R.id.new_confirmed_text_view)
        newHospitalizedTextView = findViewById(R.id.new_hospitalized_text_view)
        newRecoveredTextView = findViewById(R.id.new_recovered_text_view)
        newDeathsTextView = findViewById(R.id.new_deaths_text_view)
        updateDateTextView = findViewById(R.id.update_text_view)
    }

    private fun getData() {
        val url = "https://covid19.th-stat.com/api/open/today"

        var client = OkHttpClient()
        var request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val jsonObject = JSONObject(body)
                val covid = Covid(jsonObject)

                runOnUiThread {
                    setAllText(covid)
                }
            }
        })
    }

    private fun setAllText(covid: Covid) {
        confirmedTextView.text = covid.confirmed
        hospitalizedTextView.text = covid.hospitalized
        recoveredTextView.text = covid.recovered
        deathsTextView.text = covid.deaths

        newConfirmedTextView.text = isZeroAndGetText(covid.newConfirmed)
        newHospitalizedTextView.text = isZeroAndGetText(covid.newHospitalized)
        newRecoveredTextView.text = isZeroAndGetText(covid.newRecovered)
        newDeathsTextView.text = isZeroAndGetText(covid.newDeaths)

        Log.e("Deaths", covid.newDeaths)

        updateDateTextView.text = covid.updateDate
    }

    private fun isZeroAndGetText(text: String): String {
        return when {
            text.contains("-") -> "(ลดลง ${text.substring(1)})"
            text != "0" -> "(เพิ่มขึ้น $text)"
            else -> ""
        }
    }
}
