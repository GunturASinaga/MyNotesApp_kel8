package com.example.mynotesappsu

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Note(
    val date: Long,
    val desc: String,
    val title: String
) : Parcelable