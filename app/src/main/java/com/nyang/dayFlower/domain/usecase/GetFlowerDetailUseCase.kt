package com.nyang.dayFlower.domain.usecase

import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFlowerDetailUseCase @Inject constructor(
    private val repository: SearchFlowerRepository
){
    suspend operator fun invoke(requestFlowerDetail: RequestFlowerDetail) : Flow<FlowerDetail> = flow{
        emit(repository.getFlowerDetail(requestFlowerDetail))
    }
}