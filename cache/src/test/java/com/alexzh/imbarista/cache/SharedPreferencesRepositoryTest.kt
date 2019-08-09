package com.alexzh.imbarista.cache

import android.content.Context
import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.cache.mapper.SessionMapper
import com.alexzh.imbarista.cache.mapper.UserMapper
import com.alexzh.testdata.data.GenerateDataTestData.generateSessionEntity
import com.alexzh.testdata.data.GenerateDataTestData.generateUserEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesRepositoryTest {

    private val userMapper = UserMapper()
    private val sessionMapper = SessionMapper()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val repository = SharedPreferencesRepository(prefs, sessionMapper, userMapper)

    @Test
    fun shouldSaveSessionInfoToSharedPreferences() {
        val session = generateSessionEntity()
        repository.saveSessionInfo(session)

        assertEquals(session, repository.getSessionInfo())
    }

    @Test
    fun shouldProvideDefaultValueAfterRemovingExistingSessionInfo() {
        val emptySessionEntity = SessionEntity()

        repository.saveSessionInfo(generateSessionEntity())
        repository.clearSessionInfo()

        assertEquals(emptySessionEntity, repository.getSessionInfo())
    }

    @Test
    fun shouldProvideDefaultValuesForSessionWhenNoDataInSharedPreferences() {
        val emptySessionEntity = SessionEntity()
        assertEquals(emptySessionEntity, repository.getSessionInfo())
    }

    @Test
    fun shouldSaveUserInfoToSharedPreferences() {
        val user = generateUserEntity()
        repository.saveUserEntity(user)

        assertEquals(user, repository.getUserInfo())
    }

    @Test
    fun shouldProvideDefaultValuesAfterRemovingExistingUserInfo() {
        val emptyUserEntity = UserEntity()

        repository.saveUserEntity(generateUserEntity())
        repository.clearUserInfo()

        assertEquals(emptyUserEntity, repository.getUserInfo())
    }

    @Test
    fun shouldProvideDefaultValuesForUserWhenNoDataInSharedPreferences() {
        val emptyUserEntity = UserEntity()
        assertEquals(emptyUserEntity, repository.getUserInfo())
    }

    @After
    fun tearDown() {
        prefs.edit().clear()
    }
}