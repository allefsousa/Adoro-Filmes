package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.internal.y;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.FilmeDetalhes;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.data.Trailer;
import br.com.developer.allefsousa.adorofilmes.data.TrailerDetalhes;
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
    YouTubePlayerView youTubePlayerView;




    private DetalheFilmeContract.presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.traasitions);

          getWindow().setSharedElementEnterTransition(transition);

        }
        super.onCreate(savedInstanceState);
//         youTubePlayerView = new YouTubePlayerView(this);
//        youTubePlayerView.initialize(API, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//
//                youTubePlayer.cueVideo(VIDEO_ID);
//
////                Log.d("Allef","Allef"+trailer.getResults().get(0).getKey());
////                Toast.makeText(DetalheFilmeActivity.this, ""+trailer.getResults().get(0).getKey(), Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(DetalheFilmeActivity.this, "Falha ao reproduzir video "+youTubeInitializationResult.getErrorDialog(DetalheFilmeActivity.this,0), Toast.LENGTH_SHORT).show();
//            }
//        });

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
//        initToolbar();
        filmeDetalhes = (Result) getIntent().getSerializableExtra("filme");
        recuperaDetalhe(filmeDetalhes.getId(), filmeDetalhes.getMediaType());
        recuperaTrailer(filmeDetalhes.getId());


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    toolbar.setTitle(" ");


//                    toolbar.getSupportActionBar().setTitle("Detalhes");


                }
                else
                {
//                    getSupportActionBar().setTitle(" ");
                    toolbar.setTitle(" ");
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


    private void initToolbar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
//                    youTubePlayer.loadVideo(trailer.getResults().get(0).getKey());
////                    youTubePlayer.pause();
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
