package com.limheejin.kidstopia.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.limheejin.kidstopia.databinding.DialogBinding

class CloseDialog (): DialogFragment() {

    private var _binding: DialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBinding.inflate(inflater, container, false)
        binding.apply {
            btnCancel.setOnClickListener{
                dismiss()
            }
            btnConfirm.setOnClickListener{
                System.exit(0)
            }
        }
        return binding.root
    }

//    fun Context.dialogResize(dialog: DialogFragment, width: Float, height: Float){
//        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        if (Build.VERSION.SDK_INT < 30){
//            val display = windowManager.defaultDisplay
//            val size = Point()
//            val window = dialogFragment.dialog?.window
//        }
//    }
}
