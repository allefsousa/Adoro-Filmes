package br.com.developer.allefsousa.adorofilmes.old

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.viewpager.widget.PagerAdapter
import br.com.developer.allefsousa.adorofilmes.R
import br.com.developer.allefsousa.adorofilmes.data.Result
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * @author allef.santos on 2019-11-30
 */
class MoviesPagerAdapter : PagerAdapter() {
    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"
    val list:MutableList<Result> = mutableListOf()
    private lateinit var context:Context



    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        context = container.context
        val result = list[position]
        val itemView: View = LayoutInflater.from(container.context).inflate(R.layout.item_filme_lancamento,container,false)

        val image = itemView.findViewById<ImageView>(R.id.Ifilme)
        val tv = itemView.findViewById<TextView>(R.id.TnomeFilme)
        tv.text = result.name
        if (result.posterPath == null) {
            image.setImageDrawable(context.resources.getDrawable(R.drawable.placehol))
        } else {
            val options = RequestOptions()
                    .placeholder(R.drawable.placehol)
                    .priority(Priority.HIGH)
            Glide.with(context)
                    .load(imageBaseUrl + result.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options)
                    .into(image)

        }

        image.setOnClickListener {
            val intent = Intent(context, DetalheFilmeActivity::class.java)
            intent.putExtra("filme", result)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val vv: View = itemView.findViewById(R.id.Ifilme)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((context as Activity), Pair.create(vv, "allef"))
                context.startActivity(intent, optionsCompat.toBundle())
            } else {
                context.startActivity(intent)
            }
        }

        container.addView(itemView)
        return  itemView



    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    fun addItem(list:List<Result> ){
        this.list.addAll(list)
        notifyDataSetChanged()
    } fun listClear( ){
       list.clear()
        notifyDataSetChanged()
    }



    override fun getCount(): Int  = list.size


}