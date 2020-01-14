package br.com.developer.allefsousa.adorofilmes.old;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Result;
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.RecyclerItemClickListener;

/**
 * Created by allef on 11/08/2018.
 */

public class AdapterFilme extends RecyclerView.Adapter<AdapterFilme.ViewHolder> {
    private final String imageBaseUrl = "https://image.tmdb.org/t/p/w500/";
        private Context context;
    private List<Result> filmeList = new ArrayList<>();
    private RecyclerItemClickListener recyclerItemClickListener;


    public AdapterFilme(Context context, List<Result> filmeList, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.filmeList = filmeList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Result filme = filmeList.get(position);
        holder.updateUi(filme);


        holder.view.setOnClickListener(v ->
                recyclerItemClickListener.onItemClick(filmeList.get(position)));


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

//            if (filme.getName() == null) {
//                textNomeFilme.setText(filme.getOriginalName());
//            } else {
//                textNomeFilme.setText(filme.getName());
//            }
            String dataSemFormato = filme.getFirstAirDate();
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

//            textDataFilme.setText(dataFormatada);
            if (filme.getPosterPath() == null) {
                logoFilme.setImageDrawable(context.getResources().getDrawable(R.drawable.placehol));
            } else {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.placehol)
                        .priority(Priority.HIGH);

                Glide.with(context)
                        .load(imageBaseUrl + filme.getPosterPath())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(options)
                        .into(logoFilme);
            }
        }


    }
}
