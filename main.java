import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JTextArea text;
    public Main() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        int sizex = (int)(0.25*width);
        int sizey = (int)(0.34*height);
        int locx1=(int)(0.0625*width);
        int locx2=(int)(locx1+sizex+0.5*(width-2*locx1-3*sizex));
        int locx3=(int)(0.9375*width-sizex);
        int locy1=(int)(0.2*height);
        int locy2=(int)(sizey+locy1+0.5*(height-locy1-2*sizey));
        System.out.println(locx1+" "+locx2+" "+locx3);
        System.out.println(locy1+" "+locy2+' '+sizex+' '+sizey);

        frame = new JFrame("Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnbss = new JButton("Basis   >");
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

        JButton btnstr = new JButton("Sorts   >");
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


        JButton btndts = new JButton("Data structures   >");
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

        JButton btngrp = new JButton("Graphs   >");
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
        JButton btnalg = new JButton("Algorithmic paradigms   >");
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

        JButton btntst = new JButton("Control test   >");
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

        JButton ex = new JButton("Exit");
        ex.setSize(200, 40);
        ex.setLocation(0, 0);
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
        if (title == "Basis") filepath = "basis.txt";
        else if (title == "Sorts") filepath = "sorts.txt";
        else if (title == "Graphs") filepath = "graphs.txt";
        else if (title == "Data structures") filepath = "data structures.txt";
        else if (title == "Algorithmic paradigms") filepath = "algoritmic paradigms.txt";
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
                JFrame test = create_test_window("Test");
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
