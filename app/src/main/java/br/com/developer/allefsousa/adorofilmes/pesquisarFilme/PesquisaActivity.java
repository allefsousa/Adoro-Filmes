package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Request;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity;
import br.com.developer.allefsousa.adorofilmes.utils.SpacingItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.developer.allefsousa.adorofilmes.utils.DpUtils.dpToPx;

public class PesquisaActivity extends AppCompatActivity implements PesquisaFilmeContract.view,EditText.OnEditorActionListener {

    private final String apiKey = "745509647d8c3e2ac4c7b0d5ef2d7352";
    private PesquisaFilmeContract.presenter mPresenter;
    private AdapterFilme adapterFilme;
    private AdapterFilmeLancamentos adapterFilme2;
    private EditText.OnEditorActionListener actionListener;

    @BindView(R.id.my_recycler_lancamentos)
    RecyclerView recyclerViewlancamentos;

    @BindView(R.id.et_search)
    EditText editFilme;


    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewfilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        ButterKnife.bind(this);
        mPresenter = new PesquisarFilmePresenter(this,new getFilmeServiceImpl());

        editFilme.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(PesquisaActivity.this);
                    String tituloFilme;
                    tituloFilme = editFilme.getText().toString();
                    mPresenter.PesquisaFilme(tituloFilme);

                }

                return false;
            }
        });

        buscaTopFilmes();

    }

    private void buscaTopFilmes() {
        mPresenter.BuscaLancamentos();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


        return false;

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

    }


    @Override
    public void PesquisaFilmeSemretorno() {

    }

    @Override
    public void RecyclerViewSetValue(List<Result> resultFilme) {
        adapterFilme = new AdapterFilme(PesquisaActivity.this, resultFilme, recyclerItemClickListener);
        recyclerViewfilme.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewfilme.setNestedScrollingEnabled(false);
        recyclerViewfilme.addItemDecoration(new SpacingItemDecoration(2, dpToPx(this, 4), true));
        recyclerViewfilme.setAdapter(adapterFilme);

    }

    @Override
    public void ColapsinExpanded(boolean b) {

    }

    @Override
    public void Limpar() {
        editFilme.getText().clear();

    }

    @Override
    public void ErroResquest(Throwable t) {
        Toast.makeText(this, "Erro "+t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e("Allef", "ErroResquest: "+t.getMessage());
    }

    @Override
    public void updateUiTopFilmes(List<Result> results) {
        adapterFilme2 = new AdapterFilmeLancamentos(PesquisaActivity.this, results, recyclerItemClickListener);
        recyclerViewlancamentos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewlancamentos.setAdapter(adapterFilme2);
    }



    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Result filme) {
//            Intent intent = new Intent(PesquisaActivity.this, DetalheFilmeActivity.class);
//            intent.putExtra("idFilme",filme.getId());
//            startActivity(intent);
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

        }
    };
}
