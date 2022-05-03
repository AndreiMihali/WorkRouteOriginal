package com.example.workroute.kotlin.model

class NotificationItem{
    var message:String?=null
    var type:String?=null
    var read:String?=null
    var id:String?=null
    constructor(){}

    constructor(message:String?,
                type:String?,
                read:String?,
    )
    {
        this.message=message
        this.type=type
        this.read=read
    }

}