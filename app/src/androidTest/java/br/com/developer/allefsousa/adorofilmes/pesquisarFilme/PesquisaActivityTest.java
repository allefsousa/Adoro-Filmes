package br.com.developer.allefsousa.adorofilmes.pesquisarFilme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.developer.allefsousa.adorofilmes.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PesquisaActivityTest {

    // mocando o contexto para utilizar a string do resources



    @Rule
    public ActivityTestRule<PesquisaActivity> activityRule = new ActivityTestRule<>(PesquisaActivity.class);


    @Test
    public void Pesquisafilme() throws InterruptedException {
        onView(withId(R.id.et_search)).perform(typeText("Batman"));
        onView(withText("Batman")).check(matches(isDisplayed()));
        onView(withId(R.id.et_search))
                .perform(pressImeActionButton());
        Thread.sleep(5000);
        onView(withId(R.id.my_recycler_lancamentos))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

    }
    @Test
    public void verificaVisibilidadeElementos(){
        onView(withId(R.id.my_recycler_lancamentos)).check(matches(isEnabled()));
        onView(withId(R.id.my_recycler_view)).check(matches(isEnabled()));
        onView(withId(R.id.tPesquisa)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tlancamentos)).check(matches(isDisplayed()));
        onView(withId(R.id.et_search)).check(matches(isDisplayed()));
    }

    @Test
    public void clickButtonSearch(){
        PesquisaActivity activity = activityRule.getActivity();
        onView(withId(R.id.et_search)).perform(typeText(""));
        onView(withId(R.id.et_search))
                .perform(pressImeActionButton());
        // matcher que verifica o texto do campo
        onView(withId(R.id.et_search)).check(matches(withErrorEditext("This field is required")));


        // matcher que compara com uma mensagem de toast
        onView(withText("Login successfully."))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * matcher responsavel por pegar a mensagem de erro exibida pelo editText
     * @param expected
     * @return
     */
    private static Matcher withErrorEditext(final String expected) {
        return new TypeSafeMatcher() {
            @Override
            protected boolean matchesSafely(Object item) {
                if (item instanceof EditText) {
                    return ((EditText)item).getError().toString().equals(expected);
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