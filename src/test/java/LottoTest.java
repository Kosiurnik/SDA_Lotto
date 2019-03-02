import lotto.Lotto;
import lotto.LottoType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LottoTest {

    @AfterAll
    static void resetSingletons(){
        resetSingleton();
    }

    static void resetSingleton() {
        Field instance;
        try {
            instance = Lotto.class.getDeclaredField("ourInstance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    void testSingleton(){
        assertThat(Lotto.getInstance()).isNotEqualTo(null);
        assertSame(Lotto.getInstance(),Lotto.getInstance());
        assertSame(Lotto.getInstance(), Lotto.getInstance());
    }

    @Test
    void checkIfNoRepeatedTest(){
        assertTrue(hasDistinctValues(Lotto.getInstance().getResults(LottoType.LOTTO)));
        assertTrue(hasDistinctValues(Lotto.getInstance().getResults(LottoType.LOTTO_PLUS)));
        assertTrue(hasDistinctValues(Lotto.getInstance().getResults(LottoType.MULTI_MULTI)));
    }

    @Test
    void checkIfEqualOnSameDayTest(){
        assertArrayEquals(Lotto.getInstance().getResults(LottoType.LOTTO), Lotto.getInstance().getResults(LottoType.LOTTO));
        assertArrayEquals(Lotto.getInstance().getResults(LottoType.LOTTO_PLUS), Lotto.getInstance().getResults(LottoType.LOTTO_PLUS));
        assertArrayEquals(Lotto.getInstance().getResults(LottoType.MULTI_MULTI), Lotto.getInstance().getResults(LottoType.MULTI_MULTI));
    }

    boolean hasDistinctValues(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}