package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheFilmeActivity extends AppCompatActivity implements DetalheFilmeContract.view {
    private final String imageBaseUrl = "https://image.tmdb.org/t/p/w500/";

    @BindView(R.id.toolbarDetalhe)
    Toolbar toolbar;
    @BindView(R.id.tNome)
    TextView tNomeDetalhe;
    @BindView(R.id.iLogo)
    ImageView iLogoFilme;
    Result filmeDetalhes;

    @BindView(R.id.tsinopse)
    TextView sinopse;
    @BindView(R.id.tdataLancamento)
    TextView tlancamento;

    private DetalheFilmeContract.presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        ButterKnife.bind(this);
        mPresenter = new DetalheFilmePresenter(this, new DetalheFilmeImp());
        initToolbar();
        filmeDetalhes = (Result) getIntent().getSerializableExtra("filme");
        recuperaDetalhe(filmeDetalhes.getId(), filmeDetalhes.getMediaType());
    }

    /**
     * Meotdo responsavel por passar a id do filme ou serie a ser buscado e se oseu media type
     * é um movie ou tv para que seja exibido o detalhe corretamente
     *
     * @param id
     * @param mediaType
     */
    @Override
    public void recuperaDetalhe(String id, String mediaType) {
        mPresenter.recuperaDetalhes(id, mediaType);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes");


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
    public void atualizaUi(FilmeDetalhes filme) {
        tNomeDetalhe.setText(filme.getTitle());
        sinopse.setText(filme.getOverview());
        tlancamento.setText(filme.getReleaseDate());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placehol)
                .priority(Priority.HIGH);
        Glide.with(this).load(imageBaseUrl + filme.getPosterPath()).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(iLogoFilme);


    }

    @Override
    public void atualizaUiTv(TvDetalhes tvDetalhes) {


    }

    @Override
    public void erroAoBuscarDetalhes() {

    }
}
