public class AntColonyOptimalization {
    // atrakcje
    int[][] d = //
             {{0,  8, 7,  4,  6, 4}
            , {8,  0, 5,  7, 11, 5}
            , {7,  5, 0,  9,  6, 7}
            , {4,  7, 9,  0,  5, 6}
            , {6, 11, 6,  5,  0, 3}
            , {4,  5, 7,  6,  3, 0 }};
//    feromony
    int[][] f = //
             {{1, 1, 1, 1, 1, 1}
            , {1, 1, 1, 1, 1, 1}
            , {1, 1, 1, 1, 1, 1}
            , {1, 1, 1, 1, 1, 1}
            , {1, 1, 1, 1, 1, 1}
            , {1, 1, 1, 1, 1, 1}};

    double prop = 0.3;          //    prawdopodobieństwo wyboru przez mrówkę losowej atrakcji: 0,3
    double alpha = 4;           //    waga feromonów na potrzeby wyboru ścieżki przez mrówki α=4
    double beta = 7;            //    waga heurystyki na potrzeby wyboru ścieżki przez mrówkę β=7
    int iterations = 1000;      //    liczba iteracji: 1000
    double evaporation = 0.4;   //    współczynnik wyparowywania feromonów: 0,4
    double ant_calc = 0.5;      //    współczynnik liczby mrówek w kolonii na podstawie liczby atrakcji: 0,5


    public void printD(){
        for (int i = 0; i < d[0].length; i++)
            for (int j = 0; j < d.length; j++)
                System.out.println("arr[" + i + "][" + j + "] = " + d[i][j]);
    }

}
