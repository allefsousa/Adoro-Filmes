package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.amplitude.api.Amplitude;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity;
import br.com.developer.allefsousa.adorofilmes.utils.ViewPagerTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.developer.allefsousa.adorofilmes.AppApplication.mFirebaseAnalytics;

public class PesquisaActivity extends AppCompatActivity implements PesquisaFilmeContract.view, InstallStateUpdatedListener {


    @BindView(R.id.et_search)
    EditText editFilme;

    @BindView(R.id.viewpager)
    ViewPager viewPagerMovie;

    @BindView(R.id.tabLayout)
    TabLayout tableLayout;

    Image image;
    @BindView(R.id.tPesquisa)
    TextView texBusca;
    @BindView(R.id.imageHome)
    ImageView imageHome;
    @BindView(R.id.tlancamentos)
    TextView texLancamento;


    private PesquisaFilmeContract.presenter mPresenter;
    private InterstitialAd mInterstitialAd;
    private MoviesPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.traasitions);
            getWindow().setSharedElementEnterTransition(transition);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        ButterKnife.bind(this);
        MobileAds.initialize(this, "ca-app-pub-2296995403494910~8764833228");
        Amplitude.getInstance().logEvent("Pesquisa Filme Activity");
        viewPagerMovie.setClipToPadding(false);
        viewPagerMovie.setPageTransformer(true,new ViewPagerTransformation());

         adapter = new MoviesPagerAdapter();

        mFirebaseAnalytics.setCurrentScreen(this, "Pesquisa Filme Activity", null /* class override */);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2296995403494910/6111278327");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mPresenter = new PesquisarFilmePresenter(this, new FilmeServiceImpl());
        editFilme.requestFocus();
        editFilme.setFocusable(true);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        editFilme.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(PesquisaActivity.this);
                String tituloFilme;
                tituloFilme = editFilme.getText().toString();
                mPresenter.PesquisaFilme(tituloFilme);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tituloFilme);
                bundle.putString("EVENTO", "PESQUISOU FILME");
                mFirebaseAnalytics.logEvent("pesquisou_filme", bundle);

            }

            return false;
        });

        imageHome.setOnClickListener(v -> {
            adapter.listClear();
            mPresenter.BuscaLancamentos();
            imageHome.setVisibility(View.GONE);
            texBusca.setVisibility(View.GONE);
            texLancamento.setVisibility(View.VISIBLE);
        });


        buscaTopFilmes();
        tableLayout.setupWithViewPager(viewPagerMovie, true);


    }

    private void buscaTopFilmes() {
        mPresenter.BuscaLancamentos();
    }

    private void hideKeyboard(PesquisaActivity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    @Override
    public void nomeFilmeemBranco() {
        editFilme.setError("Informe o nome do filme.");
    }


    @Override
    public void PesquisaFilmeSemretorno() {
        Toast.makeText(this, "Não Existem resultados para sua Pesquisa !", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void RecyclerViewSetValue(List<Result> resultFilme) {
        adapter.listClear();
        adapter.addItem(resultFilme);
        viewPagerMovie.setAdapter(adapter);
    }
    public void visibilidadeTexto(){
        texBusca.setVisibility(View.VISIBLE);
        texLancamento.setVisibility(View.GONE);
        imageHome.setVisibility(View.VISIBLE);

    }



    @Override
    public void Limpar() {
        editFilme.getText().clear();
    }

    @Override
    public void ErroResquest(Throwable t) {
        Toast.makeText(this, "Erro " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateUiTopFilmes(List<Result> results) {
        adapter.addItem(results);
        viewPagerMovie.setAdapter(adapter);
    }
    @Override
    public void updateUiTopFilmesPage2(List<Result> results) {
        adapter.addItem(results);
        viewPagerMovie.setAdapter(adapter);
    }

    @Override
    public void onStateUpdate(InstallState installState) {

    }
}
