public class Main {

    public static void main(String[] args) {
        int[][] d = {{0,  8, 7, 4,  6, 4}
                , {8,  0, 5,  7, 11, 5}
                , {7,  5, 0,  9,  6, 7}
                , {4,  7, 9,  0,  5, 6}
                , {6, 11, 6,  5,  0, 3}
                , {4,  5, 7,  6,  3, 0 }
                , {4,  5, 7,  6,  3, 0 }};

        for (int i = 0; i < d.length; i++)
            for (int j = 0; j < d[0].length; j++)
                System.out.println("arr[" + i + "][" + j + "] = "
                        + d[i][j]);
//asd
    }

}
