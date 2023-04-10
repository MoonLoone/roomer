package com.example.roomer.utils.converters

import org.json.JSONObject

fun createJson(vararg params: Pair<String, Any>): JSONObject {
    val jsonObject = JSONObject()
    for (param in params) {
        jsonObject.put(param.first, param.second)
    }
    return jsonObject
}

fun getFromJson(json: JSONObject, searchElementName: String): String {
    return json.getString(searchElementName)
}
