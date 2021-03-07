package com.rutvik.apps.skreamsos.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.base.BaseActivity
import com.rutvik.apps.skreamsos.login.LoginActivity
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import com.rutvik.apps.skreamsos.utils.PermissionUtils
import com.rutvik.apps.skreamsos.utils.SharedPreferenceHelper
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaType


@Suppress("DEPRECATION")
class HomeActivity : BaseActivity(), HomeContract.HomeView {

    companion object{
        const val TAG = "HomeActivity"
        const val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZU51bWJlciI6Iis5MTk5MDAwMzMzMzMiLCJ1c2VySWQiOiI1ZjQyNDIxZDg4MGJiYzAwMTdjZTcwNjMiLCJpYXQiOjE1OTgxODg0OTR9.A8uZgATvlA4BW7ORuC0Cp5WaXZvKM1uEzTOw2JikZFo"
    }

    @Inject lateinit var presenter: HomePresenter
    @Inject lateinit var prefHelper: SharedPreferenceHelper

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private lateinit var locationCallback: LocationCallback
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var currentLocation: Location? = null
    private var currentLatitude: Double? = null
    private var currentLongitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent().inject(this)
        setContentView(R.layout.activity_home)

        presenter.attachView(this)
        prefHelper.init(this)

        setSupportActionBar(toolbar)
        setUpSideNavigationDrawer()
        updateHeaderUI()

        sosButton.setOnClickListener {
            sendSOS()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            intent.type = "image/*"
            startActivityForResult(cameraIntent, 111)
        }
    }

    override fun onStart() {
        super.onStart()
        if (PermissionUtils.isAccessFineLocationGranted(this)) {
            if (PermissionUtils.isLocationEnabled(this)) {
                setUpLocationListener()
            } else {
                PermissionUtils.showGPSNotEnabledDialog(this)
            }
        } else {
            PermissionUtils.requestAccessFineLocationPermission(this, PermissionUtils.RC_LOCATION_PERMISSION)
        }
        if (!PermissionUtils.isCameraPermissionGranted(this)) {
            PermissionUtils.requestCameraPermission(this, PermissionUtils.RC_CAMERA_PERMISSION)
        }
    }

    private fun setUpLocationListener() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    currentLocation = location
                }
                currentLatitude = currentLocation?.latitude
                currentLongitude = currentLocation?.longitude
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun setUpSideNavigationDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(this, homeDrawer, R.string.open, R.string.close)
        homeDrawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigation.itemIconTintList = null
        navigation.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.stop_sos -> presenter.deleteSOS()
                R.id.signout -> presenter.signOut()
                else -> return@OnNavigationItemSelectedListener true
            }
            true
        })
    }

    override fun sendSOS() {
        showToastLong("Latitude: $currentLatitude \nLongitude: $currentLongitude")
        val list =  ArrayList<Float>(2)
        list.add(currentLongitude!!.toFloat())
        list.add(currentLatitude!!.toFloat())
        val sos = SOSAlert("Point", list)
        presenter.sendSOS(sos)
    }

    override fun deleteSOS() {
        presenter.deleteSOS()
    }

    override fun updateHeaderUI() {
        val headerView = navigation.getHeaderView(0)
        val textHeaderName = headerView.findViewById<TextView>(R.id.textViewNavName)
        val textHeaderPhone = headerView.findViewById<TextView>(R.id.textViewNavPhNo)
        if (prefHelper.readBoolean(PrefConstants.HAS_USER_DETAILS)!!) {
            textHeaderName.text = prefHelper.readString(PrefConstants.USER_NAME)
            textHeaderPhone.text = prefHelper.readString(PrefConstants.USER_PHONE_NO)
        } else {
            val user = FirebaseHelper.getUser()
            textHeaderName.text = user?.displayName
            textHeaderName.text = user?.phoneNumber
        }
    }

    override fun signOutUser() {
        showToastLong("Signed Out!")
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return  super.onOptionsItemSelected(item!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionUtils.RC_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PermissionUtils.isLocationEnabled(this)) {
                        setUpLocationListener()
                    } else {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                } else {
                    showToastLong(getString(R.string.location_permission_not_granted))
                }
            }
            PermissionUtils.RC_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Open Camera
                } else {
                    showToastLong("Camera Permission is Required")
                    PermissionUtils.requestCameraPermission(this, PermissionUtils.RC_CAMERA_PERMISSION)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Returned!")
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            val imageBitmap = data?.extras?.get("data") as Bitmap
            val input = buildImageBodyPart("image", imageBitmap)
            presenter.sendSOSImage(input)
        }
    }

    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap):  MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = RequestBody.create("image/*".toMediaType(), leftImageFile)
        Log.d(TAG, leftImageFile.name)
        return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}
