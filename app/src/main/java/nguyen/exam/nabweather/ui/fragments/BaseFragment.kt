package nguyen.exam.nabweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Create by Nguyen on 02/06/2022
 */
abstract class BaseFragment<DATA_BINDING : ViewDataBinding> : Fragment() {

    protected abstract val layoutResourceId: Int

    protected abstract fun setupView()
    protected abstract fun setupViewModel()

    lateinit var binding: DATA_BINDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutResourceId,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupView()
    }
}