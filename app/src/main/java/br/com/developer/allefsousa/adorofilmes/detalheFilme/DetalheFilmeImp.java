package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Trailer;
import br.com.developer.allefsousa.adorofilmes.data.TrailerDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.source.remote.FilmeInterface;
import br.com.developer.allefsousa.adorofilmes.data.source.remote.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by allef on 19/08/2018.
 */

public class DetalheFilmeImp implements DetalheFilmeContract.detalherService {
    private final String apiKey = "745509647d8c3e2ac4c7b0d5ef2d7352";
    private final String idioma = "pt-br";

    FilmeInterface filmeInterface = RetrofitInstance.getRetrofitInstance().create(FilmeInterface.class);

    @Override
    public void getFilmeArrayList(DetalheFilmePresenter onFinishedListener, String idFilme, String mediaType) {


        if (mediaType == null){
            Call<FilmeDetalhes> call = filmeInterface.detalhesFilme(idFilme,apiKey, idioma);
            call.enqueue(new Callback<FilmeDetalhes>() {
                @Override
                public void onResponse(Call<FilmeDetalhes> call, Response<FilmeDetalhes> response) {
                    onFinishedListener.onFinishedRequestFilme(response.body());
                }

                @Override
                public void onFailure(Call<FilmeDetalhes> call, Throwable t) {
                    onFinishedListener.onFailureRequestFilme(t);

                }
            });
        } else if (mediaType.equals("tv")){
            Call<TvDetalhes> call = filmeInterface.detalhesTv(idFilme,apiKey, idioma);
            call.enqueue(new Callback<TvDetalhes>() {
                @Override
                public void onResponse(Call<TvDetalhes> call, Response<TvDetalhes> response) {
                    onFinishedListener.onFinishedRequestTv(response.body());
                }

                @Override
                public void onFailure(Call<TvDetalhes> call, Throwable t) {
                    onFinishedListener.onFailureRequestTv(t);

                }
            });
        }else {
            Call<FilmeDetalhes> call = filmeInterface.detalhesFilme(idFilme,apiKey, idioma);
            call.enqueue(new Callback<FilmeDetalhes>() {
                @Override
                public void onResponse(Call<FilmeDetalhes> call, Response<FilmeDetalhes> response) {
                    onFinishedListener.onFinishedRequestFilme(response.body());
                }

                @Override
                public void onFailure(Call<FilmeDetalhes> call, Throwable t) {
                    onFinishedListener.onFailureRequestFilme(t);

                }
            });
        }

    }

    @Override
    public void getFilmeTrailer(DetalheFilmePresenter onFinishedListener, String idFilme) {
        Call<Trailer> call = filmeInterface.trailerTV(idFilme,apiKey);
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                onFinishedListener.onFinishedRequestTrailer(response.body());
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {

            }
        });

    }

    @Override
    public void getTvArrayList(DetalheFilmePresenter onFinishedListener, String idSerie) {

        Call<TvDetalhes> call = filmeInterface.detalhesTv(idSerie,apiKey, idioma);
       call.enqueue(new Callback<TvDetalhes>() {
           @Override
           public void onResponse(Call<TvDetalhes> call, Response<TvDetalhes> response) {
               onFinishedListener.onFinishedRequestTv(response.body());
           }

           @Override
           public void onFailure(Call<TvDetalhes> call, Throwable t) {
                onFinishedListener.onFailureRequestTv(t);
           }
       });
    }

}
