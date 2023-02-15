package cristian.app.unsplashapidesafio.service

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {

    // Authorization: Client-ID YOUR_ACCESS_KEY
    override fun intercept(chain: Interceptor.Chain): Response {

        val requisicao = chain.request().newBuilder()
        val novaRequisicao = requisicao.addHeader(
            "Authorization",
            "Client-ID ${RetrofitInstance.ACESS_KEY}"
        )

        return chain.proceed(novaRequisicao.build())
    }
}