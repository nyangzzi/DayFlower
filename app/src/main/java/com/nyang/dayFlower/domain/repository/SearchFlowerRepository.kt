package com.nyang.dayFlower.domain.repository

import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail

interface SearchFlowerRepository {
    suspend fun getFlowerDetail(requestFlowerDetail: RequestFlowerDetail) : FlowerDetail
}