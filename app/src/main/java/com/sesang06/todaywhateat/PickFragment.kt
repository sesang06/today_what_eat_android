package com.sesang06.todaywhateat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sesang06.todaywhateat.adapter.RestaurantAdapter
import com.sesang06.todaywhateat.viewmodel.ListViewModel
import com.sesang06.todaywhateat.viewmodel.ListViewModelFactory
import com.sesang06.todaywhateat.viewmodel.PickViewModel
import com.sesang06.todaywhateat.viewmodel.PickViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_pick.*
import kotlinx.android.synthetic.main.fragment_pick.view.*

class PickFragment : Fragment() {

    val viewModelFactory by lazy {
        PickViewModelFactory(
            context!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        )
    }

    lateinit var viewModel: PickViewModel


    val disposeables = AutoClearedDisposable(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pick, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[PickViewModel::class.java]


        disposeables.add(
            viewModel.pickedRestaurant
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ picked ->
                    view.text_view_pick.text = picked
                }, {}
                )
        )

        view.button_pick.setOnClickListener {
            viewModel.pick()
        }

        viewModel.load()

        viewModel.pick()

        return view
    }


    override fun onResume() {
        super.onResume()
        viewModel.load()
    }
}