package com.nyang.dayFlower.domain.model.flowerList

import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "document")
data class ResponseFlowerList(
    @Element(name="root")
    val root: RootFlowerList? = null
)

@Xml(name="root")
data class RootFlowerList(
    @PropertyElement(name="repcategory")
    val apiName: String? = null,
    @Element(name ="result")
    val result: List<FlowerDetail>? = null,
    @PropertyElement(name="resultCode")
    val resultCode: Int? = null,
    @PropertyElement(name="resultMsg")
    val resultMsg: String? = null,
    @PropertyElement(name="resultCnt")
    val resultCnt: Int? = null,
    @PropertyElement(name="pageNo")
    val pageNo: Int? = null,
    @PropertyElement(name="numOfRows")
    val numOfRows: Int? = null
)