package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import br.com.developer.allefsousa.adorofilmes.R;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by allef on 14/08/2018.
 */
@RunWith(RobolectricTestRunner.class)
public class PesquisaActivityTestRobo {

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        Activity activity = Robolectric.setupActivity(PesquisaActivity.class);
        RecyclerView recyclerView = activity.findViewById(R.id.my_recycler_lancamentos);
        recyclerView.measure(0,0);
        recyclerView.layout(0,0,100,1000);

        // lets just imply a certain item at position 0 for simplicity
        recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();





//        Button button = (Button) activity.findViewById(R.id.press_me_button);
//        TextView results = (TextView) activity.findViewById(R.id.results_text_view);
//
//        button.performClick();
//        assertThat(results.getText().toString(), equalTo("Testing Android Rocks!"));
    }

}