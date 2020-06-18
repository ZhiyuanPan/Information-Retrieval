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

            //H计算A，归一化A
            Calculate_A();
            vector_A = Vector_Initialize(vector_A);
            //用A计算H，归一化H
            Calculate_H();
            vector_H = Vector_Initialize(vector_H);
            //最后H和A向量都稳定下来，退出循环并打印信息。
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

    void Calculate_A() {//通过H向量来计算A向量，A向量中的每一个值为H向量的值乘以矩阵中的一列向量。
        for (int i = 0; i < vector_A.length; i++) {
            double sum = 0;
            for (int j = 0; j < vector_H.length; j++) {
                sum += (vector_H[i] * matrix[j][i]);
            }
            vector_A[i] = sum;
        }
    }

    void Calculate_H() {//通过A向量来计算H向量，H向量中的每一个值为A向量的值乘以矩阵中的一列向量。
        for (int i = 0; i < vector_H.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += vector_A[i] * matrix[i][j];
            }
            vector_H[i] = sum;
        }
    }

    double[] Vector_Initialize(double[] a) {//向量归一化
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
