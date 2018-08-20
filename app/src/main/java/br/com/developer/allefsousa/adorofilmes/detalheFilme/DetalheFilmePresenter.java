package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.text.TextUtils;

import br.com.developer.allefsousa.adorofilmes.data.Request;

/**
 * Created by allef on 15/08/2018.
 */

public class DetalheFilmePresenter implements DetalheFilmeContract.presenter,DetalheFilmeContract.detalherService.OnFinishedListener{
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
    public void onFinishedRequest(Request request) {
        if (request != null){
            mView.exibeDados();
        }else {
            mView.requestSemResultado();
        }

    }

    @Override
    public void onFailureRequest(Throwable t) {
        mView.falhaRequest(t);

    }
}
