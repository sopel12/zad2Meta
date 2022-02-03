import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static int  gen_size = 5;
    static int Min = 0;
    static int Max = 31;
    static int ran = Min + (int) (Math.random() * ((Max - Min) + 1));

    static ArrayList<Integer> pops_size = new ArrayList<>();
    static ArrayList<String> func_name = new ArrayList<>();

    public static void main(String[] args) throws IOException {


        pops_size.add(10);
//        pops_size.add(20);
//        pops_size.add(30);
//        pops_size.add(50);
//        pops_size.add(100);
        String fn1 = "f(x)=x2 +x-2";
        String fn2 = "f(x)=x2*sin(15*π*x)+1";

//            Population pop = new Population(20,0.05,0.08,0,-1,0,0.01);
        int f_number = 1 ;//f(x)=x2 +x-2 w przedziale [-1,0]
//        int f_number = 2 ;//f(x)=x2*sin(15*π*x)+1 w przedziale [-1,2]

        for (int pop_size  : pops_size){
            Population pop = new Population(pop_size,0.05,0.08,0,-1,0,0.01, 1);
            pop.runAG(100);
            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
            line_chart_dataset = Population.line_chart_dataset;
            String name = "AG rozmiar populacji " + pop_size + " dla pierwszej funkci" ;
            JFreeChart lineChartObject = ChartFactory.createLineChart(
                    name,"iteration",
                    "value",
                    line_chart_dataset, PlotOrientation.VERTICAL,
                    true,true,false);

            int width = 800;    /* Width of the image */
            int height = 480;   /* Height of the image */
            File lineChart = new File( name + ".jpeg" );
            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);


        }

//
//        for (int pop_size  : pops_size){
//            Population pop = new Population(pop_size,0.05,0.08,0,-1,2,0.01, 2);
//            pop.runAG(100);
//            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
//            line_chart_dataset = Population.line_chart_dataset;
//            String name = "AG rozmiar populacji " + pop_size + " dla drugiej funkci" ;
//            JFreeChart lineChartObject = ChartFactory.createLineChart(
//                    name,"iteration",
//                    "value",
//                    line_chart_dataset, PlotOrientation.VERTICAL,
//                    true,true,false);
//
//            int width = 800;    /* Width of the image */
//            int height = 480;   /* Height of the image */
//            File lineChart = new File( name + ".jpeg" );
//            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
//
//
//        }
    }

}
