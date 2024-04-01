import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GreetingWindow {
    private JFrame frame;
    private static Color lightBlue= new Color(67,21,113), Blue = new Color(233, 178, 127);
    private static String NAME = "ЗДЕСЬ БУДЕТ НАЗВАНИЕ", SLOGAN = "ЗДЕСЬ БУДЕТ ЛОЗУНГ";
    private static Font font_Name = new Font("Courier New", Font.BOLD, 48), font_Slogan = new Font("Courier New", Font.PLAIN, 18);
    private JLabel name, slogan;

    public JFrame create_greeting_window()
    {
        frame = new JFrame(NAME);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(lightBlue);

        name = new JLabel(NAME);
        name.setFont(font_Name);
        name.setForeground(Blue);
        name.setSize(700, 100);
        name.setLocation(500, 100);
        frame.add(name);

        slogan = new JLabel(SLOGAN);
        slogan.setFont(font_Slogan);
        slogan.setForeground(Blue);
        slogan.setSize(400, 100);
        slogan.setLocation(680, 150);
        frame.add(slogan);

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                MainWindow mw = new MainWindow();
                JFrame main = mw.create_main_window();
                main.setVisible(true);
                frame.setVisible(false);
            }
        });

        return frame;
    }
}
