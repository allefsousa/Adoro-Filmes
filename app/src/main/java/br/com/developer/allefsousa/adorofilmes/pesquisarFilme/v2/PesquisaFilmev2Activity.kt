package br.com.developer.allefsousa.adorofilmes.pesquisarFilme.v2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import br.com.developer.allefsousa.adorofilmes.AppApplication
import br.com.developer.allefsousa.adorofilmes.R
import br.com.developer.allefsousa.adorofilmes.data.Result
import br.com.developer.allefsousa.adorofilmes.detalheFilme.DetalheFilmeActivity
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.FilmeServiceImpl
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.PesquisaFilmeContract
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.PesquisarFilmePresenter
import com.amplitude.api.Amplitude
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.home_movie.*

class PesquisaFilmev2Activity : AppCompatActivity(), CardStackListener, PesquisaFilmeContract.view {

    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private lateinit var mPresenter: PesquisaFilmeContract.presenter
    private var spots: MutableList<Result> = mutableListOf()
    private var itemPosition: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val inflater = TransitionInflater.from(this)
            val transition = inflater.inflateTransition(R.transition.traasitions)
            window.sharedElementEnterTransition = transition
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        mPresenter = PesquisarFilmePresenter(this, FilmeServiceImpl())
        mPresenter.BuscaLancamentos()
        manager = CardStackLayoutManager(this, this@PesquisaFilmev2Activity)
        adapter = CardStackAdapter(spots)
        setupCardStackView()
        setupButton()
        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        MobileAds.initialize(this, "ca-app-pub-2296995403494910~8764833228")
        Amplitude.getInstance().logEvent("Pesquisa Filme Activity")
        adapter.setListener(object : CardStackAdapter.OnClickListener {
            override fun onButtonClick(position: Result, position1: Int) {
                val inten = Intent(this@PesquisaFilmev2Activity, DetalheFilmeActivity::class.java)
                inten.putExtra("filme", position)
                startActivity(inten)
            }
        })

        et_search.setOnEditorActionListener { textView: TextView?, i: Int, keyEvent: KeyEvent? ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(this)
                val tituloFilme: String = et_search.text.toString()
                mPresenter.PesquisaFilme(tituloFilme)
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tituloFilme)
                bundle.putString("EVENTO", "PESQUISOU FILME")
                AppApplication.mFirebaseAnalytics.logEvent("pesquisou_filme", bundle)
            }
            false
        }
    }

    private fun hideKeyboard(activity: PesquisaFilmev2Activity) {

        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = activity.currentFocus!!
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        itemPosition = position
        val textView = view.findViewById<TextView>(R.id.naaammeeee)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.naaammeeee)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }


    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val rewind = findViewById<View>(R.id.rewind_button)
        rewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(DecelerateInterpolator())
                    .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        val like = findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val result = spots[itemPosition]
            val inten = Intent(this@PesquisaFilmev2Activity, DetalheFilmeActivity::class.java)
            inten.putExtra("filme", result)
            startActivity(inten)
//            val setting = SwipeAnimationSetting.Builder()
//                    .setDirection(Direction.Right)
//                    .setDuration(Duration.Normal.duration)
//                    .setInterpolator(AccelerateInterpolator())
//                    .build()
//            manager.setSwipeAnimationSetting(setting)
//            cardStackView.swipe()


        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(createSpots())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }


    private fun createSpots(): List<Result> {
        return spots
    }

    override fun pesquisaFilmeSemretorno() {
        Toast.makeText(this, "Não Existem resultados para sua Pesquisa !", Toast.LENGTH_SHORT).show()
    }

    override fun updateUiTopFilmes(results: MutableList<Result>) {
        spots.addAll(results)
        Log.d("CardStackView", "onCardAppeared: ${spots.size}")
        adapter.notifyDataSetChanged()
    }

    override fun visibilidadeTexto() {
    }

    override fun updateUiTopFilmesPage2(results: MutableList<Result>) {
        spots.addAll(results)
        Log.d("CardStackView", "onCardAppeared: ${spots.size}")

        adapter.notifyDataSetChanged()

    }

    override fun limpar() {
        et_search.text.clear()

    }

    override fun nomeFilmeemBranco() {
        et_search.error = "Informe o nome do filme."

    }

    override fun erroResquest(t: Throwable?) {
    }

    override fun recyclerViewSetValue(resultFilme: MutableList<Result>) {
        val findMovies: MutableList<Result> = mutableListOf()
        findMovies.addAll(resultFilme)
        findMovies.addAll(spots)
        spots.clear()
        spots.addAll(findMovies)
        adapter.notifyDataSetChanged()


    }

}
