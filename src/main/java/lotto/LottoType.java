package lotto;

public enum LottoType {
    LOTTO(6,49),
    LOTTO_PLUS(8,49),
    MULTI_MULTI(20,60);

    private final int maxChosen;
    private final int maxRandomized;

    LottoType(int i,int max) {
        maxChosen = i;
        maxRandomized = max;
    }
    public int getValue(){
        return  maxChosen;
    }

    public int getMax() {
        return maxRandomized;
    }
}
