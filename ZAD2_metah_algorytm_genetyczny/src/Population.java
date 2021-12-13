import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Population {
    int population_size = 4;
    double mutation = 0.0001;
    double prop_cross = 1;
    int gen_size = 5;
    int max_gen = gen_size;
    double precision = 1;
    int min;
    int max;
    String[][] population;
    String[][] new_population;
    double mean;
    double sum;
    double max_value;
    int columns = 15;

    //chart
    double[][] chartData;

    /*
    [0]x
    [1]f(x)
    [2]fi/Fśrednia
    [3]fi/Fsuma Metoda ruletki
    [4]Losowanie  rodziców z metody ruletki, przedstawienie jako podzial kołowy
    [5]Losowanie do ruletki
    [6]Wylosowanie Rodzice
    [7]Losowanie (par) rodziców do krzyżowania
    []Losowanie punktu krzyżowania
    []Nowa populacja (zalogowanie przejscia i przepisanie ich do tablicy na początek)

     */

    public Population(int population_size, double mutation, double prop_cross, int gen_size, int min,int max,double precision) {
        this.population_size = population_size;
        this.mutation = mutation;
        this.prop_cross = prop_cross;
        this.gen_size = gen_size;
        this.min = min;
        this.max = max;
        this.population = new String[population_size+3][columns];
        this.new_population = new String[population_size][1];
        this.precision = precision;
        setGenSize();
        this.chartData = new double[3][gen_size];

//        population[population_size][1] = "sum";
//        population[population_size+1][1] = "mean";
//        population[population_size+2][1] = "max";
//
//        population[population_size][9] = "sum";
//        population[population_size+1][9] = "mean";
//        population[population_size+2][9] = "max";


    }

    public void runAG(){
        runAG(1);
    }
    public void runAG(int generations){
        for (int i = 1; i <= generations; i++) {

            for (int x = 0; x < population_size; x++) {
                new_population[x][0] = population[x][12];
            }
            setEmptyPopulation();

            for (int j = 0; j < population_size; j++) {
                population[j][0] = new_population[j][0];
            }

            population[population_size][1] = "sum";
            population[population_size+1][1] = "mean";
            population[population_size+2][1] = "max";

            population[population_size][8] = "sum";
            population[population_size+1][8] = "mean";
            population[population_size+2][8] = "max";

//            System.out.println("old (0,12)"+ population[0][12]);
//            System.out.println("new (0,0)"+ new_population[0][0]);
//            System.out.println("reasigned (0,0)"+ population[0][0]);
//            System.out.println("old (max,12)"+ population[population_size-1][12]);
//            System.out.println("new (max,0)"+ new_population[population_size-1][0]);
//            System.out.println("reasigned (max,0)"+ population[population_size-1][0]);
            if(population[0][0]=="" || population[0][0]==null){
//                System.out.println("(0,0)"+ population[0][0]);
                generatePopulation();//[0]
            }
    //        calculateFunction();//[1]
            setCalculations(0,2);//[1]
            calculateFi_Fmean();//[2]
            calculateFi_Fsum();//[3]
            rulletSelection();//[4]
            calculateParents();//[5][6]
            generatePartner();//[7]
            generateMutationPoint();//[8]
            interbreedingProbability();//[9]
            interbreeding();//[10]
            mutation();//[11]
            fit();//[12]
            // TODO: 27.11.2021 prio:1 powtarzanie generacji
            // TODO: 27.11.2021 prio:1 dodac stop do generacji
            // TODO: 27.11.2021 prio:2 zapisywanie do pliku wyniku
            printPopulation();

            chartData[0][i-1] = Double.parseDouble(population[population_size][2]);  // sum
            chartData[1][i-1] = Double.parseDouble(population[population_size+1][2]);// mean
            chartData[2][i-1] = Double.parseDouble(population[population_size+2][2]);// max_value
        }
    }

    //    [0]x
    private void generatePopulation() {
        for (int i = 0; i < population_size; i++) {
            population[i][0] = Integer.toString(bin2int(generateSpecimen()));
        }
    }

    private void setEmptyPopulation() {
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < columns; j++) {
                population[i][j] = "";
            }
        }
    }

    // [1]f(x)
    private void calculateFunction(){
        sum = 0;
        mean = 0;
        max_value = 0;
        for (int i = 0; i < population_size; i++) {
            double f = function(toXdouble(population[i][0]));
//            double f = function(Integer.parseInt(population[i][0]));
//            double f = function1(Integer.parseInt(population[i][0]));
//            double f = function2(Integer.parseInt(population[i][0]));
//            double f = function3(Integer.parseInt(population[i][0]));
            if(f>max_value){
                max_value = f;
            }
            sum = sum + f;
            population[i][1] = Double.toString(f);
        }
        mean = sum/population_size;

        population[population_size][2] = Double.toString(sum);
        population[population_size+1][2] = Double.toString(mean);
        population[population_size+2][2] = Double.toString(max_value);
    }

    private void setCalculations(int from, int to){
        sum = 0;
        mean = 0;
        max_value = 0;
        for (int i = 0; i < population_size; i++) {
            double f = function(toXdouble(population[i][from]));
//            double f = function(Integer.parseInt(population[i][0]));
//            double f = function1(Integer.parseInt(population[i][0]));
//            double f = function2(Integer.parseInt(population[i][0]));
//            double f = function3(Integer.parseInt(population[i][0]));
            if(f>max_value){
                max_value = f;
            }
            sum = sum + f;
            population[i][from+1] = Double.toString(f);
        }
        mean = sum/population_size;

        population[population_size][to] = Double.toString(sum);
        population[population_size+1][to] = Double.toString(mean);
        population[population_size+2][to] = Double.toString(max_value);

    }
    //    [2]fi/Fśrednia
    private void calculateFi_Fmean(){
        for (int i = 0; i < population_size; i++) {
            double f = Double.parseDouble(population[i][1])/mean;
            population[i][2] = Double.toString(f);
        }
    }
    //    [3]fi/Fsuma Metoda ruletki
    private void calculateFi_Fsum(){
        for (int i = 0; i < population_size; i++) {
            double f = Double.parseDouble(population[i][1])/sum;
            population[i][3] = Double.toString(f);
        }
    }
    //[4]Losowanie  rodziców z metody ruletki, przedstawienie jako int albo binarnie
    private void rulletSelection(){
        double[] rullet = new double[population_size];
//        math.random() * (max - min + 1) + min
        for (int i = 0; i < population_size; i++) {
            if (i>0){
                rullet[i] = Double.parseDouble(population[i][3]) + rullet[i-1];
            }
            else {
                rullet[i] = Double.parseDouble(population[i][3]);
            }
            population[i][4] = Double.toString(rullet[i]);
        }

//        System.out.println(rnd);
    }

    //    [5][6]Rodzice
    private void calculateParents(){
        double rnd;
        for (int i = 0; i < population_size; i++) {
            rnd = Math.random() ;
            population[i][5] = Double.toString(rnd);
            for (int j = 0; j < population_size; j++) {
                if(Double.parseDouble(population[j][4]) >= rnd){
                    population[i][6] = population[j][0];
                    break;
                }
            }
        }
    }
    //[7]
    private void generatePartner(){
        int[] rnd_part = withoutReturn(population_size);
        for (int i = 0; i < population_size; i++) {
            population[i][7] = Integer.toString(rnd_part[i]);
        }
    }

    //[8]
    private void generateMutationPoint(){
        int[] rnd_part = randomArray(population_size/2,1,gen_size-1);
//        int[] rnd_part = generateRandomArray(1,gen_size-1, population_size/2);
        for (int i = 0; i < population_size; i++) {
            if(Integer.parseInt(population[i][7])%2 == 0){
                int idx = Integer.parseInt(population[i][7])/2;
                population[i][8] = Integer.toString(rnd_part[idx-1]);
//                population[i][8] = Integer.toString(randomMax(gen_size-1));
                for (int j = 0; j < population_size; j++) {
                    if(i!=j){
                        if(Integer.parseInt(population[i][7])-1 == Integer.parseInt(population[j][7])){
                            population[j][8] = Integer.toString(rnd_part[idx-1]);
                        }
                    }
                }
            }
        }
    }
    //[9]
    private void interbreedingProbability(){
        for (int i = 0; i < population_size; i++) {
            population[i][9] = Double.toString(Math.random());
        }
    }
    //[10]
    private void interbreeding(){
        for (int i = 0; i < population_size; i++) {
            if(Integer.parseInt(population[i][7])%2 == 0){
                for (int j = 0; j < population_size; j++) {
                    if(i!=j){
                        if (Double.parseDouble(population[i][9]) < prop_cross){
                            if(Integer.parseInt(population[i][7])-1 == Integer.parseInt(population[j][7])){
                                population[i][10] = Integer.toString(bin2int(int2bin(population[i][6]).substring(0,Integer.parseInt(population[i][8]))
                                        + int2bin(population[j][6]).substring(Integer.parseInt(population[j][8]))));
                            }
                        }else{
                            population[i][10] = population[i][6];
                        }
                    }
                }
            }else{
                for (int j = 0; j < population_size; j++) {
                    if(i!=j){
                        if (Double.parseDouble(population[i][9]) < prop_cross){
                            if(Integer.parseInt(population[i][7])+1 == Integer.parseInt(population[j][7])){
                                population[i][10] = Integer.toString(bin2int(int2bin(population[i][6]).substring(0,Integer.parseInt(population[i][8]))
                                        + int2bin(population[j][6]).substring(Integer.parseInt(population[j][8]))));
                            }
                        }else{
                            population[i][10] = population[i][6];
                        }
                    }
                }
            }
        }
    }
    //[11]
    private void mutation(){
        int mutations = (int) Math.floor(gen_size*population_size*mutation);
//        double mutations =  (double) gen_size*(double) population_size*mutation;
//        System.out.println("Mutations: " + mutations);
//        int[] mutate_row = withoutReturn(mutations,gen_size);
        int[] mutate_row = randomArrayUnique(mutations,0, population_size);
        for (int i = 0; i < mutations; i++) {
//            int mutate = randomMinMax(0,population_size);
            int mutate_bit = randomMinMax(1,gen_size-1);
//            System.out.println("-Mutate row: " + mutate_row[i] + " and bit: " + mutate_bit);
            population[mutate_row[i]-1][11] = Integer.toString(mutate_bit);
        }
        String binNumber ;
        int x;
        char xchr;
        char chr;
        for (int i = 0; i < population_size; i++) {
//            System.out.println(int2bin(population[i][10]));
            binNumber = int2bin(population[i][10]);
            if (population[i][11] != "") {
//                System.out.println("char " + int2bin(population[i][10]).charAt(Integer.parseInt(population[i][11])) + " at " + Integer.parseInt(population[i][11]));
//                System.out.println(population[i][11]);
                x = Integer.parseInt(population[i][11]);
                xchr = int2bin(population[i][10]).charAt(x) ;
//                chr = (Integer.toString(Integer.parseInt(Character.toString(xchr))+1%2)).charAt(0);
                if (xchr == '1'){
                    chr = '0';
                } else{
                    chr = '1';
                }
//                System.out.println("x: " + x);
//                System.out.println("char at x: " + xchr);
//                System.out.println("new char: " + chr);
//                binNumber.replace(binNumber.charAt(x), chr);
                binNumber = replaceCharUsingCharArray(binNumber, chr, x);
//                System.out.println(binNumber);
                population[i][12] = Integer.toString(bin2int(binNumber));
            } else {
                population[i][12] = population[i][10];
            }
        }
    }
    //[12]
    private void fit(){


    }



    // tools
    public String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
//        System.out.println("lenght of string " + chars.length);
        return String.valueOf(chars);
    }

    public void printPopulation(){

        DecimalFormat df = new DecimalFormat("0.0000");//df.format()
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
//        String format = "%3s%14s%5s%22s%22s%25s%25s%25s%12s%15s%15s%22s%23s%25s%22s%15s";
        String format = "%3s%10s%11s%12s%15s%15s%12s%12s%12s%15s%15s%21s%23s%19s%18s%15s%15s%15s";
        System.out.format(format
                , "id"
                , "gen[0]"
                , "x[0]"
                , "F(x)[1]"
                , "F(i)/śred.[2]"
                , "F(i)/suma[3]"
                , "Rullet[4]"
                , "Random[5]"
                , "Rodzice[6]"
                , "gen Rodzice[6]"
                , "Los.partner[7]"
                , "Pkt. krzyżowania[8]"
                , "Prawd. krzyżowania[9]"
                , "Nowa Pop. gen[10]"
                , "Nowa Pop. x[10]"
                , "Mutacja [11]"
                , "after Mutacja [12]"
                , "after Mutacja x[12]"
        );
        System.out.println();
        for (int i = 0; i < population.length-3; i++) {
            System.out.format(
                      format
                    , i+1
                    , int2bin(population[i][0])
                    , df.format(Double.parseDouble(toX(population[i][0])))
                    , df.format(Double.parseDouble(population[i][1]))
                    , df.format(Double.parseDouble(population[i][2]))
                    , df.format(Double.parseDouble(population[i][3]))
                    , df.format(Double.parseDouble(population[i][4]))
                    , df.format(Double.parseDouble(population[i][5]))
                    , population[i][6]
                    , int2bin(population[i][6])
                    , population[i][7]
                    , population[i][8]
                    , df.format(Double.parseDouble(population[i][9]))
                    , int2bin(population[i][10])
                    , df.format(Double.parseDouble(toX(population[i][10])))
                    , population[i][11]
                    , int2bin(population[i][12])
                    , df.format(Double.parseDouble(toX(population[i][12])))
                    );
            System.out.println();
        }

        for (int i = population.length-3; i < population.length; i++) {
            System.out.format(
                    format
                    , i+1
                    , population[i][0]
                    , population[i][0]
                    , population[i][1]
                    , df.format(Double.parseDouble(population[i][2]))
                    , population[i][3]
                    , population[i][4]
                    , population[i][5]
                    , population[i][6]
                    , population[i][6]
                    , population[i][7]
                    , population[i][8]
                    , population[i][9]
                    , population[i][10]
                    , population[i][10]
                    , population[i][11]
                    , population[i][12]
                    , population[i][12]
            );
            System.out.println();
        }
        System.out.println();
    }
    private void setGenSize(){
        if(gen_size==0){
            int new_gen_size =  (int)( (this.max-this.min)/this.precision);
            this.max_gen = new_gen_size;
            System.out.println("Gen size max value: " + new_gen_size);
            String s = Integer.toBinaryString(new_gen_size);
            System.out.println("Gen size bin: " + s);
            System.out.println("Gen size bin length: " + s.length());
            this.gen_size = s.length();
        }
    }

    public static int[] withoutReturn(int k ) {
        int[] wynik = new int[k];
        int[] liczby = new int[k];
        for (int j = 0; j < liczby.length; j++) {
            liczby[j] = j + 1;
        }
        for (int i = 0; i < wynik.length; i++) {
            int l = (int) Math.floor(Math.random() * k);
            wynik[i] = liczby[l];
            liczby[l] = liczby[k - 1];
            k--;
        }
        return wynik;
    }
//    //k=10/5
//    //n=4 (1,4)
//    public int[] withoutReturn(int k , int n) {
//        int[] wynik = new int[k];
//
//        int[] liczby = new int[n];
//        for (int j = 0; j < liczby.length; j++) {
//            liczby[j] = j + 1;
//        }
//        for (int i = 0; i < wynik.length; i++) {
////            double rnd = Math.random() * n;
//            double rnd = randomMinMax(0,n-1);
//            int l = (int) rnd ;
////            System.out.println("(" + i + ")n: " + rnd);
////            System.out.println("(" + i + ")rnd: " + rnd);
////            System.out.println("(" + i + ")l: " + l);
//            wynik[i] = liczby[l];
////            liczby[l] = liczby[n - 1];
////            n--;
//        }
//        return wynik;
//    }

    private String generateSpecimen(int bin_length) {
        String specimen = "";
        int bit = 0;
        for (int i = 0; i < bin_length; i++) {
            bit = (int) (Math.random() * ((1) + 1));
            specimen = specimen + Integer.toString(bit);
        };
        return specimen;
    }



    private String generateSpecimen() {
        String specimen = "";
//        int bit = 0;
//        for (int i = 0; i < gen_size; i++) {
//            bit = (int) (Math.random() * ((1) + 1));
            int speci = randomMinMax(0,max_gen);
//            specimen = specimen + Integer.toString(bit);
//        };
        return int2bin(speci);
    }

    private String maxGen(int max_gen) {
        String max_gen_bin = "";
        for (int i = 0; i < max_gen; i++) {
            max_gen_bin = max_gen_bin + "1";
        };
        return max_gen_bin;
    }
    private int randomMinMax(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private int generateSpecimen(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private int getmaxSpecimen(int max_gen_size) {
        return (int) (Math.random() * ((bin2int(maxGen(max_gen_size))) + 1));
    }

    private int randomMax(int max_gen_size) {
        return (int) (Math.random() * ((bin2int(maxGen(max_gen_size))) + 1));
    }

    private int bin2int(String bin) {
        return Integer.parseInt(bin, 2);
    }

    private String int2bin(int nr) {
        if(Integer.valueOf(nr) != null){
            return String.format("%" + gen_size + "s", Integer.toBinaryString(nr)).replaceAll(" ", "0");
        }
        return "";
    }



    private String int2bin(String str) {
//        if(!str.equals(null) or !str.){
        if(str != null && str != ""){
            int nr = Integer.parseInt(str);
            return String.format("%" + gen_size + "s", Integer.toBinaryString(nr)).replaceAll(" ", "0");
        }
        return "";
    }

    private double function(double x){
        return x*x;
//        return x*x + x - 2.0;
//        return x*x*Math.sin(15*Math.PI*x)+1;
    }
    private double function(int x){
//        return x*x;
        return x*x+x-2;
//        return x*x*Math.sin(15*Math.PI*x)+1;
    }

    private double function1(int x){
        return x*x;
    }
    private double function2(int x){
        return x*x+x-2;
    }
    private double function3(int x){
//        x2*sin(15*π*x)+1
        return x*x*Math.sin(15*Math.PI*x)+1;
    }

    private String toX(String x){
        String x_prec = Double.toString((Double.parseDouble(x)*precision) + min );
        return x_prec;
    }

    private double toXdouble(String x){
        double x_prec = (Double.parseDouble(x)*precision) + (double)min ;
        return x_prec;
    }

//    private int[] generateRandomArray(int min, int max, int size){
//        List<Integer> numbers = new ArrayList<Integer>();
////                ArrayList<Integer> numbers = new ArrayList<Integer>();
//
//        while (numbers.size() < size) {
//            int random = randomMinMax(min, max);
//
//            if (!numbers.contains(random)) {
//                numbers.add(random);
//            }
//        }
//
//        return toIntArray(numbers);
//    }

    public int[] randomArrayUnique(int size, int min,int max) { //, boolean sorted
        int[] wynik = new int[size];
        if (size > max) {
            System.out
                    .println("size nie moze byc wieksza od max");
        } else {
            int[] liczby = new int[max];
            for (int j = 0; j < liczby.length; j++) {
                liczby[j] = j + 1;
            }
            for (int i = 0; i < wynik.length; i++) {
                int l =  randomMinMax(min,max-1);
                wynik[i] = liczby[l];
                liczby[l] = liczby[max - 1];
                max--;
            }
//            if (sorted) {
//                Arrays.sort(wynik);
//            }
        }
        return wynik;
    }

    public int[] randomArrayUnique(int size,int max){
        return randomArrayUnique(size,0,max);
    }

    public int[] randomArray(int size, int min, int max) { //, boolean sorted
        int[] wynik = new int[size];
            for (int i = 0; i < wynik.length; i++) {
                int l =  randomMinMax(min,max);
                wynik[i] = l;
            }
        return wynik;
    }

    public int[] randomArray(int size, int max){
        return randomArray(size,0,max);
    }

    public int[] toIntArray(List<Integer> intList){
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }

    public void plotChart(){
    }

}