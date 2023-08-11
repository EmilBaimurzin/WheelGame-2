package com.spin.game.ui.spin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spin.game.core.library.random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SpinViewModel : ViewModel() {
    private val _isSpinning = MutableLiveData(false)
    val isSpinning: LiveData<Boolean> = _isSpinning

    private val _rotation = MutableLiveData(0f)
    val rotation: LiveData<Float> = _rotation

    var spinCallback: ((Int) -> Unit)? = null

    fun spin() {
        viewModelScope.launch {
            _isSpinning.postValue(true)
            var value = (8..25).random().toFloat()
            val randomDelay = 10 random 13
            val randomDecrease = Random.nextFloat() * (0.044f - 0.067f) + 0.084f
            while (value > 0.10) {
                delay(randomDelay.toLong())
                if (value > 0.10f) {
                    value -= randomDecrease
                }
                if (value - randomDecrease < 0.10f) {
                    val values = listOf(
                        325, 400, 50, 400, 750, 600, 400, 125,
                        75, 400, 1000, 25, 500, 175, 400, 60, 30,
                        250, 575, 850
                    )
                    val totalSections = values.size
                    val degreesPerSection = 360f / totalSections
                    val normalizedDegrees = (_rotation.value!!) % 360f

                    val sectionIndex = normalizedDegrees / degreesPerSection
                    if (_isSpinning.value!!) {
                        _isSpinning.postValue(false)
                        spinCallback?.invoke(values[sectionIndex.toInt()])
                    }
                }
                _rotation.postValue(_rotation.value!! + value)
            }
        }
    }
}