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
        //首先创建probability矩阵和teleportation矩阵。
        Probability_Matrix();
        Teleporting_Matrix();

        int Round = 1;
        while (true) {//进入内部含有条件判断的循环
            X = XP();
            //循环每次将向量X乘以P，得到新的X，并与上一轮的X比较
            System.out.print("Round"+Round+":");
            //如果此时的X与上一轮的X相同，则可以退出循环
            if (Round >= 2 && cheak(temp_X, X) == 1) {
                printX();
                break;
            }
            printX();
            Round++;
            for (int i = 0; i < temp_X.length; i++) {
                temp_X[i] = X[i];//建立了一个临时的向量数据，每次与X进行比较
            }
        }
    }

    public void Probability_Matrix() {//进行Probability矩阵的计算
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {//Probability矩阵中我们首先要统计含有1的个数
                if (matrix[i][j] == 1) {
                    sum++;
                }
            }//使用双层循环，第一次内部循环统计含有1的个数，
            // 第二个循环将每个“1”归一化，即除以含有1的个数
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] /= sum;
            }
        }
        System.out.println("Transition probability matrix:");
        print();
    }

    public void Teleporting_Matrix() {//对带Teleporting的矩阵进行计算
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {//首先统计非0元素的个数
                if (matrix[i][j] != 0) {
                    sum++;
                }
            }
            //将每个元素本身的值乘以（1-teleprotation），再加上teleportation平均分配到行中每一个元素的值，
            // 即可得到带teleportation的矩阵。
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
        //使用列向量X对矩阵中的每一行进行点乘，得到列中每行元素
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
