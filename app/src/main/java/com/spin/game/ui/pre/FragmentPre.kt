package com.spin.game.ui.pre

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.spin.game.R
import com.spin.game.databinding.FragmentPreBinding
import com.spin.game.ui.other.ViewBindingFragment

class FragmentPre : ViewBindingFragment<FragmentPreBinding>(FragmentPreBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.play.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentSpin)
        }

        binding.exit.setOnClickListener {
            requireActivity().finish()
        }
        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}