package com.example.buddier.core.ex

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.buddier.core.dialog.DialogFragmentLauncher

fun DialogFragment.show(launcher: DialogFragmentLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}