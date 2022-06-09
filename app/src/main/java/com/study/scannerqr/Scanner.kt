package com.study.scannerqr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.study.scannerqr.core.App
import com.study.scannerqr.utils.SharedPreferencesHelper
import me.dm7.barcodescanner.zxing.ZXingScannerView

class Scanner: Fragment(), ZXingScannerView.ResultHandler {

    companion object{
        private const val SELECTED_FORMATS = "SELECTED_FORMATS"
        private const val CAMERA_ID = "CAMERA_ID"
    }

    private var scanner: ZXingScannerView? = null
    private var selectedIndices: ArrayList<Int>? = null
    private var cameraId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View? {
        scanner = ZXingScannerView(activity)
        if(state!= null){
            selectedIndices = state.getIntegerArrayList(SELECTED_FORMATS)
            cameraId = state.getInt(CAMERA_ID)
        } else{
            selectedIndices = null
            cameraId= -1
        }
        setupFormats()
        scanner!!.setAspectTolerance(0.5f)
        return scanner
    }

    override fun onResume() {
        super.onResume()
        scanner?.setResultHandler(this)
        scanner?.startCamera()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(SELECTED_FORMATS, selectedIndices)
        outState.putInt(CAMERA_ID, cameraId)
    }

    override fun handleResult(rawResult: Result?) {
        if(rawResult != null){
            SharedPreferencesHelper.getInstance(App.instance).setBarcodeScanned(rawResult.text)
        }
    }

    private fun setupFormats(){
        scanner?.setFormats(arrayListOf(BarcodeFormat.CODE_128))
    }

    override fun onPause() {
        super.onPause()
        scanner?.stopCamera()
    }
}