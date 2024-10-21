package com.example.dicodingeventapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventEntity(

    @PrimaryKey(true)
    @ColumnInfo(name = "eventId")
    var eventId: String,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "ownerName")
    var mediaCover: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "ownerName")
    var ownerName: String? = null,

    @ColumnInfo("quota")
    var quota: Int = 0,

    @ColumnInfo("registrant")
    var registrant: Int = 0,

    @ColumnInfo(name = "beginTime")
    var beginTime: String? = null,

    @ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @ColumnInfo(name = "isActive")
    var isActive: Boolean = false,

    )
