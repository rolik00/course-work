import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatisticsWindow {
    private JFrame frame;
    private static String[] CategoryNames = {"Основы", "Сортировки", "Структуры данных",  "Графы", "Алгоритмические парадигмы", "Контрольный тест", ""};
    private int[] CategoryScores = {0, 0, 0, 0, 0, 0, -1};

    private void update_statistics()
    {
        Connection con = new Connection();
        for(int i = 0; i < 6; i++)
        {
            CategoryScores[i] = con.get_statistics(i + 1);
        }
    }
    public JFrame create_statistic_window(JFrame other) {
        update_statistics();

        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        insert_image("images//circle_image//basis.png", 30, 80);
        insert_image("images//circle_image//graphs.png", 270, 470);
        insert_image("images//circle_image//sorts.png", 515, 80);
        insert_image("images//circle_image//algorithms.png", 760, 470);
        insert_image("images//circle_image//data_structure.png", 1005, 80);
        insert_image("images//circle_image//test.png", 1250, 470);
        insert_labels();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        TestWindow.RoundButton btnmenu = new TestWindow.RoundButton("Меню",MainWindow.title_color);
        btnmenu.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 22));
        btnmenu.setForeground(MainWindow.main_color);
        btnmenu.setSize(150,70);
        btnmenu.setLocation((int)(width-1375)/2-50,(int)height-70);

        btnmenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                other.setVisible(true);
            }
        });
        frame.add(btnmenu);

        JPanel panel = new JPanel();
        frame.add(panel);
        JTextArea label = new JTextArea("                                        Статистика");
        label.setEditable(false);
        label.setBackground(MainWindow.light_title_color);
        label.setLocation(0, 0);
        label.setSize((int)width,70);
        label.setForeground(MainWindow.main_color);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 48));
        frame.getContentPane().add(label);
        return frame;
    }

    private void insert_image(String filename, int x, int y){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image newImage = myPicture.getScaledInstance(220, 220, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(newImage));
        picLabel.setBounds(x, y+20, 220, 220);
        frame.add(picLabel);
    }

    private void insert_labels(){
        int y;
        for(int i = 0; i < 7; i++)
        {
            JLabel name = new JLabel(CategoryNames[i]);
            name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
            name.setForeground(MainWindow.title_color);
            JLabel score = new JLabel(Integer.toString(CategoryScores[i]) + "%");
            if (CategoryScores[i] == -1) score.setText("");
            score.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
            score.setForeground(MainWindow.title_color);
            if (i % 2 == 0) y = 80;
            else y = 470;
            name.setBounds(35 + i * 245, y - 45, 300, 35);
            score.setBounds(125 + i * 245, y + 240+20, 220, 35);
            //frame.add(name);
            frame.add(score);
        }
    }
}
