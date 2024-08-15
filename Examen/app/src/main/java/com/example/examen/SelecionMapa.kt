package com.example.examen
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.examen.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SelecionMapa : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    var latitud = 0.0
    var longitud = 0.0
    private lateinit var mapa: GoogleMap
    private var marcadorActual: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecion_mapa)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_select) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        solicitarPermisos()
        iniciarLogicaMapa()
        val botonGuardar = findViewById<Button>(R.id.btn_map_select)
        botonGuardar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("respuesta", true)
            intent.putExtra("latitud", latitud)
            intent.putExtra("longitud", longitud)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap
        this.mapa.setOnMapClickListener(this)
        this.mapa.setOnMapLongClickListener(this)
        this.mapa.uiSettings.isZoomControlsEnabled = true
        this.mapa.uiSettings.isRotateGesturesEnabled = true
        moverAPosicionActual()
    }

    override fun onMapClick(latLng: LatLng) {
        latitud = latLng.latitude
        longitud = latLng.longitude
        anadirMarcador(latLng, "Nueva ubicación")
    }

    override fun onMapLongClick(latLng: LatLng) {
        latitud = latLng.latitude
        longitud = latLng.longitude
        anadirMarcador(latLng, "Nueva ubicación")
    }

    private fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    private fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map_select) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
                moverAPosicionActual()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun moverAPosicionActual() {
        if (permisos) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val ubicacionActual = LatLng(it.latitude, it.longitude)
                    moverCamaraConZoom(ubicacionActual, 15f)
                } ?: run {
                    // Si no se puede obtener la ubicación, mueve a una ubicación predeterminada
                    moverQuito()
                }
            }
        } else {
            // Si no se tienen permisos, mueve a una ubicación predeterminada
            moverQuito()
        }
    }

    private fun moverQuito() {
        val zoom = 15f
        val quito = LatLng(-0.1711387, -78.49228)
        moverCamaraConZoom(quito, zoom)
    }

    private fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLang, zoom
            )
        )
    }

    private fun anadirMarcador(latLang: LatLng, title: String) {
        marcadorActual?.remove()
        marcadorActual = mapa.addMarker(
            MarkerOptions().position(latLang)
                .title(title)
        )
    }
}
