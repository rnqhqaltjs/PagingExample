package com.example.pagingexample.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingexample.data.GithubResponseItem
import com.example.pagingexample.network.GitApi
import kotlinx.coroutines.delay

private const val STARTING_KEY = 1

class MyPagingSource(
    private val githubService : GitApi
) : PagingSource<Int, GithubResponseItem>() {

    init {
        Log.d("MyPagingSource", "init")
    }

    // Paging 이 실행되면 어떻게 할 것인지 정하는 부분
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubResponseItem> {

        Log.d("MyPagingSource", "--------------------")
        Log.d("MyPagingSource", "-----------START---------")
        Log.d("MyPagingSource", "--------------------")

        Log.d("MyPagingSource", "load")
        Log.d("params.loadSize", "params.key :" + params.key)

        val page = params.key ?: STARTING_KEY

        Log.d("params.loadSize", "page : $page")

        val response = githubService.getData(page, params.loadSize)

        Log.d("params.loadSize", "page : $response")
        Log.d("params.loadSize", response.body().toString())

        val data = response.body()

        Log.d("data", data.toString())

        // 처음 로드되는게 아니면 3초 딜레이를 주세요
        if(page != 1) {
            delay(3000)
        }

        Log.d("params.loadSize", params.loadSize.toString())
        Log.d("params.loadSize", (params.loadSize / 30).toString())

        if(data != null) {

            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = page + (params.loadSize / 30)
            )

        } else {

            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null
            )

        }


    }

    // 새로고침을 누르면 어떻게 할 것인지
    override fun getRefreshKey(state: PagingState<Int, GithubResponseItem>): Int? {
            return null
        }

}