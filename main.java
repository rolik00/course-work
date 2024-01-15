import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Main {
    private JFrame frame;
    private JTextArea text;
    public Main() {
        frame = new JFrame("Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnbss = new JButton("Basis   >");
        btnbss.setSize(200, 40);
        btnbss.setLocation(50, 80);
        btnbss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = create_theory_window("Basis");
                frame.setVisible(false);
                bss.setVisible(true);
            }
        });
        frame.add(btnbss);

        JButton btnstr = new JButton("Sorts   >");
        btnstr.setSize(200, 40);
        btnstr.setLocation(50, 140);
        btnstr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame str = create_theory_window("Sorts");
                frame.setVisible(false);
                str.setVisible(true);
            }
        });
        frame.add(btnstr);

        JButton btngrp = new JButton("Graphs   >");
        btngrp.setSize(200, 40);
        btngrp.setLocation(50, 200);
        btngrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame grp = create_theory_window("Graphs");
                frame.setVisible(false);
                grp.setVisible(true);
            }
        });
        frame.add(btngrp);

        JButton btndts = new JButton("Data structures   >");
        btndts.setSize(200, 40);
        btndts.setLocation(50, 260);
        btndts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame dts = create_theory_window("Data structures");
                frame.setVisible(false);
                dts.setVisible(true);
            }
        });
        frame.add(btndts);

        JButton btnalg = new JButton("Algorithmic paradigms   >");
        btnalg.setSize(200, 40);
        btnalg.setLocation(50, 320);
        btnalg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame alg = create_theory_window("Algorithmic paradigms");
                frame.setVisible(false);
                alg.setVisible(true);
            }
        });
        frame.add(btnalg);

        JButton btntst = new JButton("Control test   >");
        btntst.setSize(200, 40);
        btntst.setLocation(50, 380);
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
        ex.setLocation(50, 440);
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
        if (title == "Basis") filepath = "D:\\java\\firstpr\\basis.txt";
        else if (title == "Sorts") filepath = "D:\\java\\firstpr\\sorts.txt";
        else filepath = "D:\\java\\firstpr\\hmm.txt";

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

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(0, 0);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                frame.setVisible(true);
            }
        });
        window.add(btnback);

        JButton btntest = new JButton("Take test");
        btntest.setSize(300, 40);
        btntest.setLocation(625, 725);
        //button processing
        window.add(btntest);

        return window;
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
