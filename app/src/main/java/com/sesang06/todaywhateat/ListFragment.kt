package com.sesang06.todaywhateat

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sesang06.todaywhateat.viewmodel.ListViewModel
import com.sesang06.todaywhateat.viewmodel.ListViewModelFactory
import androidx.lifecycle.ViewModelProviders
import com.sesang06.todaywhateat.adapter.RestaurantAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    val viewModelFactory by lazy {
        ListViewModelFactory(
          context!!.getSharedPreferences("pref", MODE_PRIVATE)
        )
    }

    val layoutManger by lazy {
        LinearLayoutManager(this.context)
    }

    lateinit var viewModel: ListViewModel

    val adapter by lazy {
        RestaurantAdapter()
    }
    val disposeables = AutoClearedDisposable(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ListViewModel::class.java]

        view.recycler_view_list.layoutManager = layoutManger
        view.recycler_view_list.adapter = adapter


        disposeables.add(
            viewModel.restaurants
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items ->
                    with(adapter) {
                        if (items.isEmpty()) {
                            clearItems()
                        } else {
                            setItems(items)
                        }
                        notifyDataSetChanged()

                    }
                }
        )

        disposeables.add(
            viewModel.showEmptyView
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isEmpty ->
                    view.empty_view.visibility =
                        when(isEmpty) {
                            true -> VISIBLE
                            false -> GONE
                        }
                }
        )

        view.edit_button.setOnClickListener {
            val intent = Intent(this.context, EditActivity::class.java)
            this.startActivity(intent)
        }



        viewModel.load()
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }
}