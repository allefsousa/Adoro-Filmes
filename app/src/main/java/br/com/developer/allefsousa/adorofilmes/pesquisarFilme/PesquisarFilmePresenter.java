package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.text.TextUtils;

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
            filmeService.getMovieArrayList(this, nomefilme);
        }
    }
    public void BuscaLancamentos(){
        filmeService.getMovieLancamentoOne(this);
        filmeService.getMovieLancamentoTwo(this);

    }

    @Override
    public void onFinished(Request request) {
        if (request.getResults() == null) {
            myView.pesquisaFilmeSemretorno();
        } else {
            myView.visibilidadeTexto();
            myView.recyclerViewSetValue(request.getResults());
            myView.limpar();
        }
    }



    @Override
    public void onFailure(Throwable t) {
        myView.erroResquest(t);
    }

    @Override
    public void onFinishedTop(Request request) {
        if (request.getResults() != null && request.getPage()<2) {
            myView.updateUiTopFilmes(request.getResults());

        }else {
            myView.updateUiTopFilmesPage2(request.getResults());
        }


    }

    @Override
    public void onFailureTop(Throwable t) {

    }
}
