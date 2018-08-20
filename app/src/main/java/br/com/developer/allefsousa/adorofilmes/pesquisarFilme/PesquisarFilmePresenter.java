package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.text.TextUtils;
import android.util.Log;

import br.com.developer.allefsousa.adorofilmes.data.Request;


public class PesquisarFilmePresenter implements PesquisaFilmeContract.presenter, PesquisaFilmeContract.filmeService.OnFinishedListener {

    private PesquisaFilmeContract.view myView;
    private PesquisaFilmeContract.filmeService filmeService;

    public PesquisarFilmePresenter(PesquisaFilmeContract.view myView, PesquisaFilmeContract.filmeService filmeService) {
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
