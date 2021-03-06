package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.developer.allefsousa.adorofilmes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by allef on 24/08/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PesquisaActivityTest2 {


    // mocando o contexto para utilizar a string do resources


    @Rule
    public ActivityTestRule<PesquisaActivity> activityRule = new ActivityTestRule<>(PesquisaActivity.class);

    /**
     * matcher responsavel por pegar a mensagem de erro exibida pelo editText
     *
     * @param
     * @return
     */


    @Test
    public void Pesquisafilme() throws InterruptedException {
        onView(withId(R.id.et_search)).perform(typeText("Batman"));
        onView(withText("Batman")).check(matches(isDisplayed()));
        onView(withId(R.id.et_search))
                .perform(pressImeActionButton());
        Thread.sleep(5000);
        onView(withId(R.id.my_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

    }

    @Test
    public void verificaVisibilidadeElementos() {
        onView(withId(R.id.my_recycler_lancamentos)).check(matches(isEnabled()));
        onView(withId(R.id.my_recycler_view)).check(matches(isEnabled()));
        onView(withId(R.id.tPesquisa)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tlancamentos)).check(matches(isDisplayed()));
        onView(withId(R.id.et_search)).check(matches(isDisplayed()));
    }

    @Test
    public void clickButtonSearch() {
        onView(withId(R.id.et_search)).perform(typeText(""));
        onView(withId(R.id.et_search))
                .perform(pressImeActionButton());
        // matcher que verifica o texto do campo
//        onView(withId(R.id.et_search)).check(matches(withErrorEditext("This field is required")));


        // matcher que compara com uma mensagem de toast
//        onView(withText("Login successfully."))
//                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
//                .check(matches(isDisplayed()));
    }

    public static Matcher withErrorEditext(final String expected) {
        return new TypeSafeMatcher() {
            @Override
            protected boolean matchesSafely(Object item) {
                if (item instanceof EditText) {
                    return ((EditText) item).getError().toString().equals(expected);
                }
                return false;

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message [" + expected + "]");
            }
        };
    }
}