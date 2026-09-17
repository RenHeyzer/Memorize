package dev.renheyzer.memorize.feature.core.numbers.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dev.renheyzer.memorize.feature.core.numbers.data.dto.NumbersResultDto
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

private const val NUMBERS_COLLECTION = "numbers"
private const val USERS_COLLECTION = "users"

class NumbersRemoteDataSourceImpl(
    private val ioDispatcher: CoroutineContext,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : NumbersRemoteDataSource {

    override val userId: String
        get() = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    override suspend fun saveGameResults(result: NumbersResultDto): Unit =
        withContext(ioDispatcher) {
            val docRef = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(NUMBERS_COLLECTION)
                .document()

            val data = result.copy(id = docRef.id)
            docRef.set(data).await()
        }

    override suspend fun fetchGameResults(): List<NumbersResultDto> = withContext(ioDispatcher) {
        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .collection(NUMBERS_COLLECTION)
            .get().await().toObjects<NumbersResultDto>()
    }
}