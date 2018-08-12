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

        void ColapsinExpanded(boolean b);

        void Limpar();

        void ErroResquest(Throwable t);
    }
    interface presenter{

        void PesquisaFilme(String tituloFilme);
    }
    interface getFilmeService {

        interface OnFinishedListener {
            void onFinished(Request request);
            void onFailure(Throwable t);
        }

        void getFilmeArrayList(OnFinishedListener onFinishedListener,String nomeFilme);
    }



}
