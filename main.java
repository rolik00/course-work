import java.awt.event.*;
import javax.swing.*;

public class Main {
    private JFrame frame;
    public Main() {
        frame = new JFrame("Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        /*JButton ex = new JButton("Exit");
        ex.setSize(100, 40);
        ex.setLocation(50, 200);*/

        JButton btnbss = new JButton("Basis   >");
        btnbss.setSize(200, 40);
        btnbss.setLocation(50, 80);
        btnbss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = createWindow("Basis");
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
                JFrame str = createWindow("Sorts");
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
                JFrame grp = createWindow("Graphs");
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
                JFrame dts = createWindow("Data structures");
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
                JFrame alg = createWindow("Algorithmic paradigms");
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
                JFrame tst = createWindow("Control test");
                frame.setVisible(false);
                tst.setVisible(true);
            }
        });
        frame.add(btntst);

        /*ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/
        //frame.add(ex);

        frame.setVisible(true);
    }
    private JFrame createWindow(String title) {
        JFrame window = new JFrame(title);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        window.add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                frame.setVisible(true);
            }
        });
        window.add(btnback);

        return window;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
