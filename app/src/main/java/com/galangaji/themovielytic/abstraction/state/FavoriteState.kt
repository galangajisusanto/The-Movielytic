package com.galangaji.themovielytic.abstraction.state

sealed class FavoriteState {
    object InsertSuccess : FavoriteState()
    object DeleteSuccess : FavoriteState()
}