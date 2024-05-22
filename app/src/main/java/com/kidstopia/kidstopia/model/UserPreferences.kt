package com.kidstopia.kidstopia.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

// preferenceDataStore(name = "") 데이터스토어 설정 및 이름 설정
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

// DataStore를 통해 저장 및 불러오는 기능
class UserPreferences(context: Context) {
    private val dataStore = context.dataStore // 앞에서 정의한 DataStore 사용

    companion object { // 클래스의 모든 인스턴스에서 USER_NAME_KEY 공통으로 사용가능하도록 선언
        val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    val userNameFlow = dataStore.data
        .catch { exception -> // IO 예외 시 빈 기본설정 반환, 기타 예외 처리
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> // DataStore에서 읽은 데이터를 원하는 형식으로 변환
            preferences[USER_NAME_KEY] ?: "아무개 님!"
        }

    suspend fun updateUserName(name: String) { // 비동기 실행을 위한 suspend fun
        dataStore.edit { preferences -> // DataStore에 데이터 저장, preferences는 현재 저장된 데이터
            preferences[USER_NAME_KEY] = name + " 님!"
        }
    }
}