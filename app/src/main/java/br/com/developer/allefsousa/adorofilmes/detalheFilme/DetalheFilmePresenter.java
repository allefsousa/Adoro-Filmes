package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.text.TextUtils;

import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Trailer;
import br.com.developer.allefsousa.adorofilmes.data.TrailerDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;

/**
 * Created by allef on 15/08/2018.
 */

public class DetalheFilmePresenter implements DetalheFilmeContract.presenter,DetalheFilmeContract.detalherService.OnFinishedListenerFilme,
        DetalheFilmeContract.detalherService.OnFinishedListenerTv,DetalheFilmeContract.detalherService.OnFinishedListenerTrailer{

    private DetalheFilmeContract.view mView;
    private DetalheFilmeContract.detalherService detalherService;


    public DetalheFilmePresenter(DetalheFilmeContract.view mView, DetalheFilmeContract.detalherService detalherService) {
        this.mView = mView;
        this.detalherService = detalherService;
    }

    @Override
    public void recuperaDetalhes(String idFilme, String mediaType) {
        if (TextUtils.isEmpty(idFilme)){
            mView.idFilmeNulla();
        }else {
            detalherService.getFilmeArrayList(this,idFilme,mediaType);
        }
    }

    @Override
    public void recuperarTrailer(String filmeid) {
        if (TextUtils.isEmpty(filmeid)){
            mView.idFilmeNulla();
        }else {
            detalherService.getFilmeTrailer(this,filmeid);
        }
    }


    @Override
    public void onFinishedRequestFilme(FilmeDetalhes filmeDetalhes) {

        if (filmeDetalhes != null){
            mView.atualizaUi(filmeDetalhes);
        }else {
            mView.AtualizarUiNull();
        }

    }


    @Override
    public void onFailureRequestFilme(Throwable t) {

        mView.erroAoBuscarDetalhes();

    }

    @Override
    public void onFinishedRequestTv(TvDetalhes tvDetalhes) {
        if (tvDetalhes != null){
            mView.atualizaUiTv(tvDetalhes);
        }else {
            mView.AtualizarUiNull();
        }
    }

    @Override
    public void onFailureRequestTv(Throwable t) {

    }


    @Override
    public void onFinishedRequestTrailer(Trailer trailer) {
        mView.atualizaTrailers(trailer);

    }


    @Override
    public void onFailureRequestTrailer(Throwable t) {

    }
}
