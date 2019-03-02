package lotto;

import java.util.Arrays;

class LottoPlayer {

    private String name;
    private LottoType type;
    private int[] results;

    LottoPlayer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    LottoType getType() {
        return type;
    }

    void setResults(int[] results){
        if(type==null){
            System.out.println("Gracz "+name+" nie określił typu gry");
        }else{
            this.results = results;
        }
    }

    int[] getResults() {
        return results;
    }

    void setLottoType(LottoType type){
        this.type = type;
    }

    void checkResults(){
        if(type!=null){
            if(results==null){
                System.out.println(name+" jeszcze nie wytypował liczb");
            }else{
                int count = 0;
                for(int numChosen : results){
                    for(int numWinning : Lotto.getInstance().getResults(type)){
                        if(numChosen==numWinning){
                            count++;
                        }
                    }
                }
                System.out.println(name+" trafił "+count+" z "+type.getValue()+" liczb");
            }
        }else{
            System.out.println("Gracz "+name+" nie określił typu gry");
        }
    }
}
