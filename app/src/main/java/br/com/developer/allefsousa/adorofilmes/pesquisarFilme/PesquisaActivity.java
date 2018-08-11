package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.developer.allefsousa.adorofilmes.R;

public class PesquisaActivity extends AppCompatActivity implements PesquisaFilmeContract.view {

    private final String apiKey = "745509647d8c3e2ac4c7b0d5ef2d7352";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
    }
}
