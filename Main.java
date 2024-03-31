import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Connection con = new Connection();
        con.getAns(1,1);

        GreetingWindow gw = new GreetingWindow();
        JFrame greet = gw.create_greeting_window();
        greet.setVisible(true);
        /*JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        MainWindow mw = new MainWindow();
        JFrame main = mw.create_main_window();
        main.setVisible(true);
        frame.setVisible(false);*/
       /* JFrame frame = new JFrame(String.valueOf("topic"));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        //
        JEditorPane editorPane = null;
        try {
            editorPane = new JEditorPane("C:\\Users\\makov\\Downloads\\sample2.html");
            editorPane.setEditable(false);
            frame.getContentPane().add(editorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //
        frame.setSize(700, 800);
        frame.setVisible(true);
        */
        /*Frame frame = new JFrame(String.valueOf("topic"));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        String html = null;
        try {
            html = Files.readString(Paths.get("C:\\Users\\makov\\Downloads\\sample2.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JEditorPane jep = new JEditorPane();
        jep.setContentType("text/html");
        jep.setText(html);
        jep.setPreferredSize(new Dimension(400,50));
        frame.add(jep);
        frame.setSize(700, 800);
        frame.setVisible(true);*/
    }
}
