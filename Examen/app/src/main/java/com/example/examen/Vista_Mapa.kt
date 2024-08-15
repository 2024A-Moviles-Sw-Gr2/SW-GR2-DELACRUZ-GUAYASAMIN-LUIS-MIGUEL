package com.example.examen

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Vista_Mapa : AppCompatActivity(), OnMapReadyCallback {

    private var idEstudiante: Int = -1
    private lateinit var mapa: GoogleMap
    private lateinit var tvTitulo: TextView
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_mapa)

        idEstudiante = intent.getIntExtra("ID_ESTUDIANTE", -1)
        tvTitulo = findViewById<TextView>(R.id.tv_titulo)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)


        solicitarPermisos()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap
        iniciarLogicaMapa()


        val estudiante = ControladorBase.tablaEstudiante?.obtenerEstudiantePorId(idEstudiante)
        val latitud = estudiante!!.latitud
        val longitud = estudiante!!.longitud
        val coordenadas = LatLng(latitud, longitud)
        val zoom = 17f
        tvTitulo.text = "Domicilio de ${estudiante.nombre}"
        moverCamaraConZoom(coordenadas, zoom)
        anadirMarcador(coordenadas, estudiante.nombre)
    }

    fun solicitarPermisos() {
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

    fun iniciarLogicaMapa() {
        establecerConfiguracionMapa()
    }

    fun establecerConfiguracionMapa() {
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

    fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLang, zoom
            )
        )
    }

    fun anadirMarcador(latLang: LatLng, title: String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLang)
                .title(title)
        )!!
    }
}