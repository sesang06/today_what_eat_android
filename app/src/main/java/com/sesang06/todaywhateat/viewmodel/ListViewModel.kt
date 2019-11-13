package com.sesang06.todaywhateat.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*


interface ListViewModelType {
    val restaurants: Observable<List<String>>
    val rawStringBehaviorSubject: PublishSubject<String>
    val showEmptyView: Observable<Boolean>
    fun load(): Disposable
}

class ListViewModel(val pref: SharedPreferences): ViewModel(), ListViewModelType {



    override val restaurants: Observable<List<String>>
        get() {
            return rawStringBehaviorSubject
                .map { it.split(",") }
                .map { it.map { item: String -> item.trim() }
                    .mapNotNull { item: String -> when (item.isEmpty()){
                        true -> null
                        false -> item
                    }
                    }
                }
                .share()
        }

    override val showEmptyView: Observable<Boolean>
        get() {
            return restaurants
                .map { it.isEmpty() }
        }

    override val rawStringBehaviorSubject: PublishSubject<String> = PublishSubject.create()

    override fun load(): Disposable {
        val disposeables = CompositeDisposable()
        val rawString = pref.getString("restaurant", "")
        if (rawString != null) {
            rawStringBehaviorSubject.onNext(rawString)
        }


        return disposeables
    }


}
