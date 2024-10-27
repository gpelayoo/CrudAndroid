package com.example.crudservicios.data.repository

import com.example.crudservicios.data.common.FirestoreConstants
import com.example.crudservicios.data.common.FirestoreInstance
import com.example.crudservicios.data.model.ServiceModel
import kotlinx.coroutines.tasks.await

class ServiceRepository {

    suspend fun listar(dato: String): List<ServiceModel> {
        return FirestoreInstance.get().collection(FirestoreConstants.COLECCION_SERVICIO)
            .orderBy("descripcion").startAt(dato).endAt(dato + "\uf8ff").get()
            .await().toObjects(ServiceModel::class.java)
    }

    private suspend fun registrar(model: ServiceModel): Boolean {
        val documento= FirestoreInstance.get().collection(FirestoreConstants.COLECCION_SERVICIO).document()
        model.id = documento.id
        documento.set(model).await()

        return true
    }

    private suspend fun actualizar(model: ServiceModel): Boolean {
        val documento= FirestoreInstance.get().collection(FirestoreConstants.COLECCION_SERVICIO).document(model.id)
        documento.update(
            mapOf(
                "descripcion" to model.descripcion,
                "nombreservicio" to model.nombreservicio,
                "precio" to model.precio
            )
        ).await()

        return true
    }

    suspend fun eliminar(model: ServiceModel): Boolean {
        val documento= FirestoreInstance.get().collection(FirestoreConstants.COLECCION_SERVICIO)
            .document(model.id)
        documento.delete().await()

        return true
    }

    suspend fun grabar(model: ServiceModel):Boolean {
        return if (model.id.isEmpty()){
            registrar(model)
        }else{
            actualizar(model)
        }
    }
}