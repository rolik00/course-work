import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestWindow {
    public JFrame create_test_window(String title, JFrame frame) {
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
        JTextArea text = new JTextArea();
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

        wtest.add(create_test_button(title, 1, 1,frame));
        wtest.add(create_test_button(title, 2, 1,frame));
        wtest.add(create_test_button(title, 3, 1,frame));
        wtest.add(create_test_button(title, 4, 1,frame));

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


    private JButton create_test_button(String title, int numb, int num_test, JFrame frame){

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
                    TestWindow w = new TestWindow();
                    bss = w.create_test_window(title,frame);
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
}