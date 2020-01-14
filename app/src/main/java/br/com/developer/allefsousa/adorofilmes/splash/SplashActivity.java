package br.com.developer.allefsousa.adorofilmes.splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplitude.api.Amplitude;
import com.google.android.gms.ads.MobileAds;

import br.com.developer.allefsousa.adorofilmes.R;
import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.v2.PesquisaFilmev2Activity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.developer.allefsousa.adorofilmes.AppApplication.mFirebaseAnalytics;

public class SplashActivity extends AppCompatActivity {
    Thread splashTread;
    Animation fadeInAnimation;
    Animation fadeInAnimation2;


    @BindView(R.id.tTitulo)
    TextView textView;

    @BindView(R.id.iImage)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        MobileAds.initialize(this, "ca-app-pub-2296995403494910~8764833228");
        Amplitude.getInstance().logEvent("Splash Activity open");
        mFirebaseAnalytics.setCurrentScreen(this, "Splash Activity open", null /* class override */);

    }



    public void startAnimation() {


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;

                    //

                    while (waited < 2000) {

                        sleep(100);
                        waited += 100;


                    }
                    Intent intent = new Intent(SplashActivity.this, PesquisaFilmev2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

                } catch (Exception e) {

                }


            }
        };
        splashTread.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fadeInAnimation = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.slide_in_left);
        fadeInAnimation2 = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.fade_in);
        textView.setAnimation(fadeInAnimation);
        imageView.setAnimation(fadeInAnimation2);
        startAnimation();


    }
}
