import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultWindow {
    private JButton exitButton;
    private JFrame frame;
    private JLabel title;
    private JTextArea result_text;
    private static Font font = new Font("Verdana", Font.PLAIN + Font.BOLD, 16);
    public JFrame create_result_window(JFrame other, String result)
    {
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        title = new JLabel("РЕЗУЛЬТАТЫ");
        title.setForeground(MainWindow.title_color);
        title.setLocation(720, 50);
        title.setSize(200, 50);
        title.setFont(font);
        frame.add(title);

        result_text = new JTextArea(result);
        result_text.setFont(font);
        result_text.setLocation(690, 200);
        result_text.setSize(210, 300);
        result_text.setEditable(false);
        frame.add(result_text);

        exitButton = new JButton("Выход");
        exitButton.setSize(100, 40);
        exitButton.setLocation(750, 700);
        exitButton.setBackground(MainWindow.title_color);
        exitButton.setForeground(MainWindow.main_color);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                other.setVisible(true);
            }
        });
        frame.add(exitButton);

        return frame;
    }
}
