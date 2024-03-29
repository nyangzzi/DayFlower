package com.nyangzzi.dayFlower.domain.usecase.login.kakao

import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.User
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(): Flow<ResultWrapper<User>> =
        repository.kaKaoLogin()
}