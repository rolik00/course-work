import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GreetingWindow gw = new GreetingWindow();
        JFrame greet = gw.create_greeting_window();
        greet.setVisible(true);
    }
}
