package br.com.developer.allefsousa.adorofilmes.utils

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * @author allef.santos on 2019-12-01
 */

class ViewPagerTransformation : PageTransformer {
    override fun transformPage(page: View, position: Float) {
        var position = position
        position = if (position < -1) (-1).toFloat() else position
        position = if (position > 1) 1F else position
        val tempScale = if (position < 0) 1 + position else 1 - position
        val slope = (MAX_SCALE - MIN_SCALE) / 1
        val scaleValue = MIN_SCALE + tempScale * slope
        page.scaleX = scaleValue
        page.scaleY = scaleValue
        page.parent.requestLayout()
    }

    companion object {
        private const val MAX_SCALE = 1.0f
        private const val MIN_SCALE = 0.4f
    }
}