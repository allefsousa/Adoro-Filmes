package br.com.developer.allefsousa.adorofilmes.data.source.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allef on 06/08/2018.
 */

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";


    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(new StethoInterceptor());


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

}
