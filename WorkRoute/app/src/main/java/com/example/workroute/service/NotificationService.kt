package com.example.workroute.service

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NotificationService(val context: Context) : Thread() {

    private lateinit var token: String
    private lateinit var message: String
    private lateinit var title: String
    private var senderName: String? = null
    private lateinit var activity: String

    constructor(
        context: Context,
        token: String,
        message: String,
        title: String,
        senderName: String?,
        activity: String
    ) : this(context) {
        this.token = token
        this.message = message
        this.title = title
        this.senderName = senderName
        this.activity = activity
    }

    override fun run() {
        var myRequest = Volley.newRequestQueue(context)
        var json = JSONObject()

        json.put("to", token)
        val notification = JSONObject()

        notification.put("titulo", title)
        notification.put("detalle", "$message")
        notification.put("activityOpen", activity)

        json.put("data", notification)

        val url = "https://fcm.googleapis.com/fcm/send"

        val jsonRequest: JsonObjectRequest = object :
            JsonObjectRequest(Method.POST, url, json, Response.Listener<JSONObject?> {
                //now handle the response
            }, Response.ErrorListener { error -> error.printStackTrace() }) {
            //this is the part, that adds the header to the request
            override fun getHeaders(): Map<String, String> {
                val map = HashMap<String, String>()
                map["content-type"] = "application/json"
                map["authorization"] =
                    "key=AAAAt-8FdV8:APA91bEmHhrJazEV2AB7LWBxhcRib1wMtuAGsobZydq6OUOCXYmaKpkuhnyHFFLdb3Eg5h2VqE134NChAJGNTWracREdIiLDIxPpHvnNAxxbw-MUw6_C-WHRspmbu1GEeUp5p418RPyp"
                return map
            }
        }
        myRequest.add(jsonRequest)
    }

}