package br.com.developer.allefsousa.adorofilmes;

import org.junit.Test;

import br.com.developer.allefsousa.adorofilmes.data.Result;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    Result result = mock(Result.class);



    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


}