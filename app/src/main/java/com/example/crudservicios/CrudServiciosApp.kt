package com.example.crudservicios

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase

class CrudServiciosApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}