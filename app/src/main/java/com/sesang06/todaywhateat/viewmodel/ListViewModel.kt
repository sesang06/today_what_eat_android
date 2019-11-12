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
    val rawStringBehaviorSubject: BehaviorSubject<String>
    fun load(): Disposable
}

class ListViewModel(val pref: SharedPreferences): ViewModel(), ListViewModelType {


    override val restaurants: Observable<List<String>>
        get() {
            return rawStringBehaviorSubject
                .map { it.split(",") }
                .map { it.map { item: String -> item.trim() } }

        }



    override val rawStringBehaviorSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")


    override fun load(): Disposable {
        val disposeables = CompositeDisposable()
        val rawString = pref.getString("restaurant", "")
        if (rawString != null) {
            rawStringBehaviorSubject.onNext(rawString)
        }


        return disposeables
    }


}
