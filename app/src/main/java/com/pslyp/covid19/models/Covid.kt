package com.pslyp.covid19.models

import org.json.JSONObject

class Covid(json: JSONObject) {

    private val json: JSONObject

    val confirmed get() = checkThousand(this.json.getString("Confirmed"))
    val recovered get() = checkThousand(this.json.getString("Recovered"))
    val hospitalized get() = checkThousand(this.json.getString("Hospitalized"))
    val deaths get() = checkThousand(this.json.getString("Deaths"))
    val newConfirmed get() = checkThousand(this.json.getString("NewConfirmed"))
    val newRecovered get() = checkThousand(this.json.getString("NewRecovered"))
    val newHospitalized get() = checkThousand(this.json.getString("NewHospitalized"))
    val newDeaths get() = checkThousand(this.json.getString("NewDeaths"))
    val updateDate get() = this.json.getString("UpdateDate")

    init {
        this.json = json
    }

    private fun checkThousand(text: String): String {
        if(text.length > 3) {
            val first = text.substring(0, 1)
            val last = text.substring(1)
            return first + "," + last
        }
        return text
    }
}