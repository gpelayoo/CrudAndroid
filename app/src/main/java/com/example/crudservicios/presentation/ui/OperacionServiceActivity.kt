package com.example.crudservicios.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.crudservicios.R
import com.example.crudservicios.data.model.ServiceModel
import com.example.crudservicios.data.repository.ServiceRepository
import com.example.crudservicios.databinding.ActivityMainBinding
import com.example.crudservicios.databinding.ActivityOperacionServiceBinding
import com.example.crudservicios.presentation.common.UiState
import com.example.crudservicios.presentation.common.makeCall
import kotlinx.coroutines.launch
import pe.pcs.libpcs.UtilsCommon
import pe.pcs.libpcs.UtilsMessage

class OperacionServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperacionServiceBinding
    private var _id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperacionServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

        if(intent.extras != null)
            obtenerServicio()
    }

    private fun initListener() {
        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            subtitle = "Registrar | Editar servicio"
            navigationIcon = AppCompatResources.getDrawable(
                this@OperacionServiceActivity, R.drawable.baseline_arrow_back_24
            )
        }

        binding.includeToolbar.ibAccion.setImageResource(R.drawable.baseline_done_all_24)

        binding.includeToolbar.ibAccion.setOnClickListener {
            if(binding.etDescripcion.text.toString().trim().isEmpty() ||
                binding.etNombreServicio.text.toString().trim().isEmpty() ||
                binding.etPrecio.text.toString().trim().isEmpty()) {
                UtilsMessage.showAlertOk(
                    "Error", "Debe llenar todos los campos", this@OperacionServiceActivity
                )
                return@setOnClickListener
            }

            grabar(
                ServiceModel(
                    id = _id,
                    descripcion = binding.etDescripcion.text.toString(),
                    nombreservicio = binding.etNombreServicio.text.toString(),
                    precio = binding.etPrecio.text.toString().toDouble()
                )
            )
        }
    }

    private fun obtenerServicio() {
        _id = intent.extras?.getString("id") ?: ""
        binding.etDescripcion.setText(intent.extras?.getString("descripcion"))
        binding.etNombreServicio.setText(intent.extras?.getString("nombreservicio"))
        binding.etPrecio.setText(intent.extras?.getDouble("precio").toString())

    }

    private fun grabar(model: ServiceModel) = lifecycleScope.launch {
        binding.progressBar.isVisible= true

        makeCall { ServiceRepository().grabar(model) }.let {
            when(it){
                is UiState.Error -> {
                    binding.progressBar.isVisible= false
                    UtilsMessage.showAlertOk(
                        "Error", it.message, this@OperacionServiceActivity
                    )
                }
                is UiState.Success -> {
                    binding.progressBar.isVisible= false
                    UtilsMessage.showToast(this@OperacionServiceActivity, "Registro grabado")
                    UtilsCommon.cleanEditText(binding.root.rootView)
                    UtilsCommon.hideKeyboard(this@OperacionServiceActivity, binding.root.rootView)
                    binding.etDescripcion.requestFocus()
                    _id = ""
                    MainActivity.existeCambio = true
                }
            }
        }
    }
}