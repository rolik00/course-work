import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class Main {
    private JFrame frame;
    private JTextArea text;
    private int btn_width, btn_height, locx1, locx2, locx3, locy1, locy2;

    public Main() {

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

        JButton btngrp = create_main_button("Graphs");
        frame.add(btngrp);

        JButton btndts = create_main_button("Data structures");
        frame.add(btndts);

        JButton btnalg = create_main_button("Algorithmic paradigms");
        frame.add(btnalg);

        // потом засунем в какую-нибудь функцию
        JButton btntst = new JButton("Контрольный тест   >");
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
                JFrame tst = create_test_window("Control test");
                frame.setVisible(false);
                tst.setVisible(true);
            }
        });
        frame.add(btntst);

        JButton ex = exit();
        frame.add(ex);

        frame.setVisible(true);
    }

    private JFrame create_theory_window(String title) {
        JFrame window = new JFrame(title);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setLayout(null);

        String filepath;
        if (title == "Basis") filepath = "D:\\java\\firstpr\\theories\\basis.txt";
        else if (title == "Sorts") filepath = "D:\\java\\firstpr\\theories\\sorts.txt";
        else if (title == "Graphs") filepath = "D:\\java\\firstpr\\theories\\graphs.txt";
        else if (title == "Data structures") filepath = "D:\\java\\firstpr\\theories\\data structures.txt";
        else if (title == "Algorithmic paradigms") filepath = "D:\\java\\firstpr\\theories\\algoritmic paradigms.txt";
        else filepath = "D:\\java\\firstpr\\theories\\hmm.txt";

        text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setSize(1200, 600);
        scroll.setLocation(175, 75);
        window.getContentPane().add(scroll);

        try {
            readFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("<>"); // как-нибудь потом назовем это
        JMenuItem menuback = new JMenuItem("Назад на главное окно");
        JMenuItem menuexit = new JMenuItem("Выход из программы");
        menuback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                frame.setVisible(true);
            }
        });
        menuexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuback);
        menu.add(menuexit);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);

        JButton btntest = new JButton("Пройти тест");
        btntest.setSize(300, 40);
        btntest.setLocation(625, 725);
        btntest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame test = create_test_window(title);
                window.setVisible(false);
                test.setVisible(true);
            }
        });
        window.add(btntest);

        return window;
    }

    private JFrame create_test_window(String title) {
        JFrame wtest = new JFrame(title);
        wtest.setExtendedState(JFrame.MAXIMIZED_BOTH);
        wtest.getContentPane().setLayout(null);
        /*JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setValue(50);
        bar.setPreferredSize(new Dimension(100, 8));
        wtest.getContentPane().add(bar);*/
        //wtest.add(bar);
        text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setSize(1200, 300);
        scroll.setLocation(175, 75);
        wtest.getContentPane().add(scroll);
        if (title == "Basis")
            text.append("Задача коммивояжера является примером");
        else if (title == "Sorts")
            text.append("Задача коммивояжера является примером");
        else if (title == "Graphs")
            text.append("Задача коммивояжера является примером");
        else if (title == "Data structures")
            text.append("Задача коммивояжера является примером");
        else if (title == "Algorithmic paradigms")
            text.append("Задача коммивояжера является примером");
        else if (title == "Control test")
            text.append("The control test will be here!");
        text.setCaretPosition(0);

        JButton ex = new JButton("Выход");
        ex.setSize(100, 40);
        ex.setLocation(725, 725);
        ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wtest.setVisible(false);
                frame.setVisible(true);
            }
        });
        wtest.add(ex);
        return wtest;
    }

    private void readFile(String filePath) throws IOException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line + "\n");
            }
            text.setCaretPosition(0);
        } finally
        {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private JButton create_main_button(String title)
    {
        File file = null;
        BufferedImage oImage = null;
        if (title == "Basis") file = new File("images/основы.png");
        else if (title == "Sorts") file = new File("images/сортировки.png");
        else if (title == "Graphs") file = new File ("images/графы.jpg");
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
        else if (title == "Graphs") button.setLocation(locx3, locy1);
        else if (title == "Data structures") button.setLocation(locx1, locy2);
        else if (title == "Algorithmic paradigms") button.setLocation(locx2, locy2);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = create_theory_window(title);
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
        File file = new File("images/выход.png");
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
