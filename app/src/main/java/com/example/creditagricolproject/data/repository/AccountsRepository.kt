package com.example.creditagricolproject.data.repository
import com.example.creditagricolproject.data.datasource.AccountsApi
import com.example.creditagricolproject.domaine.model.AccountsResponse
import javax.inject.Inject
import com.example.creditagricolproject.util.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class AccountsRepository @Inject constructor(
    private val api: AccountsApi
) {
    fun getAccounts(): Flow<Result<List<AccountsResponse>>> = flow {
        emit(Result.Loading())
        val response = api.getAccounts()
        if (response.isSuccessful) {
            val accounts = response.body()
            if (accounts != null) {
                emit(Result.Success(accounts))
            } else {
                emit(Result.Success(emptyList()))
            }
        } else {
            emit(Result.Error("Failed to fetch accounts"))
        }
    }.flowOn(Dispatchers.IO)
}

