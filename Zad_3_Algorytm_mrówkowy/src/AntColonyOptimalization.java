import java.util.ArrayList;

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
    double[][] slady_feromonowe = //
             {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
            , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
    
    int liczba_atrakcji = 6;
    double prop = 0.3;          //    prawdopodobieństwo wyboru przez mrówkę losowej atrakcji: 0,3
    double alfa = 4;           //    waga feromonów na potrzeby wyboru ścieżki przez mrówki α=4
    double beta = 7;            //    waga heurystyki na potrzeby wyboru ścieżki przez mrówkę β=7
    int liczba_iteracji = 1000;      //    liczba iteracji: 1000
    double tempo_parowania = 0.4;   //    współczynnik wyparowywania feromonów: 0,4
    double mnoznik_liczby_mrowek = 0.5;      //    współczynnik liczby mrówek w kolonii na podstawie liczby atrakcji: 0,5

    ArrayList<Ant> populacja_mrowek = null;

    public void printD(){
        for (int i = 0; i < d[0].length; i++)
            for (int j = 0; j < d.length; j++)
                System.out.println("arr[" + i + "][" + j + "] = " + d[i][j]);
    }


    //done
  public ArrayList<Ant>  konfiguruj_mrowki(int liczba_atrakcji, double mnoznik_liczby_mrowek){
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
    public ArrayList<Ant>  konfiguruj_mrowki(double mnoznik_liczby_mrowek){
//    niech liczba_mrówek równa się zaokrąglij(liczba_mrówek * mnoznik_liczby_mrowek)
        int liczba_mrowek = (int) (liczba_atrakcji * mnoznik_liczby_mrowek);
//    niech kolonia_mrówek równa się pustej tablicy
        ArrayList<Ant> kolonia_mrowek = new ArrayList<Ant>();
//    dla i z przedziału(0, liczba_mrowek):
        for (int i = 0; i < liczba_mrowek; i++) {
            kolonia_mrowek.add(new Ant());
//        dodaj nową Mrówka do kolonia_mrówek
        }
//    zwróć kolonia_mrówek
        return kolonia_mrowek;
    }

//done
    public void aktualizuj_feromony(double tempo_parowania, double[][] slady_feromonowe,int liczba_atrakcji){
//    dla x z przedział(0, liczba_atrakcji):
//        dla y w przedział(0, liczba_atrakcji):
        for (int x = 0; x < liczba_atrakcji; x++) {
            for (int y = 0; y < liczba_atrakcji; y++) {
                slady_feromonowe[x][y] = slady_feromonowe[x][y] * tempo_parowania;
            //    niech ślady_fermonowe[x][y] równa się     ślady_feromonowe[x][y] * tempo_parowania
            //    dla mrówka z kolonia_mrówek:
                for (Ant mrowka: populacja_mrowek) {
                    slady_feromonowe[x][y] += 1 / mrowka.pobierz_przebyta_droge(d);
                }
            }
        }
    }

    public Ant pobierz_najlepszą(ArrayList<Ant>  populacja_mrowek, Ant poprzenia_najlepsza_mrowka){
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
    private double[][] konfiuruj_feromony() {
        double[][] reset_feromonow = new double[liczba_atrakcji][liczba_atrakcji];
        for (int i = 0; i < liczba_atrakcji; i++) {
            for (int j = 0; j < liczba_atrakcji; j++) {
                reset_feromonow[i][j] = 1.0;
            }
        }
        return reset_feromonow;
    }

//    public void rozwiaz(int liczba_iteracji, double tempo_parowania, double mnoznik_liczby_mrowek, int liczba_atrakcji){
    public void rozwiaz(){
        slady_feromonowe = konfiuruj_feromony();//niech slady_feromonowe równa się konfiuruj_feromony()
        Ant najlepsza_mrowka = null; //niech najlepsza_mrówka równa się NIC
        for (int i = 0; i < liczba_iteracji; i++) {//    dla i z przedziałów(0, liczba_iteracji)
            populacja_mrowek = konfiguruj_mrowki(mnoznik_liczby_mrowek);//       niech kolonia_mrówek równa się konfiguruj_mrówki(mnoznik_liczby_mrowek)
            for (int r = 0; r < liczba_atrakcji - 1; r++) {//        dla r z przedział(0, liczba_atrakcji – 1):
                przemiesc_mrowki(populacja_mrowek);
            }
            aktualizuj_feromony(tempo_parowania, slady_feromonowe, liczba_atrakcji);
            najlepsza_mrowka = pobierz_najlepszą(populacja_mrowek,najlepsza_mrowka);//niech najlepsza_mrówka równa się pobierz_najlepszą(kolonia_mrowek);
        }
    }

    private void przemiesc_mrowki(ArrayList<Ant> populacja_mrowek) {
        for (Ant mrowka : populacja_mrowek) {
            mrowka.odwiedz_atrakcje(slady_feromonowe,liczba_atrakcji,mrowka,alfa,beta,d);
        }
    }

}
