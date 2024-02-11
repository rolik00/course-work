import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JTextArea text;
    public Main() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon o = new ImageIcon("images/основы.svg");g
        ImageIcon s = new ImageIcon("images/сортировки.png");
        ImageIcon d = new ImageIcon("images/структурыданных.jpg");
        ImageIcon g = new ImageIcon("images/графы.jpg");
        ImageIcon a = new ImageIcon("images/алгпарадигмы.png");
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        int sizex = (int)(0.25*width);
        int sizey = (int)(0.34*height);
        System.out.println(sizex);
        System.out.println(sizey);
        int locx1=(int)(0.0625*width);
        int locx2=(int)(locx1+sizex+0.5*(width-2*locx1-3*sizex));
        int locx3=(int)(0.9375*width-sizex);
        int locy1=(int)(0.2*height);
        int locy2=(int)(sizey+locy1+0.5*(height-locy1-2*sizey));

        frame = new JFrame("Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnbss = new JButton(o);
        btnbss.setText("Основы   >");
        btnbss.setHorizontalTextPosition(JButton.CENTER);
        btnbss.setVerticalTextPosition(JButton.CENTER);
        btnbss.setSize(sizex, sizey);
        btnbss.setLocation(locx1, locy1);
        btnbss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = create_theory_window("Basis");
                frame.setVisible(false);
                bss.setVisible(true);
            }
        });
        frame.add(btnbss);

        JButton btnstr = new JButton(s);
        btnstr.setText("Сортировки   >");
        btnstr.setHorizontalTextPosition(JButton.CENTER);
        btnstr.setVerticalTextPosition(JButton.CENTER);
        btnstr.setSize(sizex, sizey);
        btnstr.setLocation(locx2, locy1);
        btnstr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame str = create_theory_window("Sorts");
                frame.setVisible(false);
                str.setVisible(true);
            }
        });
        frame.add(btnstr);


        JButton btndts = new JButton(d);
        btndts.setText("Структуры данных   >");
        btndts.setHorizontalTextPosition(JButton.CENTER);
        btndts.setVerticalTextPosition(JButton.CENTER);
        btndts.setSize(sizex, sizey);
        btndts.setLocation(locx3, locy1);
        btndts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame dts = create_theory_window("Data structures");
                frame.setVisible(false);
                dts.setVisible(true);
            }
        });
        frame.add(btndts);

        JButton btngrp = new JButton(g);
        btngrp.setText("Графы   >");
        btngrp.setHorizontalTextPosition(JButton.CENTER);
        btngrp.setVerticalTextPosition(JButton.CENTER);
        btngrp.setSize(sizex, sizey);
        btngrp.setLocation(locx1, locy2);
        btngrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame grp = create_theory_window("Graphs");
                frame.setVisible(false);
                grp.setVisible(true);
            }
        });
        frame.add(btngrp);
        JButton btnalg = new JButton(a);
        btnalg.setText("Алгоритмические парадигмы   >");
        btnalg.setHorizontalTextPosition(JButton.CENTER);
        btnalg.setVerticalTextPosition(JButton.CENTER);
        btnalg.setSize(sizex, sizey);
        btnalg.setLocation(locx2, locy2);
        btnalg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame alg = create_theory_window("Algorithmic paradigms");
                frame.setVisible(false);
                alg.setVisible(true);
            }
        });
        frame.add(btnalg);

        JButton btntst = new JButton("Контрольный тест   >");
        btntst.setSize(sizex, sizey);
        btntst.setLocation(locx3, locy2);
        btntst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // здесь потом будет другая функция, так как это окно будет создавать как и другие тесты, а для них будет отдельная функция создания окна, и надо еще не забыть про счетчик баллов
                JFrame tst = create_theory_window("Control test");
                frame.setVisible(false);
                tst.setVisible(true);
            }
        });
        frame.add(btntst);

        JButton ex = new JButton("Выход");
        ex.setSize(200, 40);
        ex.setLocation((int)width-220, 20);
        ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.add(ex);

        frame.setVisible(true);
    }
    private JFrame create_theory_window(String title) {
        JFrame window = new JFrame(title);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setLayout(null);

        String filepath;
        if (title == "Basis") filepath = "theories/basis.txt";
        else if (title == "Sorts") filepath = "theories/sorts.txt";
        else if (title == "Graphs") filepath = "theories/graphs.txt";
        else if (title == "Data structures") filepath = "theories/data structures.txt";
        else if (title == "Algorithmic paradigms") filepath = "theories/algoritmic paradigms.txt";
        else filepath = "hmm.txt";

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
        JMenuItem menuback = new JMenuItem("Back to the main window");
        JMenuItem menuexit = new JMenuItem("Exit the programm");
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

        JButton btntest = new JButton("Take test");
        btntest.setSize(300, 40);
        btntest.setLocation(625, 725);
        //button processing
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
        text.setCaretPosition(0);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}