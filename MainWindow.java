import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    private JFrame frame;
    private int btn_width, btn_height, locx1, locx2, locx3, locy1, locy2;
    public static Color main_color = new Color(67,21,113), title_color = new Color(233, 178, 127);
    public enum Topic {Basis, Sorts, Data_structures, Graphs, Algorithmic_paradigms, Control_Test}

    public JFrame create_main_window()
    {
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
        frame.getContentPane().setBackground(main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        JButton btnbss = create_main_button(Topic.Basis);
        frame.add(btnbss);

        JButton btnstr = create_main_button(Topic.Sorts);
        frame.add(btnstr);

        JButton btndts = create_main_button(Topic.Data_structures);
        frame.add(btndts);

        JButton btngrp = create_main_button(Topic.Graphs);
        frame.add(btngrp);

        JButton btnalg = create_main_button(Topic.Algorithmic_paradigms);
        frame.add(btnalg);

        // потом засунем в какую-нибудь функцию
        JButton btntst = new JButton("Контрольный тест");
        BufferedImage oImage = null;
        File file = new File("images/тест.jpg");
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
                TestWindow tw = new TestWindow();
                JFrame tst = tw.create_test_window(frame, Topic.Control_Test);
                frame.setVisible(false);
                tst.setVisible(true);
            }
        });
        frame.add(btntst);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("≡");
        menu.setFont(new Font("Verdana", Font.PLAIN, 16));
        JMenuItem menustatistic = new JMenuItem("Посмотреть статистику");
        menustatistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatisticsWindow sw = new StatisticsWindow();
                frame.setVisible(false);
                JFrame statistic = sw.create_statistic_window(frame);
                statistic.setVisible(true);
            }
        });
        JMenuItem menuexit = new JMenuItem("Выход");
        menuexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.setBackground(main_color);
        menu.add(menustatistic);
        menu.add(menuexit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        return frame;
    }

    private JButton create_main_button(Topic topic)
    {
        File file = null;
        String button_name = "";
        BufferedImage oImage = null;
        if (topic == Topic.Basis) {
            file = new File("images/основы.png");
            button_name = "Основы";
        }
        else if (topic == Topic.Sorts) {
            file = new File("images/сортировки.png");
            button_name = "Сортировки";
        }
        else if (topic == Topic.Data_structures) {
            file = new File ("images/структурыданных.jpg");
            button_name = "Структуры данных";
        }
        else if (topic == Topic.Graphs) {
            file = new File ("images/графы.jpg");
            button_name = "Графы";
        }
        else if (topic == Topic.Algorithmic_paradigms) {
            file = new File ("images/алгпарадигмы.png");
            button_name = "Алгоритмические парадигмы";
        }
        try {
            oImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JButton button = new JButton(button_name);
        button.setSize(btn_width, btn_height);
        if (topic == Topic.Basis) button.setLocation(locx1, locy1);
        else if (topic == Topic.Sorts) button.setLocation(locx2, locy1);
        else if (topic == Topic.Data_structures) button.setLocation(locx3, locy1);
        else if (topic == Topic.Graphs) button.setLocation(locx1, locy2);
        else if (topic == Topic.Algorithmic_paradigms) button.setLocation(locx2, locy2);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TheoryWindow thw = new TheoryWindow();
                JFrame bss = thw.create_theory_window(topic, frame);
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
}
