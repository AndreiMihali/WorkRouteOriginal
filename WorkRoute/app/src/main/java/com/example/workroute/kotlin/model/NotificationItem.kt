package com.example.workroute.kotlin.model

import java.util.*

class NotificationItem {
    var message: String? = null
    var type: String? = null
    var read: String? = null
    var id: String? = null
    var time: String? = null

    constructor() {
        time = Calendar.getInstance().time.toLocaleString()
    }

    constructor(
        message: String?,
        type: String?,
        read: String?,
    ) {
        this.message = message
        this.type = type
        this.read = read
        time = Calendar.getInstance().time.toLocaleString()
    }

}