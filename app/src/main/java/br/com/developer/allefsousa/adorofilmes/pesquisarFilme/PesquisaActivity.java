package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity;
import br.com.developer.allefsousa.adorofilmes.utils.SpacingItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.developer.allefsousa.adorofilmes.AppApplication.mFirebaseAnalytics;
import static br.com.developer.allefsousa.adorofilmes.utils.DpUtils.dpToPx;

public class PesquisaActivity extends AppCompatActivity implements PesquisaFilmeContract.view, InstallStateUpdatedListener {


    @BindView(R.id.my_recycler_lancamentos)
    RecyclerView recyclerViewlancamentos;
    @BindView(R.id.my_recycler_lancamentospage2)
    RecyclerView recyclerViewPage2;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewfilme;
    @BindView(R.id.et_search)
    EditText editFilme;

    @BindView(R.id.viewpager)
    ViewPager viewPagerMovie;

    Image image;
    @BindView(R.id.tPesquisa)
    TextView texBusca;
    private static final int MY_REQUEST_CODE = 17300;


    private PesquisaFilmeContract.presenter mPresenter;
    private AdapterFilme adapterFilme;
    private AdapterFilmeLancamentos adapterFilme2;
    private RecyclerItemClickListener recyclerItemClickListener;
    private InterstitialAd mInterstitialAd;



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

            }

            return false;
        });


        buscaTopFilmes();
        recyclerItemClickListener = filme -> {
            Intent inten = new Intent(PesquisaActivity.this, DetalheFilmeActivity.class);
            inten.putExtra("filme",filme);
            startActivity(inten);
        };


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-2296995403494910/7451751552");
//        checkUpdate(this);
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                                AppUpdateType.FLEXIBLE,
                                PesquisaActivity.this,
                                0);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("Support in-app-update", "UPDATE_NOT_AVAILABLE");
                }
            }
        });


    }
    public void checkUpdate(Context context) {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                                AppUpdateType.FLEXIBLE,
                                PesquisaActivity.this,
                                0);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("Support in-app-update", "UPDATE_NOT_AVAILABLE");
                }
            }
        });
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
        editFilme.setError("This field is required");
        Toast.makeText(this, "Login successfully.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void PesquisaFilmeSemretorno() {

        Toast.makeText(this, "Não Existem resultados para sua Pesquisa !", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void RecyclerViewSetValue(List<Result> resultFilme) {

        adapterFilme = new AdapterFilme(PesquisaActivity.this, resultFilme, recyclerItemClickListener);
        recyclerViewfilme.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewfilme.setNestedScrollingEnabled(false);
        recyclerViewfilme.addItemDecoration(new SpacingItemDecoration(2, dpToPx(this, 4), true));
        recyclerViewfilme.setAdapter(adapterFilme);
        recyclerViewfilme.requestFocus();


    }
    public void visibilidadeTexto(){
        texBusca.setVisibility(View.VISIBLE);
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
        adapterFilme2 = new AdapterFilmeLancamentos(PesquisaActivity.this, results, recyclerItemClickListener);
        recyclerViewlancamentos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewlancamentos.setAdapter(adapterFilme2);
    }
    @Override
    public void updateUiTopFilmesPage2(List<Result> results) {
        adapterFilme2 = new AdapterFilmeLancamentos(PesquisaActivity.this, results, recyclerItemClickListener);
        recyclerViewPage2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerViewPage2.setAdapter(adapterFilme2);
    }

    @Override
    public void onStateUpdate(InstallState installState) {

    }
}
