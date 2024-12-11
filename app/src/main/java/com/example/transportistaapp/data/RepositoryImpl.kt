package com.example.transportistaapp.data

import com.example.transportistaapp.data.database.dao.PaqueteDao
import com.example.transportistaapp.data.database.dao.RutaDao
import com.example.transportistaapp.data.database.entities.PaqueteEntity
import com.example.transportistaapp.data.database.entities.RutaEntity
import com.example.transportistaapp.data.database.entities.toDomain
import com.example.transportistaapp.data.database.entities.toRoom
import com.example.transportistaapp.data.network.FirestoreService
import com.example.transportistaapp.data.network.RutasApi
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.domain.model.Ruta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth,
    private val rutaDao: RutaDao,
    private val paqueteDao: PaqueteDao

) : Repository{
    override suspend fun loginTransportista(user: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(user, password).await()
            if (result.user != null) {
                if (firestoreService.esTransportista(result.user!!)) {
                    return result.user
                }
            }
            return null
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRutasActivas(uid: String): List<Ruta> {
        val rutasLocal = rutaDao.getAll().map { it.toDomain() }
        return if (rutasLocal.any { it.enReparto }) {

            val paquetesLocal = mutableListOf<Paquete>()
            paquetesLocal.addAll(paqueteDao.getAll().map { it.toDomain() })

            val paquetesLocalPorRuta = paquetesLocal.groupBy { it.ruta }
            rutasLocal.forEach { ruta ->
                ruta.paquetes = paquetesLocalPorRuta[ruta.id] ?: emptyList()
            }
            rutasLocal
        } else {
            val rutas = firestoreService.obtenerRutasPorTrabajador(uid)

            val paquetesEntity = mutableListOf<PaqueteEntity>()
            val rutasEntity = mutableListOf<RutaEntity>()
            rutas.forEach { ruta ->
                paquetesEntity.addAll(ruta.paquetes.map { it.toRoom() })
            }
            rutasEntity.addAll(rutas.map { it.toRoom() })
            rutaDao.deleteAll()
            paqueteDao.deleteAll()
            rutaDao.insertAll(rutasEntity)
            paqueteDao.insertAll(paquetesEntity)


            return rutas
        }
    }

    override suspend fun marcarCajaEntregada(cajaId: String, fechaEntrega : Date) {
        paqueteDao.entregar(cajaId, fechaEntrega)
    }

    override suspend fun marcarCajaNoEntregada(cajaId: String) {
        paqueteDao.entregaFallida(cajaId)
    }

    override suspend fun obtenerPaquetesNoEntregados(): List<Paquete>? {
        val rutas = rutaDao.rutasEnReparto()
        val paquetesEntities = mutableListOf<PaqueteEntity>()
        rutas.forEach {
            paquetesEntities.addAll(paqueteDao.obtenerPorRuta(it.id))
        }
        val paquetes = paquetesEntities.filter { it.estado >= 2 }.map {
            it.toDomain()
        }
        return paquetes.ifEmpty { null }

    }

    override suspend fun terminarEntrega() {
        val rutas = rutaDao.rutasEnReparto()
        val paquetes = mutableListOf<PaqueteEntity>()
        rutas.forEach {
            paquetes.addAll(paqueteDao.obtenerPorRuta(it.id))
        }
        firestoreService.terminarRutas(rutas.map {it.toDomain()})
        firestoreService.terminarPaquetes(paquetes.map { it.toDomain() })
    }

    override suspend fun getPaquetesByRoute(routeId: String): List<Paquete> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePaqueteStatus(paqueteId: String, nuevoEstado: String) {
        TODO("Not yet implemented")
    }

}