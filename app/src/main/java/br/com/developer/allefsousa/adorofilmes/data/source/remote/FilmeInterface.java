package br.com.developer.allefsousa.adorofilmes.data.source.remote;

import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by allef on 11/08/2018.
 */

public interface FilmeInterface {

    @GET("search/multi?")
    Call<Request> RequestAll(@Query("api_key") String valueKey, @Query("query") String nomeFilme, @Query("language") String idioma);

    @GET("movie/popular?")
    Call<Request> RequestPopular(@Query("api_key")String valueKey,@Query("language") String idioma);

}
