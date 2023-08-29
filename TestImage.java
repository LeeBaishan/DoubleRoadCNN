package cnn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class TestImage extends JFrame {
    private JPanel contentPane;
    NetWork netWork = Test.netWork;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestImage frame = new TestImage();

                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public TestImage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 440);
        contentPane = new JPanel();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel label = new JLabel("点击下方数字进行测试");
        label.setBounds(5, 5, 600, 30);
        label.setOpaque(true);//设置控件不透明
        label.setBackground(Color.cyan); //<span style="font-family:Verdana;">设置标间颜色</span>
        contentPane.add(label);


        for (int i = 0; i < 10; i++) {
            JLabel label1 = new JLabel("测试数字" + i);
            label1.setBounds(5, 40 + i * 35, 600, 30);
            label1.setOpaque(true);//设置控件不透明
            label1.setBackground(Color.white); //<span style="font-family:Verdana;">设置标间颜色</span>
            contentPane.add(label1);
            int finalI = i;
            label1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton()==e.BUTTON1) {
                        int number = (int)(Math.random()*100);
                        new GUIGraph("D:\\MrLee\\代码和实验室\\lab\\cnn\\MINST_test\\" + finalI + "\\" + finalI + "." + (number + 3600) + ".jpg");
                        double[] outTest = new double[0];
                        try {
                            outTest = netWork.forward(Tool.getGreyValue(/*存储数据的绝对路径*/
                                            "D:\\MrLee\\代码和实验室\\lab\\cnn\\MINST_test\\" + finalI + "\\" + finalI + "." + (number + 3600) + ".jpg"),
                                    Tool.getGreyValue(/*存储数据的绝对路径*/
                                            "D:\\MrLee\\代码和实验室\\lab\\cnn\\MINST_test\\" + finalI + "\\" + finalI + "." + (number + 3600) + ".jpg"));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        int value = Tool.getArraysMax(outTest);
                        String str = "预期输出为：" + finalI + "，   实际输出为: " + value;
                        label1.setText(str);
                    }
                }
            });
        }
    }

}
