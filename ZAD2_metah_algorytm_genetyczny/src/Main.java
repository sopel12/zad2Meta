public class Main {

    static int  gen_size = 5;
    static int Min = 0;
    static int Max = 31;
    static int ran = Min + (int) (Math.random() * ((Max - Min) + 1));

    public static void main(String[] args) {
//            Population pop = new Population(20,0.05,0.08,0,-1,0,0.01);
            Population pop = new Population(4,0.05,0.08,0,0,31,1.00);
//        for (int i = 0; i < 10; i++) {

//            pop.runAG();
            pop.runAG(200);
//            pop.printPopulation();
//        }
    }

}
