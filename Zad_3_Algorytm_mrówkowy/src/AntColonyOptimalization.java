import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class AntColonyOptimalization {
    int liczba_atrakcji = 6;
    // atrakcje
    int[][] d = //
             {{0,  8, 7,  4,  6, 4}
            , {8,  0, 5,  7, 11, 5}
            , {7,  5, 0,  9,  6, 7}
            , {4,  7, 9,  0,  5, 6}
            , {6, 11, 6,  5,  0, 3}
            , {4,  5, 7,  6,  3, 0 }};
//    feromony
    BigDecimal[][] slady_feromonowe = new BigDecimal[liczba_atrakcji][liczba_atrakcji]//
//             {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
//            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
//            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
//            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
//            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
//            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}}
        ;

    double prop = 0.3;          //    prawdopodobieństwo wyboru przez mrówkę losowej atrakcji: 0,3
    int alfa = 4;           //    waga feromonów na potrzeby wyboru ścieżki przez mrówki α=4
    int beta = 7;            //    waga heurystyki na potrzeby wyboru ścieżki przez mrówkę β=7
    int liczba_iteracji = 10;      //    liczba iteracji: 1000
    double tempo_parowania = 0.4;   //    współczynnik wyparowywania feromonów: 0,4
    double mnoznik_liczby_mrowek = 0.5;      //    współczynnik liczby mrówek w kolonii na podstawie liczby atrakcji: 0,5

    ArrayList<Ant> populacja_mrowek = null;

    private void printD(){
        for (int i = 0; i < d[0].length; i++)
            for (int j = 0; j < d.length; j++)
                System.out.println("arr[" + i + "][" + j + "] = " + d[i][j]);
    }


    //done
    private ArrayList<Ant>  konfiguruj_mrowki(int liczba_atrakcji, double mnoznik_liczby_mrowek){
//    niech liczba_mrówek równa się zaokrąglij(liczba_mrówek * mnoznik_liczby_mrowek)
      int liczba_mrowek = (int) (liczba_atrakcji * mnoznik_liczby_mrowek);
//    niech kolonia_mrówek równa się pustej tablicy
      ArrayList<Ant> kolonia_mrowek = null;
//    dla i z przedziału(0, liczba_mrowek):
      for (int i = 0; i < liczba_mrowek; i++) {
          kolonia_mrowek.add(new Ant());
//        dodaj nową Mrówka do kolonia_mrówek
      }
//    zwróć kolonia_mrówek
      return kolonia_mrowek;
  }

    //done
    private ArrayList<Ant>  konfiguruj_mrowki(double mnoznik_liczby_mrowek){
        int liczba_mrowek = (int) (liczba_atrakcji * mnoznik_liczby_mrowek);//    niech liczba_mrówek równa się zaokrąglij(liczba_mrówek * mnoznik_liczby_mrowek)
        ArrayList<Ant> kolonia_mrowek = new ArrayList<Ant>();//    niech kolonia_mrówek równa się pustej tablicy
//        System.out.println("Liczba mrówek w koloni: " + liczba_mrowek);
        for (int i = 0; i < liczba_mrowek; i++) {//    dla i z przedziału(0, liczba_mrowek):
            kolonia_mrowek.add(new Ant());//        dodaj nową Mrówka do kolonia_mrówek
        }
        System.out.println("Liczba mrówek w koloni: " + kolonia_mrowek.size());
        return kolonia_mrowek;//    zwróć kolonia_mrówek
    }

//done
    private void aktualizuj_feromony(){
//    dla x z przedział(0, liczba_atrakcji):
//        dla y w przedział(0, liczba_atrakcji):
        print2D(slady_feromonowe);
        for (int x = 0; x < liczba_atrakcji; x++) {
            for (int y = 0; y < liczba_atrakcji; y++) {
                System.out.println(slady_feromonowe[x][y] );
//                slady_feromonowe[x][y] =  Math.round(slady_feromonowe[x][y] * tempo_parowania * 1000.0) / 1000.0;
                slady_feromonowe[x][y] =  slady_feromonowe[x][y].multiply(BigDecimal.valueOf(tempo_parowania));
                System.out.println(" -----  " + slady_feromonowe[x][y] );
            //    niech ślady_fermonowe[x][y] równa się     ślady_feromonowe[x][y] * tempo_parowania
            //    dla mrówka z kolonia_mrówek:
                for (Ant mrowka: populacja_mrowek) {
                    slady_feromonowe[x][y] = slady_feromonowe[x][y].add(BigDecimal.valueOf(mrowka.pobierz_przebyta_droge(d)));
                }
            }
        }
        print2D(slady_feromonowe);
    }

    private Ant pobierz_najlepszą(ArrayList<Ant>  populacja_mrowek, Ant poprzenia_najlepsza_mrowka){
        Ant najlepsza_mrowka = poprzenia_najlepsza_mrowka;//niech najlepsza_mrówka równa się poprzednia_najlepsza_mrówka
        for (Ant mrowka: populacja_mrowek) {//dla mrówka z populacja_mrówek:
            double przebyta_odległosc = mrowka.pobierz_przebyta_droge(d);//    niech przebyta_odległość równa się mrówka.pobierz_przebytą_odległość()
            if (przebyta_odległosc < najlepsza_mrowka.pobierz_przebyta_droge(d)) {// jeśli przebyta_odległość < najlepsza_mrówka.najlepsza_odległość:
                najlepsza_mrowka = mrowka;//niech najlepsza_mrówka równa się mrówka
            }
        }
        return najlepsza_mrowka;//        zwróć najlepsza_mrówka
    }

    //done
    private BigDecimal[][] konfiuruj_feromony() {
        BigDecimal[][] reset_feromonow = new BigDecimal[liczba_atrakcji][liczba_atrakcji];
        for (int i = 0; i < liczba_atrakcji; i++) {
            for (int j = 0; j < liczba_atrakcji; j++) {
                reset_feromonow[i][j] = BigDecimal.valueOf(1.0);
            }
        }
        return reset_feromonow;
    }

//    public void rozwiaz(int liczba_iteracji, double tempo_parowania, double mnoznik_liczby_mrowek, int liczba_atrakcji){
    public void rozwiaz(){
        slady_feromonowe = konfiuruj_feromony();//niech slady_feromonowe równa się konfiuruj_feromony()
        Ant najlepsza_mrowka = new Ant(); //niech najlepsza_mrówka równa się NIC
        for (int i = 1; i <= liczba_iteracji; i++) {//    dla i z przedziałów(0, liczba_iteracji)
            System.out.println("Iteracja: " + i);
            populacja_mrowek = konfiguruj_mrowki(mnoznik_liczby_mrowek);//       niech kolonia_mrówek równa się konfiguruj_mrówki(mnoznik_liczby_mrowek)
            for (int r = 0; r < liczba_atrakcji - 1; r++) {//        dla r z przedział(0, liczba_atrakcji – 1):
                przemiesc_mrowki(populacja_mrowek);
            }
            wyswietl_przebyte_drogi(populacja_mrowek);
            aktualizuj_feromony();
            najlepsza_mrowka = pobierz_najlepszą(populacja_mrowek,najlepsza_mrowka);//niech najlepsza_mrówka równa się pobierz_najlepszą(kolonia_mrowek);
            System.out.println(" najlepsza droga " + najlepsza_mrowka.pobierz_przebyta_droge(d));

        }
    }

    private void przemiesc_mrowki(ArrayList<Ant> populacja_mrowek) {
        for (Ant mrowka : populacja_mrowek) {
            mrowka.odwiedz_atrakcje(slady_feromonowe,liczba_atrakcji,mrowka,alfa,beta,d);
        }
    }

    private void wyswietl_przebyte_drogi(ArrayList<Ant> populacja_mrowek) {
        for (Ant mrowka : populacja_mrowek) {
            mrowka.wyswietl_sciezka();
        }
    }

    public static void print2D(BigDecimal  mat[][])
    {
        // Loop through all rows
        for (BigDecimal [] row : mat)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
}
