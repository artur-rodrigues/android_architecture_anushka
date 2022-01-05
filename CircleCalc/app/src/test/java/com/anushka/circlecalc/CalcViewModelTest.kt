package com.anushka.circlecalc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CalcViewModelTest {

    private lateinit var viewModel: CalcViewModel
    private lateinit var calculations: Calculations

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val radius = 2.1
    private val returnedArea = 13.8474
    private val returnedCircumference = 13.188

    @Before
    fun setUp() {
        calculations = mock(Calculations::class.java)
        `when`(calculations.calculateArea(radius)).thenReturn(returnedArea)
        `when`(calculations.calculateCircumference(radius)).thenReturn(returnedCircumference)
        viewModel = CalcViewModel(calculations)
    }

    @Test
    fun calculateArea_radiusSent_updateLiveData() {
        viewModel.calculateArea(radius)
        val value = viewModel.areaValue.value
        assertThat(value).isEqualTo(returnedArea.toString())
    }

    @Test
    fun calculateCircumference_radiusSent_updateLiveData() {
        viewModel.calculateCircumference(radius)
        val value = viewModel.circumferenceValue.value
        assertThat(value).isEqualTo(returnedCircumference.toString())
    }
}