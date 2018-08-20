package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.text.TextUtils;

import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;

/**
 * Created by allef on 15/08/2018.
 */

public class DetalheFilmePresenter implements DetalheFilmeContract.presenter,DetalheFilmeContract.detalherService.OnFinishedListenerFilme,
        DetalheFilmeContract.detalherService.OnFinishedListenerTv{
    private DetalheFilmeContract.view mView;
    private DetalheFilmeContract.detalherService detalherService;

    public DetalheFilmePresenter(DetalheFilmeContract.view mView, DetalheFilmeContract.detalherService detalherService) {
        this.mView = mView;
        this.detalherService = detalherService;
    }

    @Override
    public void recuperaDetalhes(String idFilme) {
        if (TextUtils.isEmpty(idFilme)){
            mView.idFilmeNulla();
        }else {
            detalherService.getFilmeArrayList(this,idFilme);
        }
    }


    @Override
    public void onFinishedRequestFilme(FilmeDetalhes filmeDetalhes) {

    }

    @Override
    public void onFailureRequestFilme(Throwable t) {

    }

    @Override
    public void onFinishedRequestTv(TvDetalhes tvDetalhes) {

    }

    @Override
    public void onFailureRequestTv(Throwable t) {

    }
}
