package br.com.developer.allefsousa.adorofilmes.detalheFilme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import br.com.developer.allefsousa.adorofilmes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheFilmeActivity extends AppCompatActivity {

    @BindView(R.id.toolbarDetalhe)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
