package nguyen.exam.nabweather.ui

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Create by Nguyen on 04/06/2022
 */
object ViewBindingAdapter {

    @BindingAdapter("visibleBinding")
    @JvmStatic
    fun setViewVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
