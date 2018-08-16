package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;



public class PesquisarFilmePresenter implements PesquisaFilmeContract.presenter, PesquisaFilmeContract.getFilmeService.OnFinishedListener {

    private PesquisaFilmeContract.view myView;
    private PesquisaFilmeContract.getFilmeService filmeService;

    public PesquisarFilmePresenter(PesquisaFilmeContract.view myView, PesquisaFilmeContract.getFilmeService filmeService) {
        this.myView = myView;
        this.filmeService = filmeService;
    }

    @Override
    public void PesquisaFilme(String tituloFilme) {
        String nomefilme = tituloFilme.trim();
        if (TextUtils.isEmpty(nomefilme)) {
            myView.nomeFilmeemBranco();
        } else {
            filmeService.getFilmeArrayList(this, nomefilme);
        }
    }
    public void BuscaLancamentos(){
        filmeService.getFilmeLancamento(this);

    }

    @Override
    public void onFinished(Request request) {
        if (request.getResults() == null) {
            myView.PesquisaFilmeSemretorno();
        } else {
            myView.visibilidadeTexto();
            myView.RecyclerViewSetValue(request.getResults());
            myView.Limpar();
        }
    }



    @Override
    public void onFailure(Throwable t) {

        myView.ErroResquest(t);


    }

    @Override
    public void onFinishedTop(Request request) {
        if (request.getResults() != null) {
            myView.updateUiTopFilmes(request.getResults());
            Log.d("Allef", "onFinishedTop: "+request.getResults().size());
        }

    }

    @Override
    public void onFailureTop(Throwable t) {

    }
}
