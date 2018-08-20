package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity;
import br.com.developer.allefsousa.adorofilmes.utils.SpacingItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.developer.allefsousa.adorofilmes.utils.DpUtils.dpToPx;

public class PesquisaActivity extends AppCompatActivity implements PesquisaFilmeContract.view {


    @BindView(R.id.my_recycler_lancamentos)
    RecyclerView recyclerViewlancamentos;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewfilme;
    @BindView(R.id.et_search)
    EditText editFilme;

    Image image;
    @BindView(R.id.tPesquisa)
    TextView texBusca;

    private PesquisaFilmeContract.presenter mPresenter;
    private AdapterFilme adapterFilme;
    private AdapterFilmeLancamentos adapterFilme2;
    private RecyclerItemClickListener recyclerItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        ButterKnife.bind(this);
        mPresenter = new PesquisarFilmePresenter(this, new FilmeServiceImpl());
        editFilme.requestFocus();
        editFilme.setFocusable(true);

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
            inten.putExtra("id",filme.getId());
            startActivity(inten);
        };



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
        Log.e("Allef", "ErroResquest: " + t.getMessage());
    }

    @Override
    public void updateUiTopFilmes(List<Result> results) {
        adapterFilme2 = new AdapterFilmeLancamentos(PesquisaActivity.this, results, recyclerItemClickListener);
        recyclerViewlancamentos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewlancamentos.setAdapter(adapterFilme2);
    }
}
