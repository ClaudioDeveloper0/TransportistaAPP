package com.example.transportistaapp.data

import com.example.transportistaapp.data.network.UsersDao
import com.example.transportistaapp.domain.Repository
import com.example.transportistaapp.domain.model.RutaT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
//    private val paqueteDao: PaqueteDao,
//    private val rutaDao: RutaDao,
    private val usersDao: UsersDao,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth

) : Repository{
    override suspend fun loginTransportista(user: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(user, password).await()
            if (result.user != null){
                if (usersDao.esTransportista(result.user!!)){
                    return result.user
                }
            }
            return null
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRutasActivas(uid: String): List<RutaT> {
        val rutasSnapshot = firestore.collection("rutas")
            .whereEqualTo("uid", uid)
            .get()
            .await()

        return rutasSnapshot.documents.map { document ->
            RutaT(
                id = document.id,
                nombre = document.getString("nombre") ?: "",
                cargado = document.getBoolean("cargado") ?: false,
                validado = document.getBoolean("validado") ?: false
            )
        }
    }
}