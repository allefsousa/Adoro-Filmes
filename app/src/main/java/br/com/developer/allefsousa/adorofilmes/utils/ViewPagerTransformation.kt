package br.com.developer.allefsousa.adorofilmes.utils

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/**
 * @author allef.santos on 2019-12-01
 */

class ViewPagerTransformation : PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.setTranslationX(-position*page.getWidth());
        page.setPivotX(0F);
        page.setPivotY(0F);


        if (position < -1){    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0F);

        }
        else if (position <= 0){    // [-1,0]
            page.setRotation(90*Math.abs(position));
            page.setAlpha(1-Math.abs(position));

        }
        else if (position <= 1){    // (0,1]
            page.setRotation(0F);
            page.setAlpha(1F);

        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0F);

        }


    }
}