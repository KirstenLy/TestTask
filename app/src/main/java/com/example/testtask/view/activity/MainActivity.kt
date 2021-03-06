package com.example.testtask.view.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sdk.core.network.NetworkHelper
import com.example.sdk.extensions.gone
import com.example.sdk.extensions.visible
import com.example.testtask.App
import com.example.testtask.R
import com.example.testtask.di.ViewModelFactory
import com.example.testtask.failure.DatabaseFailure
import com.example.testtask.failure.Failure
import com.example.testtask.view.dialog.ErrorDialog
import com.example.testtask.view.dialog.NoConnectionDialog
import com.example.testtask.view.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NoConnectionDialog.OnNoNetworkConnection {

    private val TAG_FRAGMENT_NO_CONNECTION: String = "NoConnectionTag"

    @Inject lateinit var factory: ViewModelFactory
    @Inject lateinit var networkChecker: NetworkHelper

    private lateinit var noInternetConnectionDialog: NoConnectionDialog
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.get().injector?.inject(this)

        sharedViewModel = ViewModelProviders.of(this, factory)[SharedViewModel::class.java]

        sharedViewModel.networkStatusLiveData.observe(this, Observer { isInternetAvailable ->
            if (isInternetAvailable) {
                networkChecker.isOfflineModeEnabled = false
                onAppWorkingModeSelected()
            } else {
                showNoConnectionDialog()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //If NoConnectionDialog showing, restore it to prevent NPE
        restoreNoConnectionDialog()
    }

    private fun showNoConnectionDialog() {
        noInternetConnectionDialog = NoConnectionDialog()
        noInternetConnectionDialog.show(supportFragmentManager, TAG_FRAGMENT_NO_CONNECTION)
    }

    private fun onAppWorkingModeSelected() {
        sharedViewModel.setData()

        sharedViewModel.progressBarLiveData.observe(this,
            Observer<Boolean> { state -> setLoading(state) })

        sharedViewModel.errorLiveData.observe(this, Observer { failure ->
            when (failure){
                is Failure.NetworkConnection ->showNoConnectionDialog()
                is Failure.ServerError ->{ showErrorDialog(getString(R.string.failure_network, getString(R.string.failure_network_server_error))) }
                is DatabaseFailure.EmptyDatabase ->{ showErrorDialog(getString(R.string.failure_database,getString(R.string.failure_database_empty))) }
            }
        })
    }

    private fun showErrorDialog(error:String){
        ErrorDialog.getInstance(error).show(supportFragmentManager, "ErrorTag")
    }

    private fun setLoading(state: Boolean) {
        if (state) progress.visible() else progress.gone()
    }

    private fun showMessage(id: Int) {
        Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show()
    }

    //It's single activity app, so close Activity equals close app
    fun closeApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity()
        } else {
            this.finish()
        }
    }

    private fun restoreNoConnectionDialog() {
        if (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_NO_CONNECTION) != null) {
            noInternetConnectionDialog = (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_NO_CONNECTION) as NoConnectionDialog)
        }
    }

    //NoInternetFragment methods
    override fun onRetryConnectionClick() {
        if (!networkChecker.isConnectedToNetwork()) {
            showMessage(R.string.base_error_no_connection)
        } else {
            noInternetConnectionDialog.dismiss()
            networkChecker.isOfflineModeEnabled = false
            onAppWorkingModeSelected()
        }
    }

    override fun onOfflineModeClick() {
        noInternetConnectionDialog.dismiss()
        networkChecker.isOfflineModeEnabled = true
        onAppWorkingModeSelected()
    }

    override fun onExitClick() {
        closeApp()
    }
}
