import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainWindow {
    private JFrame frame;
    private int btn_width, btn_height, locx1, locx2, locx3, locy1, locy2;

    public MainWindow() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        btn_width = (int)(0.25*width);
        btn_height = (int)(0.3*height);
        locx1=(int)(0.0625*width);
        locx2=(int)(locx1+btn_width+0.5*(width-2*locx1-3*btn_width));
        locx3=(int)(0.9375*width-btn_width);
        locy1=(int)(0.1*height);
        locy2=(int)(btn_height+locy1+0.5*(height-locy1-2*btn_height));

        frame = new JFrame("Меню");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnbss = create_main_button("Basis");
        frame.add(btnbss);

        JButton btnstr = create_main_button("Sorts");
        frame.add(btnstr);

        JButton btndts = create_main_button("Data structures");
        frame.add(btndts);

        JButton btngrp = create_main_button("Graphs");
        frame.add(btngrp);

        JButton btnalg = create_main_button("Algorithmic paradigms");
        frame.add(btnalg);

        // потом засунем в какую-нибудь функцию
        JButton btntst = new JButton("Контрольный тест   >");
        BufferedImage oImage = null;
        File file = new File("images/тест.png");
        try {
            oImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image scaledImage = oImage.getScaledInstance(btn_width, btn_height - 25, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        btntst.setIcon(imageIcon);
        btntst.setVerticalTextPosition(SwingConstants.BOTTOM);
        btntst.setHorizontalTextPosition(SwingConstants.CENTER);
        btntst.setSize(btn_width, btn_height);
        btntst.setLocation(locx3, locy2);
        btntst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestWindow w = new TestWindow();
                w.create_test_window("Control test",frame);
            }
        });
        frame.add(btntst);

        JButton ex = exit();
        frame.add(ex);

        frame.setVisible(true);
    }

    private JButton create_main_button(String title)
    {
        File file = null;
        BufferedImage oImage = null;
        if (title == "Basis") file = new File("images/основы.png");
        else if (title == "Sorts") file = new File("images/сортировки.png");
        else if (title == "Graphs") file = new File ("images/графы.png");
        else if (title == "Data structures") file = new File ("images/структурыданных.jpg");
        else if (title == "Algorithmic paradigms") file = new File ("images/алгпарадигмы.png");
        //else if (title == "Control test") file = new File ("images/тест.png");
        try {
            oImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JButton button = new JButton(title);
        button.setSize(btn_width, btn_height);
        if (title == "Basis") button.setLocation(locx1, locy1);
        else if (title == "Sorts") button.setLocation(locx2, locy1);
        else if (title == "Data structures") button.setLocation(locx3, locy1);
        else if (title == "Graphs") button.setLocation(locx1, locy2);
        else if (title == "Algorithmic paradigms") button.setLocation(locx2, locy2);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = null;
                try {
                    TheoryWindow bss_window = new TheoryWindow();
                    bss = bss_window.create_theory_window(title,frame);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setVisible(false);
                bss.setVisible(true);
            }
        });
        Image scaledImage = oImage.getScaledInstance(btn_width, btn_height - 25, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        button.setIcon(imageIcon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }
    private JButton exit()
    {
        File file = new File("images/выход.jpg");
        BufferedImage oImage = null;
        try {
            oImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JButton ex = new JButton("Выход");
        ex.setSize(100, 40);
        ex.setLocation(725, 725);
        ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Image scaledImage = oImage.getScaledInstance(100, 15, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        ex.setIcon(imageIcon);
        ex.setVerticalTextPosition(SwingConstants.BOTTOM);
        ex.setHorizontalTextPosition(SwingConstants.CENTER);
        return ex;
    }
}
