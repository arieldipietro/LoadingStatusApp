package com.udacity


sealed class ButtonState {
    //object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()

    fun setStateCompleted(){
        ButtonState.Completed
    }
}