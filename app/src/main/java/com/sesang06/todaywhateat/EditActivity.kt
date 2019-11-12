package com.sesang06.todaywhateat

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sesang06.todaywhateat.viewmodel.EditViewModel
import com.sesang06.todaywhateat.viewmodel.EditViewModelFactory
import com.sesang06.todaywhateat.viewmodel.ListViewModel
import com.sesang06.todaywhateat.viewmodel.ListViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*

class EditActivity : AppCompatActivity() {


    val viewModelFactory by lazy {
        EditViewModelFactory(
            getSharedPreferences("pref", Context.MODE_PRIVATE)
        )
    }


    lateinit var viewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[EditViewModel::class.java]
        viewModel.rawStringBehaviorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                edit_text_restaurant.setText(text)
            }, {})

        viewModel.load()

        button_restaurant.setOnClickListener {
            viewModel.save(edit_text_restaurant.text.toString())
            this.finish()
        }
    }

}