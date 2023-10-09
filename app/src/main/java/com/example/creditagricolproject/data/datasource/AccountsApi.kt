package com.example.creditagricolproject.data.datasource

import com.example.creditagricolproject.domaine.model.AccountsResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountsApi {
    @GET("/banks.json")
     suspend fun getAccounts() : Response<List<AccountsResponse>>

}