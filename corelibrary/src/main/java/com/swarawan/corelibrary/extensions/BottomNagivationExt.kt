package com.swarawan.corelibrary.extensions

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log

/**
 * Created by Rio Swarawn on 5/20/18.
 */

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView
    try {
        menuView.javaClass.getDeclaredField("mShiftingMode").apply {
            isAccessible = true
            setBoolean(menuView, false)
            isAccessible = false
        }
        for (i in 0..menuView.childCount) {
            (menuView.getChildAt(i) as BottomNavigationItemView).apply {
                setShiftingMode(false)
                setChecked(itemData.isChecked)
            }
        }
    } catch (ex: Exception) {
        Log.d(BottomNavigationView::class.java.name, "BottomNavigationView . disableShiftMode ()")
    }
}