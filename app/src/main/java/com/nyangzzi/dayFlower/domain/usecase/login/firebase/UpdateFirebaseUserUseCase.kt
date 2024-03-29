package com.nyangzzi.dayFlower.domain.usecase.login.firebase

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFirebaseUserUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(user: User): Flow<ResultWrapper<Unit>> =
        repository.updateFirebaseUser(user)
}