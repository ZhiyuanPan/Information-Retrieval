public class PageRank {
    double[][] matrix = {
            {0, 1, 1},
            {0, 0, 1},
            {0, 1, 0}
    };
    double[] X = {0.1, 0.1, 0.1};
    double Teleprotation_Rate = 0.1;

    public void page_rank() {
        double[] temp_X = new double[3];
        Probability_Matrix();
        Teleporting_Matrix();

        int Round = 1;
        while (true) {
            X = XP();
            System.out.print("Round"+Round+":");
            if (Round >= 2 && cheak(temp_X, X) == 1) {
                printX();
                break;
            }
            printX();
            Round++;
            for (int i = 0; i < temp_X.length; i++) {
                temp_X[i] = X[i];
            }
        }
    }

    public void Probability_Matrix() {
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    sum++;
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] /= sum;
            }
        }
        System.out.println("Transition probability matrix:");
        print();
    }

    public void Teleporting_Matrix() {
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    sum++;
                }
            }
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = Teleprotation_Rate / matrix.length
                        + matrix[i][j] * (1 - Teleprotation_Rate);
            }
        }
        System.out.println("Transition matrix with teleporting:");
        print();
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(Double.valueOf(String.format("%.2f", matrix[i][j])) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int cheak(double[] X1, double[] X2) {
        int ifSame = 1;
        for (int i = 0; i < X1.length; i++) {
            if (!Double.toString(X1[i]).equals(Double.toString(X2[i]))) {
                ifSame = 0;
            }
        }
        return ifSame;
    }

    public double[] XP() {
        for (int i = 0; i < X.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += X[i] * matrix[i][j];
            }
            X[i] = sum;
        }
        return X;
    }

    public void printX() {
        for (int i = 0; i < X.length; i++) {
            System.out.print(X[i] + "  ");
        }
        System.out.println();
    }
}
