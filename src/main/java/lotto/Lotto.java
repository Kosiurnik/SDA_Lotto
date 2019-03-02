package lotto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Lotto {

    private static Lotto ourInstance = new Lotto();
    private Map<LocalDate,LottoResult> lottoResults;

    public static Lotto getInstance() {
        return ourInstance;
    }

    private Lotto() {
        lottoResults = new HashMap<>();
    }

    public int[] getResults(LottoType type){
        LocalDate today = LocalDate.now();
        if(!this.lottoResults.containsKey(today)){
            LottoResult results = new LottoResult();
            results.putResults(type,randomNoRepeat(type.getValue(),type.getMax()));
            lottoResults.put(today,results);
        }else{
            LottoResult results = this.lottoResults.get(today);
            if(results.getResults(type)==null){
                results.putResults(type,randomNoRepeat(type.getValue(),type.getMax()));
            }
        }
        return lottoResults.get(today).getResults(type);
    }


    private int[] randomNoRepeat(int length,int max){
        int min = 1;
        if (length > max - min)
            throw new IllegalArgumentException("Przedział "+min+" - "+max+" jest zbyt mały dla "+length+" unikalnych liczb.");
        int[] numbers = new int[length];

        Random randNum = new Random();
        for (int i = 0; i < length; i++){
            int number = randNum.nextInt(max-min)+min;
            while (contains(numbers,number)){
                number = randNum.nextInt(max-min)+min;
            }
            numbers[i]=number;
        }
        return  numbers;
    }

    private static boolean contains(final int[] array, final int v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }
}
