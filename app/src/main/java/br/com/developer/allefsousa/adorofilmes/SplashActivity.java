package br.com.developer.allefsousa.adorofilmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.developer.allefsousa.adorofilmes.pesquisarFilme.PesquisaActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        fadeInAnimation = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.slide_in_left);
        fadeInAnimation2 = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.fade_in);
        textView.setAnimation(fadeInAnimation);
        imageView.setAnimation(fadeInAnimation2);
        startAnimation();


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
                    Intent intent = new Intent(SplashActivity.this,PesquisaActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

                } catch (Exception e) {

                }


            }
        };
        splashTread.start();
    }
}
