package cnn;

public interface Pool {
    double[][][] go(double[][][] inputImgTemp);
    double[][][] turnBack(double[][][] outImgTemp_t);
}
