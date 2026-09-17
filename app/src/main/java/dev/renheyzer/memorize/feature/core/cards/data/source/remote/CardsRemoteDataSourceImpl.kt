package dev.renheyzer.memorize.feature.core.cards.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dev.renheyzer.memorize.feature.core.cards.data.dto.CardsResultDto
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

private const val USERS_COLLECTION = "users"
private const val CARDS_COLLECTION = "cards"

class CardsRemoteDataSourceImpl(
    private val ioDispatcher: CoroutineContext,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : CardsRemoteDataSource {

    override val userId: String
        get() = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    override suspend fun saveGameResult(result: CardsResultDto): Unit = withContext(ioDispatcher) {
        val userId =
            firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

        val docRef = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CARDS_COLLECTION)
            .document()

        val data = result.copy(id = docRef.id)
        docRef.set(data).await()
    }

    override suspend fun fetchGameResults(): List<CardsResultDto> = withContext(ioDispatcher) {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(CARDS_COLLECTION)
            .get().await().toObjects<CardsResultDto>()
    }
}