package com.nyangzzi.dayFlower.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class GetFirebaseRepositoryImpl : GetFirebaseRepository {
    override suspend fun recommendedSearchWords(): Flow<ResultWrapper<List<String>>> =
        callbackFlow {

            val db = FirebaseFirestore.getInstance()

            db.collection("common")   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    
                    trySend(
                        ResultWrapper.Success(result.map { document ->
                            (document["meaning"] as String).split(",")
                        }.last())
                    )

                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    trySend(ResultWrapper.Error("오류가 발생했습니다"))
                }

            awaitClose { channel.close() }
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(Dispatchers.IO)
}