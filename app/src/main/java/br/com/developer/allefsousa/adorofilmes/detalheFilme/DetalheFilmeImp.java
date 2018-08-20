package br.com.developer.allefsousa.adorofilmes.detalheFilme;

/**
 * Created by allef on 19/08/2018.
 */

public class DetalheFilmeImp implements DetalheFilmeContract.detalherService {
    private final String apiKey = "745509647d8c3e2ac4c7b0d5ef2d7352";
    private final String tipoFilme = "pt-br";

    @Override
    public void getFilmeArrayList(DetalheFilmePresenter onFinishedListener, String idFilme) {

    }
}
