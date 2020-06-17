public class Hub_Authority {
    double[][] matrix = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 1, 1, 0, 0, 0}
    };
    double[] vector_H = new double[matrix.length];
    double[] vector_A = new double[matrix.length];

    public void hub_authority() {
        int Round = 1;
        double[] temp_H = new double[vector_H.length];
        double[] temp_A = new double[vector_H.length];

        for (int j = 0; j < vector_H.length; j++) {
            vector_H[j] = (double) 1 / matrix.length;
        }

        while (true) {
            System.out.println("Round" + Round + ":");

            Calculate_A();
            vector_A = Vector_Initialize(vector_A);
            Calculate_H();
            vector_H = Vector_Initialize(vector_H);

            if (Round >= 2 && cheak(temp_H, vector_H) == 1 && cheak(temp_A, vector_A) == 1) {
                print();
                break;
            }

            print();
            Round++;
            for (int i = 0; i < temp_A.length; i++) {
                temp_A[i] = vector_A[i];
                temp_H[i] = vector_H[i];
            }
        }
    }

    void Calculate_A() {
        for (int i = 0; i < vector_A.length; i++) {
            double sum = 0;
            for (int j = 0; j < vector_H.length; j++) {
                sum += (vector_H[i] * matrix[i][j]);

            }
            vector_A[i] = sum;
        }
    }

    void Calculate_H() {
        for (int i = 0; i < vector_H.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += vector_A[i] * matrix[j][i];
            }
            vector_H[i] = sum;
        }
    }

    double[] Vector_Initialize(double[] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        for (int i = 0; i < a.length; i++) {
            a[i] /= sum;
        }
        return a;
    }

    void print() {
        System.out.print("H:");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(Double.valueOf(String.format("%.2f", vector_H[i])) + " ");
        }
        System.out.println();
        System.out.print("A:");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(Double.valueOf(String.format("%.2f", vector_A[i])) + " ");
        }
        System.out.println();
        System.out.println();
    }

    public int cheak(double[] X1, double[] X2) {
        int ifSame = 1;
        for (int i = 0; i < X1.length; i++) {
            if (!String.format("%.2f", vector_H[i]).equals(String.format("%.2f", vector_A[i]))) {
                ifSame = 0;
                break;
            }
        }
        return ifSame;
    }
}
