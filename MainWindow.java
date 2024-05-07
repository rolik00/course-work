import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    private JFrame frame;
    private int btn_width, btn_height, locx1, locx2, locx3, locy1, locy2;
    public static Color main_color = new Color(74,41,107), title_color = new Color(233, 178, 127),
            light_title_color = new Color(230, 195, 163), light_main_color = new Color(138, 103,172);
    public enum Topic {Basis, Sorts, Data_structures, Graphs, Algorithmic_paradigms, Control_Test}
    private int[] CategoryScores = {0, 0, 0, 0, 0, 0, -1};

    private void update_statistics()
    {
        Connection con = new Connection();
        for(int i = 0; i < 6; i++)
        {
            CategoryScores[i] = con.get_statistics(i + 1);
        }
    }
    public JFrame create_main_window(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        btn_width = 380;//(int)(0.25*width);
        btn_height = 337;//(int)(0.3*height);
        locx1=(int)(0.0625*width);
        locx2=(int)(locx1+btn_width+0.5*(width-2*locx1-3*btn_width));
        locx3=(int)(0.9375*width-btn_width);
        locy1=(int)(0.1*height)+30;
        locy2=(int)(btn_height+locy1+0.5*(height-locy1-2*btn_height));

        frame = new JFrame("Меню");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        TestWindow.RoundButton btnbss = create_main_button(Topic.Basis);
        frame.add(btnbss);

        TestWindow.RoundButton btnstr = create_main_button(Topic.Sorts);
        frame.add(btnstr);

        TestWindow.RoundButton btndts = create_main_button(Topic.Data_structures);
        frame.add(btndts);

        TestWindow.RoundButton btngrp = create_main_button(Topic.Graphs);
        frame.add(btngrp);

        TestWindow.RoundButton btnalg = create_main_button(Topic.Algorithmic_paradigms);
        frame.add(btnalg);

        TestWindow.RoundButton btntst = new TestWindow.RoundButton("Контрольный тест",title_color);
        BufferedImage oImage = null;
        File file = new File("images/тест.png");
        try {
            oImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image scaledImage = oImage.getScaledInstance(btn_width+15, btn_height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        btntst.setIcon(imageIcon);
        //btntst.setVerticalTextPosition(SwingConstants.BOTTOM);
        //btntst.setHorizontalTextPosition(SwingConstants.CENTER);
        btntst.setSize(btn_width, btn_height);
        btntst.setLocation(locx3, locy2);
        btntst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update_statistics();
                if (CategoryScores[0]>75 && CategoryScores[1]>75 && CategoryScores[2]>75 && CategoryScores[3]>75 && CategoryScores[4]>75){
                    TestWindow tw = new TestWindow();
                    JFrame tst = tw.create_test_window(frame, Topic.Control_Test);
                    frame.setVisible(false);
                    tst.setVisible(true);}
                else{
                    JOptionPane.showMessageDialog(null, "Наберите 75% в каждой категории, чтобы разблокировать контрольный тест.");
                }
            }
        });
        frame.add(btntst);

        JPanel panel = new JPanel();
        frame.add(panel);

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("images/fon.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image newImage = myPicture.getScaledInstance(1440, 1024, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(newImage));
        picLabel.setBounds(0, 0, 1440, 1024);

        JTextArea label = new JTextArea("                                   Кладовая алгоритмов");
        label.setEditable(false);
        label.setBackground(light_title_color);
        label.setLocation(0, 0);
        label.setSize((int)width,70);
        label.setForeground(main_color);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 48));

        TestWindow.RoundButton stat = new TestWindow.RoundButton("≡",main_color);
        TestWindow.RoundButton exit = new TestWindow.RoundButton("exit",main_color);
        stat.setLocation(10,10);
        stat.setSize(55,55);
        exit.setLocation((int)width-60,10);
        exit.setSize(55,55);

        File fileexit = new File("images/exit.png");
        BufferedImage oimageexit = null;
        try {
            oimageexit = ImageIO.read(fileexit);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image exitimg = oimageexit.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon exiticn = new ImageIcon(exitimg);
        exit.setIcon(exiticn);

        File filestat = new File("images/stat.png");
        BufferedImage oimagestat = null;
        try {
            oimagestat = ImageIO.read(filestat);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image statimg = oimagestat.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon staticn = new ImageIcon(statimg);
        stat.setIcon(staticn);

        stat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatisticsWindow sw = new StatisticsWindow();
                frame.setVisible(false);
                JFrame statistic = sw.create_statistic_window(frame);
                statistic.setVisible(true);
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //menu.setBackground(main_color);
        //menuBar.setBackground(title_color);
        frame.getContentPane().add(stat);
        frame.getContentPane().add(exit);
        frame.getContentPane().add(label);
        frame.getContentPane().add(picLabel);
        return frame;
    }

    private TestWindow.RoundButton create_main_button(Topic topic){
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
            file = new File ("images/структурыданных.png");
            button_name = "Структуры данных";
        }
        else if (topic == Topic.Graphs) {
            file = new File ("images/графы.png");
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
        TestWindow.RoundButton button = new TestWindow.RoundButton(button_name,title_color);
        button.setSize(btn_width, btn_height);
        if (topic == Topic.Basis) button.setLocation(locx1, locy1);
        else if (topic == Topic.Sorts) button.setLocation(locx2, locy1);
        else if (topic == Topic.Data_structures) button.setLocation(locx3, locy1);
        else if (topic == Topic.Graphs) button.setLocation(locx1, locy2);
        else if (topic == Topic.Algorithmic_paradigms) button.setLocation(locx2, locy2);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TheoryWindow thw = new TheoryWindow();
                JFrame bss = null;
                bss = thw.create_theory_window(topic, frame);
                frame.setVisible(false);
                bss.setVisible(true);
            }
        });
        Image scaledImage = oImage.getScaledInstance(btn_width+20, btn_height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        button.setIcon(imageIcon);
        return button;
    }
}
