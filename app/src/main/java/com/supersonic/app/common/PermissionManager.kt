package com.supersonic.app.common

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

class PermissionManager(private var mContext: AppCompatActivity, private var mCallback: PermissionResponseCallback) {

    private val REQ_PERMS = 899789

    interface PermissionResponseCallback {

        fun onPermissionGranted()

        fun onPermissionDenied()

    }

    fun checkPermissions(permissions: Array<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pendingPermssions = arrayListOf<String>()
            for (permission in permissions) {
                if (mContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    pendingPermssions.add(permission)
                }
            }

            if (pendingPermssions.isNotEmpty()) {
                askForPermission(pendingPermssions)
            } else {
                mCallback.onPermissionGranted()
            }
        } else {
            mCallback.onPermissionGranted()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun askForPermission(permission: ArrayList<String>) {
        mContext.requestPermissions(permission.toTypedArray(), REQ_PERMS)
    }

    fun rectifyPermissionResult(grantResults: IntArray, permissions: Array<out String>) {
        val deniedPermissions = arrayListOf<String>()
        for (i in 0 until grantResults.size) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i])
            }
        }

        if (deniedPermissions.isNotEmpty()) {
            checkPermissions(deniedPermissions.toTypedArray())
        } else {
            mCallback.onPermissionGranted()
        }
    }

}