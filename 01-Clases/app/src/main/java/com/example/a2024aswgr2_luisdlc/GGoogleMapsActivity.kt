package com.example.a2024aswgr2_luisdlc

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super. onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
        val botonCarolina = findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina.setOnClickListener {
            val carolina = LatLng(-8.18221288005854652, -78.48553955554578)
            val zoom = 17f
            moverCamaraConZoom(carolina, zoom)
        }
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
        } else{
            ActivityCompat . requestPermissions (
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1)
    }
    }
    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                anadirPolilinea()
                anadirPoligono()
                escucharListeners()
            }
        }
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine =
                ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse =
                ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
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
            ))

    }

    fun anadirMarcador(latLang: LatLng, title:String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(latLang)
                .title(title)

        )!!
    }


    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            0.1755181190138262, -78.47918808450619)
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador (quicentro, titulo)
        markQuicentro.tag = titulo
        moverCamaraConZoom(quicentro, zoom)
    }
    fun anadirPolilinea(){
        with(mapa){
        val polilineaUno = mapa.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(-8.1758614481269219, -78.48571121689449),
                    LatLng(-8.17843634842358283, -78.48244965871446),
                    LatLng(-8.17843634842358283, -78.47927391522337))
        )
        polilineaUno.tag = "Polilinea-uno"
        }
    }

    fun anadirPoligono(){
        with(mapa){
        val poligonoUno = mapa.addPolygon(
            PolygonOptions().clickable(true)
                .add(
                    LatLng(-8.172342398151381, -78.48596878896792),
                    LatLng(-8.17568896758495734, -78.48124882187579),
                    LatLng(-8.17345819199934728, -78.47584868767286)))
            poligonoUno.tag = "Poligono-uno"
        }
    }

    fun escucharListeners() {
        mapa.setOnPolygonClickListener {
            mostrarSnackbar("setOnPolygonClickListener $it.tag")
        }
        mapa.setOnPolylineClickListener {
                mostrarSnackbar("setOnPolylineClickListener $it.tag")
        }
        mapa.setOnMarkerClickListener {
            mostrarSnackbar("setOnMarkerClickListener $it.tag")
            return@setOnMarkerClickListener true
        }

        mapa.setOnCameraMoveListener {
            mostrarSnackbar("setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            mostrarSnackbar("setOnCaneraMoveStartedListener")
        }

        mapa.setOnCameraIdleListener {
            mostrarSnackbar("setOnCameraIdleListener")
        }
    }


    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_google_maps),
            texto,
            Snackbar. LENGTH_INDEFINITE)
            snack.show()
    }

}