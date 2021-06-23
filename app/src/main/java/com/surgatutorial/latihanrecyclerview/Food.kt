package com.surgatutorial.latihanrecyclerview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(val name: String, val time: String, val image: Int): Parcelable
