package com.nyangzzi.dayFlower.presentation.feature.login

sealed class LoginEvent {
    object KakaoLogin : LoginEvent()
    object NaverLogin : LoginEvent()
    object ClearToastMsg : LoginEvent()
    object ClearDataStore : LoginEvent()
    object LoadApp : LoginEvent()
}