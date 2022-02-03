import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Ant {

    public ArrayList<Integer> odwiedzone_atrakcje = new ArrayList<>();
    public ArrayList<Integer> wszystkie_atrakcje = new ArrayList<>();
//    ArrayList<BigDecimal> uzywane_prawdopodobienstwa = null;
    ArrayList<Double> uzywane_prawdopodobienstwa = null;
    ArrayList<Integer> uzywane_indeksy = null;
    ArrayList<Integer> dostepne_atrakcje = null;
    MathContext _mathContext = new MathContext(4, MathContext.DECIMAL32.getRoundingMode());
//    Ant(){
//        wszystkie_atrakcje_restart();
//    };

    public void odwiedz_atrakcje(BigDecimal[][] slady_feromonowe) {

    }
//    Ant(){}

//    public void odwiedz_atrakcje(BigDecimal[][] slady_feromonowe, int liczba_atrakcji, Ant mrowka, int alfa, int beta, int[][] d) {
//        if(odwiedzone_atrakcje.size() != 0) {
//            odwiedź_atrakcje_probabilistycznie(slady_feromonowe, liczba_atrakcji, mrowka, alfa, beta, d);
//            odwiedzone_atrakcje.add(selekcja_ruletkowa());
//        }
////        else{
////            odwiedź_losową_atrakcję(liczba_atrakcji);
////            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
////        }
////        printArray(odwiedzone_atrakcje);
//    }

    public void odwiedz_atrakcje(double[][] slady_feromonowe, int liczba_atrakcji, Ant mrowka, int alfa, int beta, int[][] d) {
        if(odwiedzone_atrakcje.size() != 0) {
            odwiedź_atrakcje_probabilistycznie(slady_feromonowe, liczba_atrakcji, mrowka, alfa, beta, d);
            odwiedzone_atrakcje.add(selekcja_ruletkowa());
        }
//        else{
//            odwiedź_losową_atrakcję(liczba_atrakcji);
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
//        }
//        printArray(odwiedzone_atrakcje);
    }

    public void odwiedź_losową_atrakcję(int liczba_atrakcji){
        wszystkie_atrakcje_restart(liczba_atrakcji);
        odwiedzone_atrakcje.add((int) Math.floor(Math.random() * 6));
    }

//    public void odwiedź_atrakcję_probabilistycznie(double[][] slady_feromonowe){
//
//    }
//    public void odwiedź_atrakcje_probabilistycznie(BigDecimal[][] slady_feromonowe,int liczba_atrakcji, Ant mrowka, int alfa, int beta, int[][] d){
    public void odwiedź_atrakcje_probabilistycznie(double[][] slady_feromonowe,int liczba_atrakcji, Ant mrowka, int alfa, int beta, int[][] d){
        int idx = 0;
        if ( mrowka.odwiedzone_atrakcje.size() > 0){
            idx =  mrowka.odwiedzone_atrakcje.size()-1;
        }
        int aktualna_atrakcja = mrowka.odwiedzone_atrakcje.get(idx); //        niech aktualna_atrakcja równa się mrowka.odwiedzone_atrakcje[-1]
        wszystkie_atrakcje_restart(liczba_atrakcji);//        niech wszystkie_atrakcje równa się przedział (0, liczba_atrakcji)
//        ArrayList<Integer> dostepne_atrakcje = wszystkie_atrakcje;
        dostepne_atrakcje = wszystkie_atrakcje;
        dostepne_atrakcje.removeAll(odwiedzone_atrakcje);//        niech dostępne_atrakcje równa się wszystkie_atrakcje – mrówka.odwiedzone_atrakcje
//        ArrayList<Integer> uzywane_indeksy = null;//        niech używane_indeksy równa się pustej tablicy
        uzywane_indeksy = new ArrayList<>();//        niech używane_indeksy równa się pustej tablicy
//        ArrayList<BigDecimal> uzywane_prawdopodobienstwo = new ArrayList<>();//        niech używane_prawdopodobieństwo równa się pustej tablicy
        ArrayList<Double> uzywane_prawdopodobienstwo = new ArrayList<>();//        niech używane_prawdopodobieństwo równa się pustej tablicy
//        BigDecimal suma_prawdopodobienstw = BigDecimal.valueOf(0.0);//        niech suma_prawdopodobieństw równa się 0
        double suma_prawdopodobienstw = 0.0;//        niech suma_prawdopodobieństw równa się 0
        for (Integer atrakcja: dostepne_atrakcje) {//        dla atrakcja z dostępne_atrakcje:
            uzywane_indeksy.add(atrakcja);//        dodaj atrakcja do używane_indeksy
//            BigDecimal feromony_na_sciezce = slady_feromonowe[aktualna_atrakcja][atrakcja].pow( alfa,_mathContext);//        niech feromony_na_ścieżce równa się bibl_mat.potęga(slady_feromonowe[aktualna_atrakcja][atrakcja], alfa)
            double feromony_na_sciezce = Math.pow(slady_feromonowe[aktualna_atrakcja][atrakcja], alfa);//        niech feromony_na_ścieżce równa się bibl_mat.potęga(slady_feromonowe[aktualna_atrakcja][atrakcja], alfa)
//            System.out.println("[aktualna_atrakcja][atrakcja] " + aktualna_atrakcja + " " + atrakcja);
//            System.out.println("slady_feromonowe[aktualna_atrakcja][atrakcja] " + slady_feromonowe[aktualna_atrakcja][atrakcja]);
//            System.out.println("feromony_na_sciezce " + feromony_na_sciezce);
//            BigDecimal heurystyka_dla_sciezki = BigDecimal.valueOf(1.0/d[aktualna_atrakcja][atrakcja]).pow(beta,_mathContext);//        niech heurystyka_dla_ścieżki równa się bibl_mat.potęga(1/odległość_między_atrakcjami[aktualna_atrakcja][atrakcja], beta)
            double heurystyka_dla_sciezki = Math.pow((1.0/d[aktualna_atrakcja][atrakcja]),beta);//        niech heurystyka_dla_ścieżki równa się bibl_mat.potęga(1/odległość_między_atrakcjami[aktualna_atrakcja][atrakcja], beta)
//            System.out.println("heurystyka_dla_sciezki " + heurystyka_dla_sciezki);
//            BigDecimal prawdopodobienstwo = feromony_na_sciezce.multiply(heurystyka_dla_sciezki,_mathContext);//        niech prawdopodobieństwo równa się feromony_na_ścieżce * hesurystyka_dla ścieżki
            double prawdopodobienstwo = feromony_na_sciezce * heurystyka_dla_sciezki;//        niech prawdopodobieństwo równa się feromony_na_ścieżce * hesurystyka_dla ścieżki
//            System.out.println("prawdopodobienstwo " + prawdopodobienstwo);
            uzywane_prawdopodobienstwo.add(prawdopodobienstwo);//        dodaj prawdopodobieństwo do używane_prawdopodobieństwa
        }
        uzywane_prawdopodobienstwa(uzywane_prawdopodobienstwo);
        uzywane_prawdopodobienstwa = uzywane_prawdopodobienstwo;//        niech używane_prawdopodobieństwa równa się [prawdopodobieństwo / suma_prawdopodobieństw dla prawdopodobieństwo z używane_prawdopodobieństwa]
//        zwróć [używane_indeksy, używane_prawdopodobieństwa]
    }

//    public double selekcja_ruletkowa(int[] używane_indeksy,double uzywane_prawdopodobieństwa,int liczba_dostepnych_atrkacji){
//public int selekcja_ruletkowa(){
////        double[] przedzialy = null;//        niech przedziały równa się pustej tablicy
////        BigDecimal w_sumie = BigDecimal.valueOf(0) ;//        niech w_sumie równa się 0
//    double w_sumie = 0 ;//        niech w_sumie równa się 0
//    int liczba_dostepnych_atrkacji = dostepne_atrakcje.size();
//
//    double losowanie = Math.random(); //        niech losowanie równa się losowa(0, 1)
//    int wynik = 0;
//    for (int i = 0; i < liczba_dostepnych_atrkacji; i++) {//        dla i z przedział(0, liczba_dostępnych_atrkacji):
//
//        if(BigDecimal.valueOf(losowanie).compareTo(w_sumie) > 0  && BigDecimal.valueOf(losowanie).compareTo(uzywane_prawdopodobienstwa.get(i).add(w_sumie)) <= 0 ) {
////            dodaj [używane_indeksy[i], w_sumie, w_sumie+używane_prawdopodobieństwa[i]]
////            do przedziały w_sumie += używane_prawdopodobieństwa[i]
//            wynik = uzywane_indeksy.get(i);
//        }
////                w_sumie = w_sumie.add(uzywane_prawdopodobienstwa.get(i));
//        w_sumie += uzywane_prawdopodobienstwa.get(i);
//    }


    public int selekcja_ruletkowa(){
//        double[] przedzialy = null;//        niech przedziały równa się pustej tablicy
//        BigDecimal w_sumie = BigDecimal.valueOf(0) ;//        niech w_sumie równa się 0
        double w_sumie = 0 ;//        niech w_sumie równa się 0
        int liczba_dostepnych_atrkacji = dostepne_atrakcje.size();

        double losowanie = Math.random(); //        niech losowanie równa się losowa(0, 1)
        int wynik = 0;
        for (int i = 0; i < liczba_dostepnych_atrkacji; i++) {//        dla i z przedział(0, liczba_dostępnych_atrkacji):

            if(losowanie> w_sumie && losowanie <= (uzywane_prawdopodobienstwa.get(i) + w_sumie) ) {
//            dodaj [używane_indeksy[i], w_sumie, w_sumie+używane_prawdopodobieństwa[i]]
//            do przedziały w_sumie += używane_prawdopodobieństwa[i]
                wynik = uzywane_indeksy.get(i);
            }
//                w_sumie = w_sumie.add(uzywane_prawdopodobienstwa.get(i));
                w_sumie += uzywane_prawdopodobienstwa.get(i);
        }


        //        niech wynik równa się [przedział dla przedział z przedziały,jeśli przedział[1] < losowanie <= przedział[2]]
        return wynik;
    }

    //done
    public double pobierz_przebyta_droge(Ant mrowka, int[][] d) {
        double total_distance = 0.0;//        niech total_distance równa się 0
        for (int a = 1; a < mrowka.odwiedzone_atrakcje.size(); a++) {//        dla a z przedziału(1, długość mrówka.odwiedzone_atrakcje):
            total_distance += d[mrowka.odwiedzone_atrakcje.get(a-1)][mrowka.odwiedzone_atrakcje.get(a)];//total_distance += odległość między mrówka.odwiedzone_atrakcje[a-1] i mrówka.odwiedzone_atrakcje[a]
        }
        return total_distance;//        zwróc total_distance
    }

    //done
    public double pobierz_przebyta_droge(int[][] d) {
        double total_distance = 0.0;//        niech total_distance równa się 0
        for (int a = 1; a < odwiedzone_atrakcje.size(); a++) {//        dla a z przedziału(1, długość mrówka.odwiedzone_atrakcje):
            total_distance += d[odwiedzone_atrakcje.get(a - 1)][odwiedzone_atrakcje.get(a)];//        total_distance += odległość między mrówka.odwiedzone_atrakcje[a-1] i mrówka.odwiedzone_atrakcje[a]
        }
        return total_distance;//        zwróc total_distance
    }

    public ArrayList<Integer> wszystkie_atrakcje(int liczba_atrakcji) {
        ArrayList<Integer> wszystkie_atrakcje = null;
        for (int i = 0; i < liczba_atrakcji; i++) {
            wszystkie_atrakcje.add(i);
        }
        return wszystkie_atrakcje;
    }

    public void wszystkie_atrakcje_restart(int liczba_atrakcji) {
        wszystkie_atrakcje = new ArrayList<>();
        for (int i = 0; i < liczba_atrakcji; i++) {
            wszystkie_atrakcje.add(i);
        }
    }

//    public BigDecimal sum_elements(ArrayList<BigDecimal> uzywane_prawdopodobienstwo){
//        BigDecimal sum = BigDecimal.valueOf(0.0);
//        for (BigDecimal prawdopodobienstwo : uzywane_prawdopodobienstwo ) {
//            sum = sum.add(prawdopodobienstwo);
//        }
//        return sum;
//    }
    public double sum_elements(ArrayList<Double> uzywane_prawdopodobienstwo){
        Double sum = 0.0;
        for (Double prawdopodobienstwo : uzywane_prawdopodobienstwo ) {
            sum += prawdopodobienstwo;
        }
        return sum;
    }

//    public void uzywane_prawdopodobienstwa(ArrayList<BigDecimal> uzywane_prawdopodobienstwo){
//        BigDecimal sum = BigDecimal.valueOf(0.0);
//        sum = sum_elements(uzywane_prawdopodobienstwo);
//        for (int i = 0; i < uzywane_prawdopodobienstwo.size(); i++) {
//            uzywane_prawdopodobienstwo.set(i,uzywane_prawdopodobienstwo.get(i).divide(sum, 4, RoundingMode.HALF_UP));
//        }
//    }

    public void uzywane_prawdopodobienstwa(ArrayList<Double> uzywane_prawdopodobienstwo){
        double sum = 0.0;
        sum = sum_elements(uzywane_prawdopodobienstwo);
        for (int i = 0; i < uzywane_prawdopodobienstwo.size(); i++) {
            uzywane_prawdopodobienstwo.set(i,uzywane_prawdopodobienstwo.get(i) / sum);
        }
    }

    public void printArray(ArrayList<Integer> lista){
        System.out.print(" - Scieżka ");
        for (Object a :
                lista) {
            System.out.print(a + " ");
        }
    }


    public void wyswietl_sciezka(){
        System.out.print(" - Scieżka ");
        for (Object atrakcja :
                odwiedzone_atrakcje) {
            System.out.print(atrakcja + " ");
        }
    }

}
