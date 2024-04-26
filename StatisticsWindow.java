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
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        insert_image("images//circle_image//basis.png", 30, 80);
        insert_image("images//circle_image//sorts.png", 270, 470);
        insert_image("images//circle_image//data_structure.png", 515, 80);
        insert_image("images//circle_image//graphs.png", 760, 470);
        insert_image("images//circle_image//algorithms.png", 1005, 80);
        insert_image("images//circle_image//test.png", 1250, 470);
        insert_labels();

        JButton ex = new JButton("Выход");
        ex.setSize(100, 40);
        ex.setLocation(725, 725);
        ex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                other.setVisible(true);
            }
        });
        frame.add(ex, BorderLayout.SOUTH);

        return frame;
    }

    private void insert_image(String filename, int x, int y)
    {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image newImage = myPicture.getScaledInstance(220, 220, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(newImage));
        picLabel.setBounds(x, y, 220, 220);
        frame.add(picLabel);
    }

    private void insert_labels()
    {
        int y;
        for(int i = 0; i < 7; i++)
        {
            JLabel name = new JLabel(CategoryNames[i]);
            name.setFont(new Font("Courier New", Font.ITALIC, 18));
            name.setForeground(MainWindow.title_color);
            JLabel score = new JLabel(Integer.toString(CategoryScores[i]) + "%");
            if (CategoryScores[i] == -1) score.setText("");
            score.setFont(new Font("Courier New", Font.ITALIC, 20));
            score.setForeground(MainWindow.title_color);
            if (i % 2 == 0) y = 80;
            else y = 470;
            name.setBounds(35 + i * 245, y - 45, 300, 35);
            score.setBounds(125 + i * 245, y + 240, 220, 35);
            frame.add(name);
            frame.add(score);
        }
    }
}
