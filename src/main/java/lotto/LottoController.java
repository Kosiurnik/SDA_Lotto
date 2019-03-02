package lotto;

import java.time.LocalDate;
import java.util.*;

public class LottoController {

    private List<LottoPlayer> players;
    private Scanner scanner;

    public LottoController() {
        players = new ArrayList<>();
    }

    public void run(){
        LocalDate today = LocalDate.now();
        scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Lotto ("+today+")");
        System.out.println("---------------------");
        System.out.println("Wpisz graczy na listę, aby rozpocząć grę");
        do {
            System.out.println("Wybierz opcję");
            System.out.println("1: Dopisz gracza do listy (aktualnie "+players.size()+" graczy)");
            System.out.println("2: Wylistuj graczy");
            System.out.println("3: Zmień tryb gry");
            System.out.println("4: Rozpocznij grę");
            System.out.println("5: Pokaż dzisiejsze wytypowane liczby");
            System.out.println("0: Wyjdź z aplikacji");
            try{
                switch (scanner.nextInt()) {
                    case 1:
                        addNewPlayer();
                        break;
                    case 2:
                        printPlayerList();
                        break;
                    case 3:
                        changeLottoTypeForPlayers();
                        break;
                    case 4:
                        beginGame();
                        break;
                    case 5:
                        showCorrectNumbers();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Podano nieprawidłową opcję.");
                        System.out.println();
                }
            }catch (InputMismatchException e){scanner.next();}
        } while (!exit);

        scanner.close();
    }

    private void beginGame() {
        if(players.size()!=0){
            for(LottoPlayer player : players){
                if(player.getType()==null){
                    player.setLottoType(setPlayerLottoType());
                }
                player.setResults(chooseNumbers(player));
            }
            System.out.println();
            for(LottoPlayer player : players){
                if(player.getResults()!=null){
                    player.checkResults();
                }
            }
        }else{
            System.out.println("Nie można rozpocząć gry bez podania graczy!");
            System.out.println();
        }
    }

    private void changeLottoTypeForPlayers(){
        if(players.size()!=0){
            System.out.println("Dla którego gracza zmienić tryb gry?");
            int i = 1;
            for(LottoPlayer player : players){
                System.out.println("\t"+(i++)+": "+player.getName() +" ("+getLottoTypeName(player.getType())+")");
            }
            int wybor = scanner.nextInt();
            if(wybor<=0&&wybor>i){
                System.out.println("Nieznany gracz");
            }else{
                players.get(wybor-1).setLottoType(setPlayerLottoType());
            }

        }else{
            System.out.println("Należy najpierw podać graczy");
        }
    }

    private int[] chooseNumbers(LottoPlayer player){
        if(player.getType()!=null){
            System.out.println("Gracz "+player.getName()+": gra w "+getLottoTypeName(player.getType()));
            System.out.println("Skreśl "+player.getType().getValue()+" liczb z przedzialu od 1 do "+player.getType().getMax());
            int i = 0;
            int[] chooseNumbers = new int[player.getType().getValue()];
            while(i<player.getType().getValue()){
                System.out.print(Arrays.toString(chooseNumbers)+": ");
                try{
                    int num = scanner.nextInt();
                    if(num<=0&&num>player.getType().getMax()){
                        System.out.println("Niepoprawna liczba");
                    }
                    else if(alreadyHasNumber(chooseNumbers,num)){
                        System.out.println("Liczby nie mogą się powtarzać");
                    }else{
                        chooseNumbers[i] = num;
                        i++;
                    }
                }catch(InputMismatchException e){
                    System.out.println("Wprowadź poprawną liczbę");
                    scanner.next();
                }
            }
            return chooseNumbers;
        }else{
            System.out.println("Gracz "+player.getName()+" nie określił typu gry");
            return null;
        }

    }

    private void addNewPlayer(){
        System.out.println("Podaj nazwę gracza:");
        scanner.nextLine();
        String name = scanner.nextLine();
        if(!name.equals("")){
            LottoPlayer player = new LottoPlayer(name);
            player.setLottoType(setPlayerLottoType());
            players.add(player);
            System.out.println("Dodano gracza "+name);
        }else{
            System.out.println("Imię nie może być puste");
        }
    }

    private LottoType setPlayerLottoType(){
        System.out.println("Wybierz typ gry: ");
        System.out.println("\t1: "+getLottoTypeName(LottoType.LOTTO));
        System.out.println("\t2: "+getLottoTypeName(LottoType.LOTTO_PLUS));
        System.out.println("\t3: "+getLottoTypeName(LottoType.MULTI_MULTI));
        System.out.println("\t0: Anuluj");
        try{
            switch(scanner.nextInt()){
                case 1: return LottoType.LOTTO;
                case 2: return LottoType.LOTTO_PLUS;
                case 3: return LottoType.MULTI_MULTI;
                default: return null;
            }
        }catch (InputMismatchException e){
            System.out.println("Niewłaściwa opcja");
        }
        return null;
    }

    private void printPlayerList(){
        int index = 1;
        for(LottoPlayer player : players){
            System.out.println((index++)+": Nazwa: "+player.getName()+"\t"+"Rodzaj gry: "+getLottoTypeName(player.getType()));
        }
        System.out.println();
    }

    private String getLottoTypeName(LottoType type){
        if(type==null)
            return "Nie wybrano";
        switch (type){
            case LOTTO: return "Lotto";
            case LOTTO_PLUS: return "Lotto Plus";
            case MULTI_MULTI: return "Multi Multi";
            default: return "Nie wybrano";
        }
    }

    private void showCorrectNumbers(){
        System.out.println("Dzisiejsze losowanie:");
        System.out.println("\t"+getLottoTypeName(LottoType.LOTTO)+": "+Arrays.toString(Lotto.getInstance().getResults(LottoType.LOTTO)));
        System.out.println("\t"+getLottoTypeName(LottoType.LOTTO_PLUS)+": "+Arrays.toString(Lotto.getInstance().getResults(LottoType.LOTTO_PLUS)));
        System.out.println("\t"+getLottoTypeName(LottoType.MULTI_MULTI)+": "+Arrays.toString(Lotto.getInstance().getResults(LottoType.MULTI_MULTI)));
    }

    private boolean alreadyHasNumber(int[] arr, int num){
        for(int i : arr){
            if(i==num)
                return true;
        }
        return false;
    }

}
