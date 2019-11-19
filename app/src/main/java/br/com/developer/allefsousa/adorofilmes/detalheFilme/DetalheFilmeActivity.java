package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.amplitude.api.Amplitude;
import com.google.android.material.appbar.AppBarLayout;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.data.Trailer;
import br.com.developer.allefsousa.adorofilmes.data.TvDetalhes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheFilmeActivity extends YouTubeBaseActivity implements DetalheFilmeContract.view {
    private static final String API = "AIzaSyA0TRZ_qJ993vT58oiCobu9WSleAz1KzrM";
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
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    YouTubePlayerView youTubePlayerView;
    private String nameMovie = "";




    private DetalheFilmeContract.presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.traasitions);

          getWindow().setSharedElementEnterTransition(transition);

        }
        super.onCreate(savedInstanceState);

        Amplitude.getInstance().logEvent("Detalhe Filme Activity");

        setContentView(R.layout.activity_detalhe_filme);
        ButterKnife.bind(this);
        youTubePlayerView =  findViewById(R.id.youtube);

        MobileAds.initialize(this, "ca-app-pub-2296995403494910~8764833228");
        AdView mAdView = findViewById(R.id.adView1);
        AdView mAdView1 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);
        mPresenter = new DetalheFilmePresenter(this, new DetalheFilmeImp());
        filmeDetalhes = (Result) getIntent().getSerializableExtra("filme");
        recuperaDetalhe(filmeDetalhes.getId(), filmeDetalhes.getMediaType());
        recuperaTrailer(filmeDetalhes.getId());


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(nameMovie);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }

        });










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

    @Override
    public void recuperaTrailer(String Filmeid) {
        mPresenter.recuperarTrailer(Filmeid);
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
        String dataSemFormato = filme.getReleaseDate();
        String dataFormatada = "";

        if (dataSemFormato != null) {


            try {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formato.parse(dataSemFormato);
                formato.applyPattern("dd/MM/yyyy");
                dataFormatada = formato.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        nameMovie = filme.getTitle();
        tNomeDetalhe.setText(filme.getTitle());
        sinopse.setText(filme.getOverview());
        tlancamento.setText(dataFormatada);

        Glide.with(this).load(imageBaseUrl + filme.getPosterPath()).into(iLogoFilme);


    }

    @Override
    public void atualizaUiTv(TvDetalhes tvDetalhes) {
        tNomeDetalhe.setText(tvDetalhes.getName());
        sinopse.setText(tvDetalhes.getOverview());
        tlancamento.setText(tvDetalhes.getFirstAirDate());

//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.placehol)
//                .priority(Priority.HIGH);
//        Glide.with(this).load(imageBaseUrl + tvDetalhes.getPosterPath()).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(iLogoFilme);


    }

    @Override
    public void erroAoBuscarDetalhes() {

    }

    @Override
    public void atualizaTrailers(Trailer trailer) {
        if (!trailer.getResults().isEmpty()){
            youTubePlayerView.initialize(API, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.cueVideo(trailer.getResults().get(0).getKey());

                    // Hiding player controls
//                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });

        }else {
            youTubePlayerView.setVisibility(View.GONE);
        }

    }

}
