package nguyen.exam.nabweather.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by Nguyen on 03/06/2022
 */
abstract class BaseRecyclerViewHolder<T>(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(data: T)
}
