package com.spin.game.ui.spin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spin.game.databinding.FragmentSpinBinding
import com.spin.game.ui.other.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentSpin : ViewBindingFragment<FragmentSpinBinding>(FragmentSpinBinding::inflate) {
    private val sp by lazy {
        requireActivity().getSharedPreferences("SP", Context.MODE_PRIVATE)
    }
    private val viewModel: SpinViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setText()
        binding.plus.isVisible = false

        viewModel.spinCallback = {
            setBalance(it)
            setText()
            lifecycleScope.launch {
                binding.plus.text = "+ $it"
                binding.plus.isVisible = true
                delay(500)
                binding.plus.isVisible = false
            }
        }

        binding.restart.setOnClickListener {
            resetBalance()
        }

        binding.menu.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buy.setOnClickListener {
            if (!viewModel.isSpinning.value!!) {
                if (getBalance() >= 300) {
                    setBalance(-300)
                    setText()
                    viewModel.spin()
                }
            }
        }

        viewModel.isSpinning.observe(viewLifecycleOwner) {
            binding.buy.isVisible = !it
        }

        viewModel.rotation.observe(viewLifecycleOwner) {
            binding.wheel.rotation = -it
        }
    }

    private fun getBalance(): Int = sp.getInt("BALANCE", 1500)
    private fun setBalance(value: Int) = sp.edit().putInt("BALANCE", getBalance() + value).apply()

    private fun resetBalance() {
        sp.edit().putInt("BALANCE", 1500).apply()
        setText()
    }

    private fun setText() {
        binding.balance.text = getBalance().toString()
    }
}