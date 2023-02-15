package cristian.app.unsplashapidesafio.service

import cristian.app.unsplashapidesafio.model.FotoResponse
import cristian.app.unsplashapidesafio.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPIService {

    @GET("photos")
    suspend fun getListPhotos(
    ): Response<FotoResponse>

    @GET("search/photos")
    suspend fun getSearchPhoto(
        @Query("query") pesquisa: String
    ): Response<SearchResponse>

}