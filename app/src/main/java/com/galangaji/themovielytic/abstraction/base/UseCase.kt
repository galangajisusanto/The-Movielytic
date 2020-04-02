package com.galangaji.themovielytic.abstraction.base

abstract class UseCase<T> {
    abstract fun execute(): T
}