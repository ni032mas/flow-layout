package com.ni032mas.flowlayout

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class FlowLayout : FrameLayout {
    private lateinit var viewText: TextView
    private lateinit var viewFlow: View
    private lateinit var typedArray: TypedArray
    private lateinit var viewTextLayoutParams: LayoutParams
    private var viewTextWidth: Int = 0
    private var viewTextHeight: Int = 0
    private lateinit var viewFlowLayoutParams: LayoutParams
    private var viewFlowWidth: Int = 0
    private var viewFlowHeight: Int = 0
    private var viewTextLineCount = 0
    private var availableWidth = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            viewText = findViewById(typedArray.getResourceId(R.styleable.FlowLayout_viewTextIn, View.NO_ID))
            viewFlow = findViewById(typedArray.getResourceId(R.styleable.FlowLayout_viewFlowIn, View.NO_ID))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        viewTextLayoutParams = viewText.layoutParams as LayoutParams
        viewTextWidth = viewText.measuredWidth + viewTextLayoutParams.leftMargin + viewTextLayoutParams.rightMargin
        viewTextHeight = viewText.measuredHeight + viewTextLayoutParams.topMargin + viewTextLayoutParams.bottomMargin
        viewFlowLayoutParams = viewFlow.layoutParams as LayoutParams
        viewFlowWidth = viewFlow.measuredWidth + viewFlowLayoutParams.leftMargin + viewFlowLayoutParams.rightMargin
        viewFlowHeight = viewFlow.measuredHeight + viewFlowLayoutParams.topMargin + viewFlowLayoutParams.bottomMargin
        viewTextLineCount = viewText.lineCount
        val viewTextLastLineWidth = if (viewTextLineCount > 0) viewText.layout.getLineWidth(viewTextLineCount - 1).toInt() else 0
        availableWidth = widthSize - paddingStart - paddingEnd
        widthSize = paddingStart + paddingEnd
        when {
            viewTextLineCount > 1 && viewTextLastLineWidth + viewFlowWidth + viewFlow.paddingEnd < viewText.measuredWidth -> {
                widthSize += viewTextWidth
                heightSize += viewTextHeight + viewFlowHeight / 3
            }
            viewTextLineCount > 1 && (viewTextLastLineWidth + viewFlowWidth + viewFlow.paddingEnd > availableWidth) -> {
                widthSize += viewTextWidth
                heightSize += viewTextHeight + viewFlowHeight + viewFlowHeight / 3
            }
            viewTextLineCount == 1 && (viewTextWidth + viewFlowWidth > availableWidth) -> {
                widthSize += viewText.measuredWidth
                heightSize += viewTextHeight + viewFlowHeight + viewFlowHeight / 3
            }
            else -> {
                widthSize += viewTextWidth + viewFlowWidth
                heightSize += if (viewTextHeight == 0) viewFlowHeight else viewTextHeight + viewFlowHeight / 3
            }
        }
        setMeasuredDimension(widthSize, heightSize)
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY))
    }
}
