import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultWindow {
    private JFrame frame;
    private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 32);
    public JFrame create_result_window(JFrame other, String result, int score){
        frame = new JFrame("Tест");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        Font res = new Font(Font.SANS_SERIF, Font.BOLD, 48);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        TestWindow.RoundedJTextArea task = new TestWindow.RoundedJTextArea(MainWindow.light_title_color);
        task.setText("  Тест завершен.");
        task.getCaret().setSelectionVisible(false);
        task.setForeground(MainWindow.main_color);
        task.setEditable(false);
        task.setFont(res);
        task.setLocation((int)(width-1375)/2,85);
        task.setSize(1375,85);
        frame.add(task);

        TestWindow.RoundedJTextArea task1 = new TestWindow.RoundedJTextArea(MainWindow.light_title_color);
        task1.setText("Ваш результат: " + score + " из 10.");
        task1.getCaret().setSelectionVisible(false);
        task1.setForeground(MainWindow.main_color);
        task1.setEditable(false);
        task1.setFont(res);
        task1.setLocation((int)(width-1375)/2,(int)height-150);
        task1.setSize(1375,85);
        frame.add(task1);

        TestWindow.RoundedJTextArea result_text = new TestWindow.RoundedJTextArea(MainWindow.light_main_color);
        result_text.setText(result);
        result_text.setFont(font);
        result_text.setForeground(MainWindow.title_color);
        result_text.setLocation((int)(width-1375)/2,172);
        result_text.setSize(1375, (int)height-150-172);
        result_text.setBackground(MainWindow.light_main_color);
        result_text.setEditable(false);
        frame.add(result_text);

        Font fontexit = new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 24);
        TestWindow.RoundButton exitButton = new TestWindow.RoundButton("На главную",MainWindow.light_title_color);
        exitButton.setSize(300, 40);
        exitButton.setLocation(625, 755);
        exitButton.setFont(fontexit);
        exitButton.setForeground(MainWindow.main_color);
        exitButton.setSize(312,70);
        exitButton.setLocation((int)width/2-156,(int)height-50);
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
