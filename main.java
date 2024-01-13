import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {
    private Basis bss;
    private Sort str;
    private Graph grp;
    private D_Struct dts;
    private Algo alg;
    private Test tst;

    public Main() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("Choose the topic");
        text.setBounds(700, 30, 315, 30);
        //text.setFont(new Font("Serif", Font.PLAIN, 18));
        add(text);


        bss = new Basis(this);
        str = new Sort(this);
        grp = new Graph(this);
        dts = new D_Struct(this);
        alg = new Algo(this);
        tst = new Test(this);

        JButton btnbss = new JButton("Basis   >");
        btnbss.setSize(200, 40);
        btnbss.setLocation(50, 80);
        btnbss.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bss.setVisible(true);
            }
        });
        add(btnbss);

        JButton btnstr = new JButton("Sorts   >");
        btnstr.setSize(200, 40);
        btnstr.setLocation(50, 140);
        btnstr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                str.setVisible(true);
            }
        });
        add(btnstr);

        JButton btngrp = new JButton("Graphs   >");
        btngrp.setSize(200, 40);
        btngrp.setLocation(50, 200);
        btngrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                grp.setVisible(true);
            }
        });
        add(btngrp);

        JButton btndts = new JButton("Data structures   >");
        btndts.setSize(200, 40);
        btndts.setLocation(50, 260);
        btndts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dts.setVisible(true);
            }
        });
        add(btndts);

        JButton btnalg = new JButton("Algorithmic paradigms   >");
        btnalg.setSize(200, 40);
        btnalg.setLocation(50, 320);
        btnalg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                alg.setVisible(true);
            }
        });
        add(btnalg);

        JButton btntst = new JButton("Control test   >");
        btntst.setSize(200, 40);
        btntst.setLocation(50, 380);
        btntst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                tst.setVisible(true);
            }
        });
        add(btntst);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}

class Basis extends JFrame {
    private Main m;
    public Basis(Main m) {
        this.m = m;

        setTitle("Basis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}

class Sort extends JFrame {
    private Main m;
    public Sort(Main m) {
        this.m = m;

        setTitle("Sort");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}

class Graph extends JFrame {
    private Main m;
    public Graph(Main m) {
        this.m = m;

        setTitle("Graphs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}

class D_Struct extends JFrame {
    private Main m;
    public D_Struct(Main m) {
        this.m = m;

        setTitle("Data structures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}

class Algo extends JFrame {
    private Main m;
    public Algo(Main m) {
        this.m = m;

        setTitle("Algorithmic paradigms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JLabel text = new JLabel("you crossed to another window!");
        text.setBounds(700, 400, 315, 14);
        add(text);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}

class Test extends JFrame {
    private Main m;
    public Test(Main m) {
        this.m = m;

        setTitle("Control Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);

        JButton btnback = new JButton("Back");
        btnback.setSize(100, 40);
        btnback.setLocation(700, 675);
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.setVisible(true);
            }
        });
        add(btnback);

        pack();
        setVisible(false);
    }
}
