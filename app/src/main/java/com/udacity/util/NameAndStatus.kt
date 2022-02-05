package com.udacity.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NameAndStatus(var fileName: String, var status: String) : Parcelable