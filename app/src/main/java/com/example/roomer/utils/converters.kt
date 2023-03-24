package com.example.roomer.utils

import org.json.JSONObject

fun createJson(vararg params: Pair<String, Any>): JSONObject{
    val jsonObject = JSONObject()
    for (param in params){
        jsonObject.put(param.first, param.second)
    }
    return jsonObject
}
