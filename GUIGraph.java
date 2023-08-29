package cnn;

import javax.swing.*;
import java.awt.*;

public class GUIGraph extends JFrame{
    String filename;
    public GUIGraph(String filename) {
        // TODO Auto-generated constructor stub
        setSize(600,440);
        setVisible(true);
        this.filename=filename;
    }
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        Image img=getToolkit().getImage(filename);//获取Image对象加载图像
        int h=img.getHeight(this);//获取图像高度
        int w=img.getWidth(this);//获取图像宽度
//        g.drawImage(img, 200, 100, this);//原图
//        g.drawImage(img,200,80,w/2,h/2,this);//缩小一半
//        g.drawImage(img, 280,80,w*2,h/3,this);
        g.drawImage(img, 100,80,w*10,h*10,this);
    }
//    public  void showImage(File file) {
//        new test("" + file);
//    }

}
