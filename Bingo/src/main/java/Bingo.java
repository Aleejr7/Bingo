import java.util.*;
public class Bingo {
    public static int possibleNumbers = 99;
    public static int cardSize = 5;

    public static void main(String[] args) {
        Random random = new Random();
        int[] numbersDrawn = new int[possibleNumbers];
        int[] sweepstake = new int[cardSize];
        int menu;

        introMessage();
        String[] nicknames = generateNicknames();
        int[][] cards = new int[nicknames.length][cardSize];
        int[][] markedCards = new int[nicknames.length][cardSize];

        int j;
        int i = 0;
        while(i == 0){
            int gameMode = automaticOrManual();
            System.out.println();
            //MODO MANUAL
            if(gameMode == 1){
                i++;
                cards = generateManualCard(nicknames);
                printCards(nicknames, cards);
                for (int k = 0; k < 26; k++) {
                    System.out.printf("\n %d° Round - SORTEIO \n", k+1);
                    sweepstake = sweepstakeManual();
                    numbersDrawn = fillDrawnNumbers(sweepstake, numbersDrawn);
                    markedCards = markCard(sweepstake, cards, markedCards);

                    int indexWinner = checkWinner(markedCards);
                    if(indexWinner != -1){
                        printWinner(indexWinner, cards, nicknames, numbersDrawn, k, markedCards);
                    }

                    j = 0;
                    while(j == 0){
                        menu = menu();
                        switch (menu){
                            case 1:
                                j++;
                                break;
                            case 2:
                                //IMPRIMIR O RANKING
                                j++;
                                break;
                            case 3:
                                System.out.println("########## Até a próxima! \uD83D\uDE1C ##########");
                                System.exit(0);
                                break;
                            default:
                                break;
                        }
                    }
                }
                //MODO AUTOMÁTICO
            } else if (gameMode == 2) {
                cards = generateAutomaticCard(nicknames);
                printCards(nicknames, cards);
                i++;
                for (int k = 0; k < 26; k++) {
                    System.out.printf("\n %d° Round - SORTEIO \n", k+1);
                    sweepstake = sweepstakeAutomatic(numbersDrawn);
                    numbersDrawn = fillDrawnNumbers(sweepstake, numbersDrawn);
                    System.out.println(Arrays.toString(sweepstake));
                    markedCards = markCard(sweepstake, cards, markedCards);

                    int indexWinner = checkWinner(markedCards);
                    if(indexWinner != -1){
                        printWinner(indexWinner, cards, nicknames, numbersDrawn, k, markedCards);
                    }

                    j = 0;
                    while(j == 0) {
                        menu = menu();
                        switch (menu) {
                            case 1:
                                j++;
                                break;
                            case 2:
                                //IMPRIMIR O RANKING
                                System.out.println("RANKINGGGGGGGGG ######");
                                j++;
                                break;
                            case 3:
                                System.out.println("########## Até a próxima! \uD83D\uDE1C ##########");
                                System.exit(0);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }else{
                System.out.println("Modo de jogo escolhido inválido!");
            }
        }



    }

    public static void introMessage(){
        System.out.println("#########################################################");
        System.out.println("########## SEJA BEM-VINDO AO JUNIOR´S BINGO!☘ ##########");
        System.out.println("#########################################################");
    }
    public static String[] generateNicknames(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDigite os apelidos dos jogadores:  (Ex.: jogador1-jogador2-jogador3)");
        String input = scanner.nextLine();
        String[] nicknames = input.split("-");
        System.out.println("\nPlayers: " + Arrays.toString(nicknames));
        return nicknames;
    }
    public static int automaticOrManual(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nInforme o modo de jogo: \nDigite 1 para Manual \nDigite 2 para Automatico");
        return scanner.nextInt();
    }
    public static int[][] generateManualCard(String[] nicknames){
        Scanner scanner = new Scanner(System.in);
        int[][] cards = new int[nicknames.length][cardSize];
        for(int i = 0; i < nicknames.length; i++){
            System.out.printf("%s, escolha 5 numeros de 1 até 99 para a sua cartela:  (Ex.: 48,6,30,55,82)\n", nicknames[i]);
            String input = scanner.nextLine();
            String[] cardText = input.split(",");
            for(int j = 0; j < cardText.length; j++){
                cards[i][j] = Integer.parseInt(cardText[j]);
            }
        }
        return cards;
    }
    public static int[][] generateAutomaticCard(String[] nicknames){
        Random random = new Random();
        int number;
        int[][] cards = new int[nicknames.length][cardSize];

        for(int i = 0; i < nicknames.length; i++){
            int[] card = {0,0,0,0,0};
            for(int j = 0; j < cardSize; j++){
                number = random.nextInt(possibleNumbers + 1);
                if(checkExistingNumber(number, card) == 1 || number == 0){
                    j--;
                }else{
                    card[j] = number;
                }
            }
            for(int w = 0; w < cardSize; w++){
                cards[i][w] = card[w];
            }
        }
        return cards;
    }
    public static int checkExistingNumber(int number, int[] numbers){
        int i = 0;
        for(int j = 0; j < numbers.length; j++){
            if(numbers[j] == number){
                i = 1;
            }
        }
        return i;
    }
    public static void printCards(String[] nicknames, int[][] cards){
        for (int i = 0; i < nicknames.length; i++) {
            System.out.printf("Cartela do %s: ", nicknames[i]);
            System.out.println(Arrays.toString(cards[i]));
        }
    }
    public static int[] sweepstakeManual(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite os 5 números sorteados:    (Ex.:1,10,50,80,93)");
        String input = scanner.nextLine();
        String[] sweepstake = input.split(",");
        int[] numbers = new int[cardSize];
        for (int i = 0; i < sweepstake.length; i++) {
            numbers[i] = Integer.parseInt(sweepstake[i]);
        }
        return numbers;
    }
    public static int[] sweepstakeAutomatic(int[] numbersDrawn){
        Random random = new Random();
        int[] sweepstake = new int[cardSize];
        int number;
        for (int i = 0; i < sweepstake.length; i++) {
            number = random.nextInt(possibleNumbers);
            if(checkExistingNumber(number, numbersDrawn) == 0 && number != 0){
                sweepstake[i] = number;
            }else{
                i--;
            }
        }
        return sweepstake;
    }
    public static int[] fillDrawnNumbers(int[] newNumbers, int[] numbersDrawn){
        boolean exists = false;
        for (int i = 0; i < newNumbers.length; i++) {
            for (int j = 0; j < numbersDrawn.length; j++) {
                if(newNumbers[i] == numbersDrawn[j]){
                    exists = true;
                }
            }
            if(!exists){
                numbersDrawn[newNumbers[i] - 1] = newNumbers[i];
            }
        }
        return numbersDrawn;
    }
    public static int menu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n########## MENU ##########");
        System.out.println("Digite 1 para continuar.");
        System.out.println("Digite 2 para ver o Ranking.");
        System.out.println("Digite 3 para abortar o jogo.");
        return scanner.nextInt();
    }
    public static int[][] markCard(int[] sweepstake, int[][] cards, int[][] markedCards){
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[0].length; j++) {
                for (int k = 0; k < sweepstake.length; k++) {
                    if(cards[i][j] == sweepstake[k]){
                        markedCards[i][j] = 1;
                    }
                }
            }
        }
        return markedCards;
    }

    public static void rankingFinally(int[][] markedCards, String[] nicknames, int[][] cards){
        String [] apelido = new String[nicknames.length];
        int first = 0, second = 0, third = 0, numbersOne = 0;
        int[][] scores = new int[nicknames.length][1];
        int[] ranking = new int[nicknames.length];
        for(int i = 0; i < markedCards.length; i++){
            for (int j = 0; j < markedCards[0].length; j++) {
                if(markedCards[i][j] == 1){
                   numbersOne++;
                }
            }
            ranking[i] = numbersOne;
            int[] ranking2 = ranking;
        }
        System.out.println("########## RANKING ###############################################################################");
        Arrays.sort(ranking);
        System.out.printf("%d° posição:  %s Pontos:  %d", 1, nicknames[0], 5);
    }
    public static int checkWinner(int[][] markedCards){
        int numbersOne = 0;
        for(int i = 0; i < markedCards.length; i++){
            for (int j = 0; j < markedCards[0].length; j++) {
               if(markedCards[i][j] == 1){
                   numbersOne++;
               }
            }
            if(numbersOne == 5){
                return i;
            }
        }
        return -1;
    }
    public static void printWinner(int indexWinner, int[][] cards, String[] nicknames, int[] numbersDrawn, int k, int[][] markedCards) {
        k++;
        System.out.println("\n\n########## BINGO! #####################################################################################################");
        System.out.println("Quantidade de rodadas: " + k);
        Arrays.sort(cards[indexWinner]);
        System.out.println("Cartela premiada: " + Arrays.toString(cards[indexWinner]) + "  Player: " + nicknames[indexWinner]);
        System.out.println("Quantidade de números sorteados: " + (k*5) + "  Números sorteados: " + Arrays.toString(numbersDrawn));
        rankingFinally(markedCards, nicknames, cards);
        System.out.println("\n########## ATÉ A PRÓXIMA PLAYERS ######################################################################################");
        System.exit(0);
    }

}