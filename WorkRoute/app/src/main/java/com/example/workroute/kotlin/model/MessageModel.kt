package com.example.workroute.kotlin.model

class MessageModel {
    var dateTime: String? = null
    var textMessage: String? = null
    var sender: String? = null
    var receiver: String? = null
    var time:String?=null

    constructor() {}
    constructor(
        dateTime: String?,
        textMessage: String?,
        sender: String?,
        receiver: String?,
        time:String?
    ) {
        this.dateTime = dateTime
        this.textMessage = textMessage
        this.sender = sender
        this.receiver = receiver
        this.time=time
    }
}