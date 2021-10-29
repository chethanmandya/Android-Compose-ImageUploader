package com.chethan.pagingSample.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.chethan.pagingSample.utils.extension.findActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

/**
 * Instantiate and manage it in composition like this
 */

@Composable
fun checkSelfPermissionState(
    permission: String
): PermissionState? {

    val context = LocalContext.current
    val activity = context.findActivity()

    val call = remember(activity, permission) {
        activity?.let { PermissionResultCall(it, permission) }
    }
    // drive initialCheck and unregister from composition lifecycle
    DisposableEffect(call) {
        call?.initialCheck()
        onDispose {
            call?.unregister()
        }
    }
    return call?.checkSelfPermission()
}

class PermissionState(
    private val permission: String,
    val hasPermission: Flow<Boolean>,
    val shouldShowRationale: Flow<Boolean>,
    private val launcher: ActivityResultLauncher<String>
) {
    fun launchPermissionRequest() = launcher.launch(permission)
}

private class PermissionResultCall(
    private val activity: AppCompatActivity,
    private val permission: String
) {

    private val hasPermission = MutableStateFlow(false)
    private val showRationale = MutableStateFlow(false)

    private var call: ActivityResultLauncher<String> = activity.activityResultRegistry.register(
        "LocationPermissions#",
        ActivityResultContracts.RequestPermission()
    ) { result ->
        onPermissionResult(result)
    }

    /**
     * Call this after [Activity.onCreate] to perform the initial permissions checks
     */
    fun initialCheck() {
        hasPermission.value = checkPermission()
        showRationale.value = checkShowRationale()
    }

    fun unregister() {
        call.unregister()
    }

    fun checkSelfPermission(): PermissionState {
        return PermissionState(
            permission,
            hasPermission.filterNotNull(),
            showRationale.filterNotNull(),
            call
        )
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun checkShowRationale(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.shouldShowRequestPermissionRationale(permission)
        } else {
            false
        }
    }

    private fun onPermissionResult(result: Boolean) {
        hasPermission.value = result
        showRationale.value = checkShowRationale()
    }
}
