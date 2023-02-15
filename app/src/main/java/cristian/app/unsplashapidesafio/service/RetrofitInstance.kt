package cristian.app.unsplashapidesafio.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    /*
    * Unsplash API:
    *
    * Acess key -> ""
    * Secret key -> ""
    *
    * A maioria das ações pode ser executada sem exigir autenticação de um usuário específico.
    * Pesquisar, buscar ou baixar uma foto não exige que o usuário faça login.
    * Para autenticar solicitações dessa forma, passe a chave de acesso do seu aplicativo por meio do cabeçalho HTTP Authorization:
    *
    * Authorization: Client-ID YOUR_ACCESS_KEY
    *
    * */

    companion object{

        private const val BASE_URL = "https://api.unsplash.com/"
        const val ACESS_KEY = "qffgfKaVNecSfv8KwFLLfT5iD6mlnO5nx7Yz__BvzB4"

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor())
            .build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        val service: UnsplashAPIService = retrofit.create(UnsplashAPIService::class.java)
    }
}