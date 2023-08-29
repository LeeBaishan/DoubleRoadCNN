package cnn;

public class NetWork {
    //第一大层卷积
    ConLayer conLayer1_1;
    ConLayer conLayer1_2;
    PoolLayer poolLayer1_1;
    PoolLayer poolLayer1_2;
    Residual residual1;

    //第二大层卷积
    ConLayer conLayer2_1;
    ConLayer conLayer2_2;
    PoolLayer poolLayer2_1;
    PoolLayer poolLayer2_2;
    Residual residual2;
    Residual residual2_1;
    Residual residual2_2;
    BPANN bpann;
    double studyRate;

    public NetWork(double studyRate) {
        this.studyRate = studyRate;
        conLayer1_1 = new ConLayer(32, 1, 5, 5, 1, 1, studyRate);
        conLayer1_2 = new ConLayer(32, 1, 5, 5, 1, 1, studyRate);
        poolLayer1_1 = new PoolLayer(2, 2, 1);
        poolLayer1_2 = new PoolLayer(2, 2, 1);
        residual1 = new Residual(1,32,28, 28, 2,0.5);
        conLayer2_1 = new ConLayer(64, 32, 5, 5, 1, 1, studyRate);
        conLayer2_2 = new ConLayer(64, 32, 5, 5, 1, 1, studyRate);
        poolLayer2_1 = new PoolLayer(2, 2, 1);
        poolLayer2_2 = new PoolLayer(2, 2, 1);
        residual2 = new Residual(32, 64, 14, 14, 2,0.5);
        residual2_1 = new Residual(32, 64, 14, 14, 2,0.5);
        residual2_2 = new Residual(32, 64, 14, 14, 2,0.5);
        bpann = new BPANN(3136, 1024, 10, studyRate);
    }

    public double[] forward(double[][][] pic1, double[][][] pic2) {

        double[][][] res1 = residual1.go(pic1);
        double[][][] con1_1 = conLayer1_1.go(pic1);
        double[][][] con1_2 = conLayer1_2.go(pic2);
        double[][][] pool1_1 = poolLayer1_1.go(con1_1);
        double[][][] pool1_2 = poolLayer1_2.go(con1_2);


        //conLayer1,2表示对应两个两个通道的卷积信息；
        //res2-1,res2-2表示对应两个通道的残差信息；
        //pool2-1,pool2-2表示对应两个通道的中间层信息；
        double[][][] res2 = residual2.go(res1);
        double[][][] res2_1 = residual2.go(pool1_1);
        double[][][] res2_2 = residual2.go(pool1_2);
        double[][][] con2_1 = conLayer2_1.go(pool1_2);
        double[][][] con2_2 = conLayer2_2.go(pool1_1);
        double[][][] pool2_1 = poolLayer2_1.go(con2_1);
        double[][][] pool2_2 = poolLayer2_2.go(con2_2);
        //temp3存储慢树突聚合信息
        double[][][] temp3 = Tool.getImage(res2, pool2_1, pool2_2, res2_1, res2_2);

        return bpann.go(temp3);
    }

    public void back(int e, int expect) {
        double[][][] pool2_total_t = bpann.turnBack(e, expect);
        pool2_total_t = Tool.getImage(pool2_total_t, 2.8);
        double[][][] con2_1_t = poolLayer2_1.turnBack(pool2_total_t);
        double[][][] con2_2_t = poolLayer2_1.turnBack(pool2_total_t);

        double[][][] temp = Tool.getImage(con2_1_t, con2_2_t);
        double[][][] pool1_1_t = conLayer2_2.turnBack(con2_2_t);
        double[][][] pool1_2_t = conLayer2_1.turnBack(con2_1_t);

        double[][][] con1_1_t = poolLayer1_1.turnBack(pool1_1_t);
        double[][][] con1_2_t = poolLayer1_1.turnBack(pool1_2_t);

        conLayer1_1.turnBack(con1_1_t);
        conLayer1_2.turnBack(con1_2_t);
    }
}
