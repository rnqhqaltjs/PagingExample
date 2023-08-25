package com.example.pagingexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingexample.paging.MyAdapter
import kotlinx.coroutines.launch

// MainActivity -> ViewModel -> PagingSource -> Repository(네트워크 통신)
// https://api.github.com/users/google/repos?page=1&per_page=90

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val rv = findViewById<RecyclerView>(R.id.rv)
        val myAdapter = MyAdapter()

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = myAdapter

        lifecycleScope.launch {
            viewModel.items.collect {
                myAdapter.submitData(it)
            }
        }

//        val page = 10
//        val range = page.until(page+20)
//
//        Log.d("range", range.toString())
//
//        range.map {
//            Log.d("this", it.toString())
//        }


    }
}