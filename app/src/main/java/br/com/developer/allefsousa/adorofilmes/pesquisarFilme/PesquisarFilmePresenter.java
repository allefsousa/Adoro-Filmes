package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.text.TextUtils;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;

/**
 * Created by allef on 11/08/2018.
 */

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

    @Override
    public void onFinished(Request request) {
        if (request.getResults() == null) {
            myView.PesquisaFilmeSemretorno();
        } else {
            myView.RecyclerViewSetValue(request.getResults());
            myView.ColapsinExpanded(false);
            myView.Limpar();
        }
    }



    @Override
    public void onFailure(Throwable t) {

        myView.ErroResquest(t);


    }
}
