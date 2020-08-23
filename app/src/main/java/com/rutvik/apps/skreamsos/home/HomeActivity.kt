package com.rutvik.apps.skreamsos.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.base.BaseActivity
import com.rutvik.apps.skreamsos.login.LoginActivity
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import com.rutvik.apps.skreamsos.utils.PermissionUtils
import com.rutvik.apps.skreamsos.utils.SharedPreferenceHelper
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

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

        sosButton.setOnClickListener { sendSOS() }
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
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
        list.add(19.2222F)
        list.add(21.2222F)
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
        }
    }
}
