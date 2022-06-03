package nguyen.exam.nabweather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by Nguyen on 03/06/2022
 */
abstract class BaseBindingRecyclerViewAdapter<T, VH : BaseRecyclerViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    protected var items = listOf<T>()

    abstract val layoutResourceId: Int

    abstract fun createViewHolder(dataBinding: ViewDataBinding): VH

    fun setData(items: List<T>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResourceId,
            parent,
            false
        )
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount() = items.count()

    fun getItem(position: Int) = items[position]
}
