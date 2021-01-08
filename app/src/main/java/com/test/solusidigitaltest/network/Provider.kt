package com.test.solusidigitaltest.network

object Provider {
    fun newsProviderRepository():Repository{
        return  Repository(ApiService.create())
    }
}