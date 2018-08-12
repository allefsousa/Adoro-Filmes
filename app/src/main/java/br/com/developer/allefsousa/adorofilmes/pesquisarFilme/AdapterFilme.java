package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.data.Result;

/**
 * Created by allef on 11/08/2018.
 */

public class AdapterFilme extends RecyclerView.Adapter<AdapterFilme.ViewHolder> {
    private Context context;
    private List<Result> filmeList = new ArrayList<>();
    private RecyclerItemClickListener recyclerItemClickListener;
    private final String imageBaseUrl ="https://image.tmdb.org/t/p/w500/";
    SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyyy");


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
        if (filmeList.get(position).getName() == null){
            holder.textNomeFilme.setText(filmeList.get(position).getOriginalName());
        }else {
            holder.textNomeFilme.setText(filmeList.get(position).getName());
        }

        holder.textDataFilme.setText(filmeList.get(position).getFirstAirDate());


        if (filmeList.get(position).getPosterPath()== null) {
            holder.logoFilme.setImageResource(R.drawable.placeholder);
        } else {
            Glide.with(context).load(imageBaseUrl+filmeList.get(position).getPosterPath()).into(holder.logoFilme);
        }


        holder.view.setOnClickListener(v ->
                recyclerItemClickListener.onItemClick(filmeList.get(position)));


    }

    @Override
    public int getItemCount() {
        return filmeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNomeFilme;
        TextView textDataFilme;
        ImageView logoFilme;
        View view;

        public ViewHolder(View v) {
            super(v);
            textNomeFilme = v.findViewById(R.id.TnomeFilme);
            textDataFilme = v.findViewById(R.id.TanoLancamento);
            logoFilme = v.findViewById(R.id.Ifilme);
            view = v.findViewById(R.id.lyt_parent);
        }


    }
}
