import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    private JFrame frame;
    private JTextArea text;
    private URL url;
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

    private JFrame create_theory_window(String title) throws MalformedURLException {
        JFrame window = new JFrame(title);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setLayout(null);

        String filepath;
        if (title == "Basis") url = new URL("https://raw.githubusercontent.com/rolik00/course-work/main/theories/basis.txt");
        else if (title == "Sorts") url = new URL("https://raw.githubusercontent.com/rolik00/course-work/main/theories/sorts.txt");
        else if (title == "Graphs") url = new URL("https://raw.githubusercontent.com/rolik00/course-work/main/theories/graphs.txt");
        else if (title == "Data structures") url = new URL("https://raw.githubusercontent.com/rolik00/course-work/main/theories/data%20structures.txt");
        else if (title == "Algorithmic paradigms") url = new URL("https://raw.githubusercontent.com/rolik00/course-work/main/theories/algoritmic%20paradigms.txt");

        text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setSize(1200, 600);
        scroll.setLocation(175, 75);
        window.getContentPane().add(scroll);

        try {
            readFile();
        } catch (IOException e) {}

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

        try (Connection connection =
                     DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework", "root", "lupsup");
             Statement statement = connection.createStatement()) {
            ResultSet res= statement.executeQuery("SELECT question FROM quest" +
                    " WHERE num = 1");
            if(res.next()) {
                text.append(res.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        text.setCaretPosition(0);

        wtest.add(create_test_button(title, 1, 1));
        wtest.add(create_test_button(title, 2, 1));
        wtest.add(create_test_button(title, 3, 1));
        wtest.add(create_test_button(title, 4, 1));

        JButton next = new JButton("Далее");
        next.setSize(100, 40);
        next.setLocation(725, 725);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wtest.setVisible(false);
                frame.setVisible(true);
            }
        });
        wtest.add(next);

        JButton ex = new JButton("Выход");
        ex.setSize(100, 40);
        ex.setLocation(1400, 30);
        ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wtest.setVisible(false);
                frame.setVisible(true);
            }
        });
        wtest.add(ex);
        return wtest;
    }

    private void readFile() throws IOException {
        BufferedReader read = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String line;
        while ((line = read.readLine()) != null)
            text.append(line + "\n");
        read.close();
        text.setCaretPosition(0);
    }

    private JButton create_test_button(String title, int numb, int num_test){

        String btext="";
        try (Connection connection =
                     DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework", "root", "lupsup");
             Statement statement = connection.createStatement()) {
            ResultSet res= statement.executeQuery("SELECT option"+numb+ " FROM quest" +
                    " WHERE num = 1");
            if(res.next()) {
                btext = res.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JButton button = new JButton(btext);
        button.setSize(590, 120);
        if (numb == 1) button.setLocation(175, 400);
        else if (numb == 2) button.setLocation(785, 400);
        else if (numb == 3) button.setLocation(175, 550);
        else if (numb == 4) button.setLocation(785, 550);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame bss = null;
                try {
                    bss = create_test_window(title);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.setVisible(false);
                bss.setVisible(true);
            }
        });
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    private JButton create_main_button(String title)
    {
        File file = null;
        BufferedImage oImage = null;
        if (title == "Basis") file = new File("images/основы.png");
        else if (title == "Sorts") file = new File("images/сортировки.png");
        else if (title == "Graphs") file = new File ("images/графы.png");
        else if (title == "Data structures") file = new File ("images/структурыданных.png");
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
                    bss = create_theory_window(title);
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}