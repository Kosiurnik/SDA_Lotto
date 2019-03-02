package lotto;

import java.util.HashMap;
import java.util.Map;

class LottoResult {
    private Map<LottoType,int[]> results;

    LottoResult() {
        this.results = new HashMap<>();
    }

    int[] getResults(LottoType type) {
        return this.results.getOrDefault(type, null);
    }

    void putResults(LottoType type, int[] results) {
        this.results.put(type,results);
    }
}
