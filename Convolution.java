package cnn;

public interface Convolution {

    double[][][] go(double[][][] inputImgTemp);

    double[][][] turnBack(double[][][] outImgTemp_t);
}
