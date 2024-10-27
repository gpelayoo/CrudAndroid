package com.example.crudservicios.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudservicios.R
import com.example.crudservicios.data.model.ServiceModel
import com.example.crudservicios.data.repository.ServiceRepository
import com.example.crudservicios.databinding.ActivityMainBinding
import com.example.crudservicios.presentation.adapter.ServiceAdapter
import com.example.crudservicios.presentation.common.UiState
import com.example.crudservicios.presentation.common.makeCall
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import pe.pcs.libpcs.UtilsCommon
import pe.pcs.libpcs.UtilsMessage

class MainActivity : AppCompatActivity(), ServiceAdapter.IOnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

        leerServicio("")
    }

    override fun onResume() {
        super.onResume()

        if(!existeCambio) return
        existeCambio = false
        leerServicio(binding.etBuscar.text.toString().trim())
    }

    private fun initListener() {

        binding.includeToolbar.ibAccion.setOnClickListener {
            startActivity(Intent(this, OperacionServiceActivity::class.java))
        }

        binding.rvLista.apply {
            adapter = ServiceAdapter(this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.tilBuscar.setEndIconOnClickListener {
            leerServicio(binding.etBuscar.text.toString().trim())
            UtilsCommon.hideKeyboard(this@MainActivity, it)
        }

    }

    private fun leerServicio(dato: String) = lifecycleScope.launch {
        binding.progressBar.isVisible= true

        makeCall { ServiceRepository().listar(dato) }.let {
            when(it){
                is UiState.Error -> {
                    binding.progressBar.isVisible= false
                    UtilsMessage.showAlertOk(
                        "Error", it.message, this@MainActivity
                    )
                }
                is UiState.Success -> {
                    binding.progressBar.isVisible= false
                    (binding.rvLista.adapter as ServiceAdapter).setLista(it.data)
                }
            }
        }
    }

    private fun eliminar(model: ServiceModel) = lifecycleScope.launch {
        binding.progressBar.isVisible= true

        makeCall { ServiceRepository().eliminar(model) }.let {
            when(it){
                is UiState.Error -> {
                    binding.progressBar.isVisible= false
                    UtilsMessage.showAlertOk(
                        "Error", it.message, this@MainActivity
                    )
                }
                is UiState.Success -> {
                    binding.progressBar.isVisible= false
                    UtilsMessage.showToast(this@MainActivity, "Registro eliminado")
                    leerServicio(binding.etBuscar.text.toString().trim())
                }
            }
        }
    }

    private fun extracted() {
        TODO()
    }

    override fun clickEditar(serviceModel: ServiceModel) {
        startActivity(
            Intent(this, OperacionServiceActivity::class.java).apply {
                putExtra("id", serviceModel.id)
                putExtra("descripcion", serviceModel.descripcion)
                putExtra("nombreservicio", serviceModel.nombreservicio)
                putExtra("precio", serviceModel.precio)
            }
        )
    }

    override fun clickEliminar(serviceModel: ServiceModel) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Eliminar")
            setMessage("¿Desea eliminar el registro ${serviceModel.descripcion}?")
            setCancelable(false)

            setPositiveButton("Si") { dialog, _ ->
                eliminar(serviceModel)
                leerServicio(binding.etBuscar.text.toString().trim())
                dialog.dismiss()
            }

            setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    companion object {
        var existeCambio = false
    }
}