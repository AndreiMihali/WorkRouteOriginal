package com.example.workroute.kotlin.model

import java.util.*

class NotificationItem {
    var message: String? = null
    var type: String? = null
    var read: String? = null
    var id: String? = null
    var time: Long? = null

    constructor() {
        time = Calendar.getInstance().timeInMillis
    }

    constructor(
        message: String?,
        type: String?,
        read: String?,
    ) {
        this.message = message
        this.type = type
        this.read = read
        time = Calendar.getInstance().timeInMillis
    }

}