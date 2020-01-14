package br.com.developer.allefsousa.adorofilmes.pesquisarFilme.v2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.developer.allefsousa.adorofilmes.R
import br.com.developer.allefsousa.adorofilmes.data.Result
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity
import com.bumptech.glide.Glide

class CardStackAdapter(
        private var spots: List<Result> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
    private lateinit var context:Context
    private var listener: OnClickListener? = null
    private var listenerPosition: PositionListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_filme_v, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        Glide.with(holder.image)
                .load(imageBaseUrl + spot.posterPath)
                .into(holder.image)

        holder.image.setOnClickListener { v ->

            listener?.onButtonClick(spot,position)
        }

    }

    private fun click(spot: Result, position: Int) {
        listener?.onButtonClick(spot, position)
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<Result>) {
        this.spots = spots
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getSpots(): List<Result> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.naaammeeee)
        var image: ImageView = view.findViewById(R.id.imageeee)
    }

    // Click Listner Buttom
    fun setListener(listener: OnClickListener) {
        this.listener = listener
    }

    fun positionListener(listener: PositionListener) {
        this.listenerPosition = listener
    }

    interface OnClickListener {
        fun onButtonClick(position: Result, position1: Int)
    }
    interface PositionListener {
        fun itemPositionClick( position1: Result)
    }

}
