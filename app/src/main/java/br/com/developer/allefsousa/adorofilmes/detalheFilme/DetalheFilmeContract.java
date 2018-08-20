package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.PesquisaFilmeContract;

/**
 * Created by allef on 15/08/2018.
 */

public interface DetalheFilmeContract {
    interface view{
        void recuperaDetalhe(String idFilme);

        void idFilmeNulla();

        void requestSemResultado();

        void exibeDados();

        void falhaRequest(Throwable t);
    }
    interface  presenter{

        void recuperaDetalhes(String idFilme);
    }

    interface detalherService{

        interface OnFinishedListener {
            void onFinishedRequest(Request request);
            void onFailureRequest(Throwable t);
        }

        void getFilmeArrayList(DetalheFilmePresenter onFinishedListener, String idFilme);


    }

}
