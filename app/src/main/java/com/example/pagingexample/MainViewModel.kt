package com.example.pagingexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingexample.data.GithubResponseItem
import com.example.pagingexample.network.GitApi
import com.example.pagingexample.network.RetrofitInstance
import com.example.pagingexample.paging.MyPagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private val api = RetrofitInstance.getInstance().create(GitApi::class.java)

    val items : Flow<PagingData<GithubResponseItem>> = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            MyPagingSource(api)
        }
    )
        .flow
        .cachedIn(viewModelScope)
}