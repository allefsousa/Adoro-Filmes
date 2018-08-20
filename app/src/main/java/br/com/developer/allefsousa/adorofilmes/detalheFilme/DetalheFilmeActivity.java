package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheFilmeActivity extends AppCompatActivity implements DetalheFilmeContract.view{
    private DetalheFilmeContract.presenter mPresenter;
    private final String imageBaseUrl ="https://image.tmdb.org/t/p/w500/";

    @BindView(R.id.toolbarDetalhe)
    Toolbar toolbar;
    @BindView(R.id.tNome)
    TextView tNomeDetalhe;
    @BindView(R.id.iLogo)
    ImageView iLogoFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        ButterKnife.bind(this);
        mPresenter = new DetalheFilmePresenter(this,new DetalheFilmeImp());
        initToolbar();
        String idfilme = recuperaIdFilme();
        recuperaDetalhe(idfilme);
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

    @Override
    public void AtualizarUiNull() {

    }

    @Override
    public void atualizaUi(FilmeDetalhes filmeDetalhes) {
        tNomeDetalhe.setText(filmeDetalhes.getTitle());
        Glide.with(this).load(imageBaseUrl+filmeDetalhes.getPosterPath()).into(iLogoFilme);


    }

    @Override
    public void atualizaUiTv(TvDetalhes tvDetalhes) {

    }

    @Override
    public void erroAoBuscarDetalhes() {

    }
}
