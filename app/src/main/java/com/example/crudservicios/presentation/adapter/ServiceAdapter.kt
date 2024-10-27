package com.example.crudservicios.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudservicios.data.model.ServiceModel
import com.example.crudservicios.databinding.ItemsServicioBinding
import com.google.firebase.firestore.core.View

class ServiceAdapter (
    private val listener: IOnClickListener
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    private var lista = emptyList<ServiceModel>()

    interface IOnClickListener {
        fun clickEditar(serviceModel: ServiceModel)
        fun clickEliminar(serviceModel: ServiceModel)
    }

    inner class ServiceViewHolder(private val binding: ItemsServicioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun enlazar(serviceModel: ServiceModel) {
            binding.tvTitulo.text = serviceModel.descripcion
            binding.tvNombreServicio.text = serviceModel.nombreservicio
            binding.tvPrecio.text = pe.pcs.libpcs.UtilsCommon.formatFromDoubleToString(serviceModel.precio)

            binding.ibEditar.setOnClickListener {
                listener.clickEditar(serviceModel)
            }

            binding.ibEliminar.setOnClickListener {
                listener.clickEliminar(serviceModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(
            ItemsServicioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.enlazar(lista[position])
    }

    fun setLista(_lista: List<ServiceModel>) {
        this.lista = _lista
        notifyDataSetChanged()
    }
}