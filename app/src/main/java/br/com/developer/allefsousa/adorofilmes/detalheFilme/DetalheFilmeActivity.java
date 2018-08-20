package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import br.com.developer.allefsousa.adorofilmes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheFilmeActivity extends AppCompatActivity implements DetalheFilmeContract.view{
    private DetalheFilmeContract.presenter mPresenter;

    @BindView(R.id.toolbarDetalhe)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        ButterKnife.bind(this);
        initToolbar();
        String idfilme = recuperaIdFilme();
        recuperaDetalhe(idfilme);
        Toast.makeText(this, "id "+ idfilme, Toast.LENGTH_SHORT).show();
    }

    private String recuperaIdFilme() {
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        return (String) dados.get("id");
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void recuperaDetalhe(String idFilme) {
        mPresenter.recuperaDetalhes(idFilme);

    }

    @Override
    public void idFilmeNulla() {

    }

    @Override
    public void requestSemResultado() {

    }

    @Override
    public void exibeDados() {

    }

    @Override
    public void falhaRequest(Throwable t) {

    }
}
