package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;


import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.data.source.remote.FilmeInterface;
import br.com.developer.allefsousa.adorofilmes.data.source.remote.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by allef on 06/08/2018.
 */

public class getFilmeServiceImpl implements PesquisaFilmeContract.getFilmeService {
    private final String apiKey = "745509647d8c3e2ac4c7b0d5ef2d7352";
    private final String tipoFilme = "pt-br";

    @Override
    public void getFilmeLancamento(OnFinishedListener onFinishedListener) {
        FilmeInterface filmeInterface = RetrofitInstance.getRetrofitInstance().create(FilmeInterface.class);
        Call<Request> call = filmeInterface.RequestPopular(apiKey,tipoFilme);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                onFinishedListener.onFinishedTop(response.body());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                onFinishedListener.onFailureTop(t);
            }
        });
    }

    @Override
    public void getFilmeArrayList(OnFinishedListener onFinishedListener,String nomeFilme) {

        FilmeInterface filmeInterface = RetrofitInstance.getRetrofitInstance().create(FilmeInterface.class);


        Call<Request> call = filmeInterface.RequestAll(apiKey,nomeFilme,tipoFilme);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                onFinishedListener.onFailure(t);

            }
        });


    }


}
