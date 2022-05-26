package com.securicam.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.securicam.R

class PasswordEditText: AppCompatEditText, View.OnTouchListener {
    private lateinit var setShowPasswordImage: Drawable
    private lateinit var passwordImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "******"

        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        setShowPasswordImage = ContextCompat.getDrawable(context, R.drawable.ic_visible_24) as Drawable
        passwordImage = ContextCompat.getDrawable(context, R.drawable.ic_lock_24) as Drawable
        setButtonDrawables(startOfTheText = passwordImage, endOfTheText = setShowPasswordImage)
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 6) error = context.getString(R.string.password_minimum)
                if(s.isEmpty()) error = context.getString(R.string.password_empty)
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            var isShowPasswordButtonClicked = false

            val setPasswordVisibleButton: Float = (width - paddingEnd - setShowPasswordImage.intrinsicWidth).toFloat()
            when {
                event.x > setPasswordVisibleButton -> isShowPasswordButtonClicked = true
            }

            if (isShowPasswordButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        text?.let { setSelection(it.length) }
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        text?.let { setSelection(it.length) }
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }
}