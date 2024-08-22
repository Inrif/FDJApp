package com.abbesolo.fdjapp.domain

import com.abbesolo.fdjapp.data.models.League
import com.abbesolo.fdjapp.data.repositories.SportsRepository
import com.abbesolo.fdjapp.domain.usecase.GetLeaguesUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Created by HOUNSA ROMUALD on 22/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */

@ExperimentalCoroutinesApi
class GetLeaguesUseCaseTest {

    private lateinit var repository: SportsRepository
    private lateinit var getLeaguesUseCase: GetLeaguesUseCase


    @Before
    fun setUp() {
        repository = mock()
        getLeaguesUseCase = GetLeaguesUseCase(repository)
    }

    @Test
    fun `invoke should return list of leagues when repository returns data successfully`() = runTest {
        // Arrange
        val expectedLeagues = listOf(
            League(idLeague = "1", strLeague = "Premier League", strSport = "Soccer", strLeagueAlternate = "EPL", strBadge = "logo_url_1"),
            League(idLeague = "2", strLeague = "La Liga", strSport = "Soccer", strLeagueAlternate = "LL", strBadge = "logo_url_2")
        )
        whenever(repository.getAllLeagues()).thenReturn(expectedLeagues)

        // Act
        val result = getLeaguesUseCase()

        // Assert
        assertThat(result).isEqualTo(expectedLeagues)
        verify(repository, times(1)).getAllLeagues()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `invoke should throw exception when repository throws exception`() = runTest {
        // Arrange
        val exception = RuntimeException("Network Error")
        whenever(repository.getAllLeagues()).thenThrow(exception)

        // Act & Assert
        try {
            getLeaguesUseCase()
            assert(false) { "Exception was expected but not thrown" }
        } catch (e: Exception) {
            assertThat(e).isEqualTo(exception)
        }
        verify(repository, times(1)).getAllLeagues()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Arrange
        whenever(repository.getAllLeagues()).thenReturn(emptyList())

        // Act
        val result = getLeaguesUseCase()

        // Assert
        assertThat(result).isEmpty()
        verify(repository, times(1)).getAllLeagues()
        verifyNoMoreInteractions(repository)
    }
}