package ru.skillbranch.devintensive.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.MainActivity

const val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128

fun MainActivity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun MainActivity.executeOnSendAction() {
    val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
    val (r, g, b) = color
    messageEt.setText("")
    benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    textTxt.text = phrase
    hideKeyboard()
}

fun MainActivity.isKeyboardOpen(): Boolean {
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm = rootView.resources.displayMetrics
    val heightDiff = rootView.rootView.height - (r.bottom - r.top)
    return heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
}

fun MainActivity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}