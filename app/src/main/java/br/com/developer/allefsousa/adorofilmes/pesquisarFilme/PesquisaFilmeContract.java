package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;

/**
 * Created by allef on 11/08/2018.
 */

public interface PesquisaFilmeContract {

    interface view{

        void nomeFilmeemBranco();

        void PesquisaFilmeSemretorno();

        void RecyclerViewSetValue(List<Result> resultFilme);

        void Limpar();

        void ErroResquest(Throwable t);

        void updateUiTopFilmes(List<Result> results);
        void visibilidadeTexto();

        void updateUiTopFilmesPage2(List<Result> results);
    }
    interface presenter{

        void PesquisaFilme(String tituloFilme);
        void BuscaLancamentos();
    }

    interface filmeService {

        interface OnFinishedListener {
            void onFinished(Request request);
            void onFailure(Throwable t);

            void onFinishedTop(Request request);
            void onFailureTop(Throwable t);
        }

        void getFilmeArrayList(OnFinishedListener onFinishedListener, String nomeFilme);

        void getFilmeLancamento(OnFinishedListener onFinishedListener);
        void getFilmeLancamentoPage2(OnFinishedListener onFinishedListener);
        
    }



}
