package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    private var cv_borderColor = DEFAULT_BORDER_COLOR
    private var cv_borderWidth = Utils.convertDpToPx(context, 2)
    private var cv_text: String? = null
    private var cv_bitmap: Bitmap? = null

    init {
        if (attrs != null) {
            val ctx = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = ctx.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            cv_borderWidth = ctx.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, cv_borderWidth)
            ctx.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = Utils.convertPxToDp(context, cv_borderWidth)

    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = Utils.convertDpToPx(context, dp)
        this.invalidate()
    }

    //    @ColorRes
    private fun getBorderColor(): Int {
        return cv_borderColor
    }

    private fun setBorderColor(hex: String) {
        cv_borderColor = Color.parseColor(hex)
        this.invalidate()
    }

    private fun setBorderColor(@ColorRes colorId: Int) {
        cv_borderColor = ContextCompat.getColor(App.applicationContext(), colorId)
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        var bitmap = getBitmapFromDrawable()
        if (bitmap == null || width == 0 || height == 0) {
            Log.d("M_CircleImageView", "onDraw. Exit without drawing")
            return
        }
        bitmap = toCircledBitmap(cropBitmap(scaleBitmap(bitmap, width), width))
        if (cv_borderWidth > 0)
            bitmap = strokeBitmap(bitmap, cv_borderWidth, cv_borderColor)
        canvas.drawBitmap(bitmap, 0F, 0F, null)
    }

    private fun createDefaultAvatar(theme: Resources.Theme): Bitmap {
        val color = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, color, true)
        val avatar = Bitmap.createBitmap(layoutParams.height, layoutParams.height, Bitmap.Config.ARGB_8888)
        Canvas(avatar).drawColor(color.data)
        return avatar
    }

    fun createAvatar(text: String?, sizeSp: Int, theme: Resources.Theme) {
        if (this.cv_bitmap == null || text != this.cv_text) {
            val image = if (text == null) createDefaultAvatar(theme) else createInitialsAvatar(text, sizeSp, theme)
            this.cv_text = text
            this.cv_bitmap = image
            invalidate()
        }
    }

    private fun createInitialsAvatar(initials: String, sizeSp: Int, theme: Resources.Theme): Bitmap {
        val avatar = createDefaultAvatar(theme)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = sizeSp.toFloat()
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        val bounds = Rect()
        paint.getTextBounds(initials, 0, initials.length, bounds)
        val backgroundBounds = RectF()
        backgroundBounds.set(0f, 0f, layoutParams.height.toFloat(), layoutParams.height.toFloat())
        val textBottom = backgroundBounds.centerY() - bounds.exactCenterY()
        Canvas(avatar).drawText(initials, backgroundBounds.centerX(), textBottom, paint)
        return avatar
    }

    private fun getBitmapFromDrawable(): Bitmap? {
        if (this.cv_bitmap != null)
            return this.cv_bitmap
        if (drawable == null)
            return null
        if (drawable is BitmapDrawable)
            return (drawable as BitmapDrawable).bitmap
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun toCircledBitmap(bitmap: Bitmap): Bitmap {
        val minBound = min(bitmap.width, bitmap.height)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        val avatar = Bitmap.createBitmap(minBound, minBound, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(avatar)
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(minBound / 2F, minBound / 2F, minBound / 2F, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val rect = Rect(0, 0, minBound, minBound)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return avatar
    }

    private fun strokeBitmap(bitmap: Bitmap, strokeWidth: Int, color: Int): Bitmap {
        val rect = RectF()
        val strokePositionX = strokeWidth / 2F
        val strokePositionY = bitmap.width - strokeWidth / 2F
        rect.set(strokePositionX , strokePositionX, strokePositionY, strokePositionY)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        Canvas(bitmap).drawOval(rect, paint)
        return bitmap
    }

    // Can also implement Enum with crop types for further usages and move it to utils
    private fun cropBitmap(bitmap: Bitmap, size: Int) =
        Bitmap.createBitmap(bitmap, (bitmap.width - size) / 2, (bitmap.height - size) / 2, size, size)

    private fun scaleBitmap(bitmap: Bitmap, expectedSize: Int) : Bitmap {
        return if (bitmap.width != expectedSize || bitmap.height != expectedSize) {
            val minBound = min(bitmap.width, bitmap.height).toFloat()
            val scale = minBound / expectedSize
            Bitmap.createScaledBitmap(bitmap, (bitmap.width / scale).toInt(), (bitmap.height / scale).toInt(), false)
        } else bitmap
    }
}