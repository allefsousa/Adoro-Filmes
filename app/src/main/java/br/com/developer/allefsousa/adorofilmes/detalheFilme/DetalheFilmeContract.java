package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;

/**
 * Created by allef on 15/08/2018.
 */

public interface DetalheFilmeContract {
    interface view {
        void recuperaDetalhe(String idFilme);

        void idFilmeNulla();

        void requestSemResultado();

        void exibeDados();

        void falhaRequest(Throwable t);

        void AtualizarUiNull();

        void atualizaUi(FilmeDetalhes filmeDetalhes);

        void atualizaUiTv(TvDetalhes tvDetalhes);

        void erroAoBuscarDetalhes();
    }

    interface presenter {

        void recuperaDetalhes(String idFilme);
    }

    interface detalherService {

        void getFilmeArrayList(DetalheFilmePresenter onFinishedListener, String idFilme);

        void getTvArrayList(DetalheFilmePresenter onFinishedListener, String idSerie);


        interface OnFinishedListenerFilme {
            void onFinishedRequestFilme(FilmeDetalhes filmeDetalhes);

            void onFailureRequestFilme(Throwable t);
        }

        interface OnFinishedListenerTv {
            void onFinishedRequestTv(TvDetalhes tvDetalhes);

            void onFailureRequestTv(Throwable t);
        }


    }

}
