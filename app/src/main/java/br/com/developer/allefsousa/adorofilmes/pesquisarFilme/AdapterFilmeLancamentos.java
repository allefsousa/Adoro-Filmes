package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity;

/**
 * Created by allef on 11/08/2018.
 */

public class AdapterFilmeLancamentos extends RecyclerView.Adapter<AdapterFilmeLancamentos.ViewHolder> {
    private Context context;
    private List<Result> filmeList = new ArrayList<>();
    private RecyclerItemClickListener recyclerItemClickListener;
    private final String imageBaseUrl ="https://image.tmdb.org/t/p/w500/";
    SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyyy");


    public AdapterFilmeLancamentos(Context context, List<Result> filmeList, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.filmeList = filmeList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme_lancamento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Result filme = filmeList.get(position);
        holder.updateUi(filme);

        holder.view.setOnClickListener((View v) ->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                View vv = v.findViewById(R.id.Ifilme);

                ActivityOptionsCompat optionsCompat =  ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, Pair.create(vv,"allef"));

                Intent inten = new Intent(context, DetalheFilmeActivity.class);
                inten.putExtra("filme",filme);
                context.startActivity(inten,optionsCompat.toBundle());


            }else {
                recyclerItemClickListener.onItemClick(filmeList.get(position));
            }



        });


    }



    @Override
    public int getItemCount() {
        return filmeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textNomeFilme;
//        TextView textDataFilme;
        ImageView logoFilme;
        View view;

        public ViewHolder(View v) {
            super(v);
//            textNomeFilme = v.findViewById(R.id.TnomeFilme);
//            textDataFilme = v.findViewById(R.id.TanoLancamento);
            logoFilme = v.findViewById(R.id.Ifilme);
            view = v.findViewById(R.id.lyt_parent);
        }

        private void updateUi(Result filme) {
//            textNomeFilme.setText(filme.getName());
//            textDataFilme.setText(filme.getFirstAirDate());
            if (filme.getPosterPath()== null) {
                logoFilme.setImageDrawable (context.getResources().getDrawable(R.drawable.placehol));
            } else {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.placehol)
                        .priority(Priority.HIGH);

                Glide.with(context)
                        .load(imageBaseUrl+filme.getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(options)
                        .into(logoFilme);
            }
        }


    }
}
