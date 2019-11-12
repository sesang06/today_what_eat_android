package com.sesang06.todaywhateat.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject


interface EditViewModelType {
    val rawStringBehaviorSubject: BehaviorSubject<String>
    fun load(): Disposable
    fun save(text: String): Disposable
}

class EditViewModel(val pref: SharedPreferences): ViewModel(), EditViewModelType {


    override val rawStringBehaviorSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")



    override fun load(): Disposable {
        val disposeables = CompositeDisposable()
        val rawString = pref.getString("restaurant", "")
        if (rawString != null) {
            rawStringBehaviorSubject.onNext(rawString)
        }


        return disposeables
    }

    override fun save(text: String): Disposable {
        val disposeables = CompositeDisposable()
        pref.edit().putString("restaurant", text).apply()

        return disposeables
    }


}
