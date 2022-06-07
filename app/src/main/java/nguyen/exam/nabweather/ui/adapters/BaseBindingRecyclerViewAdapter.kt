package nguyen.exam.nabweather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by Nguyen on 03/06/2022
 */
abstract class BaseBindingRecyclerViewAdapter<T : Any, VH : BaseRecyclerViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    private val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            this@BaseBindingRecyclerViewAdapter.areItemsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            this@BaseBindingRecyclerViewAdapter.areContentsTheSame(oldItem, newItem)

        override fun getChangePayload(oldItem: T, newItem: T) =
            this@BaseBindingRecyclerViewAdapter.getChangePayload(oldItem, newItem)
    }

    /**
     * Use differ to check and update only changed items.
     */
    private var differ: AsyncListDiffer<T>? = null

    protected var items = listOf<T>()

    abstract val layoutResourceId: Int

    abstract fun createViewHolder(dataBinding: ViewDataBinding): VH

    fun submitData(items: List<T>) {
        differ?.submitList(items) ?: kotlin.run {
            this.items = items
            notifyDataSetChanged()
        }
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

    override fun getItemCount() = differ?.currentList?.size ?: items.count()

    protected fun getItem(position: Int) = differ?.currentList?.get(position) ?: items[position]

    /**
     * Can be override to check the item's id.
     */
    protected open fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    /**
     * If the data <T> is data class, it is already implement [equals] function.
     */
    protected open fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    protected open fun getChangePayload(oldItem: T, newItem: T): Any? {
        return true
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        differ = AsyncListDiffer(this, diffCallback)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        differ = null
    }
}
