package nguyen.exam.nabweather.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Create by Nguyen on 04/06/2022
 */
object TextViewBindingAdapter {

    @BindingAdapter("text")
    @JvmStatic
    fun setText(textView: TextView, text: CharSequence) {
        if (textView.text != text) {
            textView.text = text
        }
    }
}
