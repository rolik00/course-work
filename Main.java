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

    }
}
