package cnn;

public class Residual {
    //前向
    double[][][] inputImage;

    double[][][] canImage;
    double[][][] outImage;

    //反向
    double[][][] inputImage_t;
    double[][][] canImage_t;
    double[][][] outImage_t;

    int number;
    int channel;
    int height;
    int width;

    int stride;
    double weight;

    public Residual(int number, int channel, int height, int width, int stride, double weight) {
        this.number = number;
        this.channel = channel;
        this.height = height;
        this.width = width;
        this.stride = stride;
        this.weight = weight;

        this.inputImage = new double[number][height][height];
        this.inputImage_t = new double[number][height][height];
        ResidualMethods.setResWeight(inputImage_t);

        this.canImage = new double[channel][height][height];
        this.canImage_t = new double[channel][height][height];
        ResidualMethods.setResWeight(canImage_t);

        this.outImage = new double[channel][height/2][width/2];
        this.outImage_t = new double[channel][height/2][width/2];
        ResidualMethods.setResWeight(outImage_t);
    }

    public double[][][] go(double[][][] inputImage) {
        this.inputImage = inputImage;

        ResidualMethods.setCanImage(canImage, inputImage, channel);//残差层中分化图像数据

        outImage = new double[canImage.length][canImage[0].length/2][canImage[0][0].length/2];
        ResidualMethods.setOutImage(outImage, canImage, stride);

        return outImage;
    }

}
