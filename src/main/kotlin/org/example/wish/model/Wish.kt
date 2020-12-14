package org.example.wish.model

import org.example.wish.model.enum.Status
import org.joda.time.DateTime

data class Wish(
    val id: Long? = null,
    val name: String?,
    val description: String?,
    val link: String?,
    val status: Status = Status.ABERTO,
    val date: DateTime?
)