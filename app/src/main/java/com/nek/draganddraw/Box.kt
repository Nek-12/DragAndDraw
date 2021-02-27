package com.nek.draganddraw

import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

class Box(val start: PointF): Parcelable {

    var end: PointF = start
    val left: Float
        get() = Math.min(start.x, end.x)
    val right: Float
        get() = Math.max(start.x, end.x)
    val top: Float
        get() = Math.min(start.y, end.y)
    val bottom: Float
        get() = Math.max(start.y, end.y)


    //read items in the same order they were written!
    constructor(parcel: Parcel) : this(parcel.readParcelable<PointF>(PointF::class.java.classLoader)!!) {
        end = parcel.readParcelable(PointF::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(start, flags)
        parcel.writeParcelable(end, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Box> {
        override fun createFromParcel(parcel: Parcel): Box {
            return Box(parcel)
        }

        override fun newArray(size: Int): Array<Box?> {
            return arrayOfNulls(size)
        }
    }
}
