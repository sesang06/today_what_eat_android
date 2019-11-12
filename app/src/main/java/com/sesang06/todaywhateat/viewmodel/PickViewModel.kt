package com.sesang06.todaywhateat.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

interface PickViewModelType {

    val pickedRestaurant: Observable<String>

    fun load(): Disposable

    fun pick()

}

class PickViewModel(val pref: SharedPreferences): ViewModel(), PickViewModelType {


    val restaurants: Observable<List<String>>
        get() {
            return rawStringBehaviorSubject
                .map { it.split(",") }
                .map { it.map { item: String -> item.trim() } }

        }

    override val pickedRestaurant: Observable<String>
        get() {
            return pickedPublishSubject
                .withLatestFrom(restaurants, BiFunction { _: Unit, t2: List<String> -> t2 })
                .map {
                    val random = Random()
                    val position = random.nextInt(it.size)
                    return@map it[position]
                }
        }


    private val pickedPublishSubject: PublishSubject<Unit> = PublishSubject.create()

    val rawStringBehaviorSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")


    override fun load(): Disposable {
        val disposeables = CompositeDisposable()
        val rawString = pref.getString("restaurant", "")
        if (rawString != null) {
            rawStringBehaviorSubject.onNext(rawString)
        }


        return disposeables
    }

    override fun pick() {
        pickedPublishSubject.onNext(Unit)
    }

}

