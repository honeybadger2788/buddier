package com.example.buddier.core.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject
import com.example.buddier.core.delegate.WeakReferenceDelegate

class DialogFragmentLauncher @Inject constructor() : LifecycleObserver {

    private var activity: FragmentActivity? by WeakReferenceDelegate()
    private var dialogFragment: DialogFragment? by WeakReferenceDelegate()

    fun show(dialogFragment: DialogFragment, activity: FragmentActivity) {
        if (activity.lifecycle.currentState.isAtLeast(RESUMED)) {
            dialogFragment.show(activity.supportFragmentManager, null)
        } else {
            this.activity = activity
            this.dialogFragment = dialogFragment
            activity.lifecycle.addObserver(this@DialogFragmentLauncher)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onActivityResumed() {
        val activity = activity ?: return
        val dialogFragment = dialogFragment ?: return

        dialogFragment.show(activity.supportFragmentManager, null)
        activity.lifecycle.removeObserver(this)
    }
}
