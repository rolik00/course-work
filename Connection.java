import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connection {
    public String getQuestion(int theme, int number) {
        final String connectionUrl = "jdbc:sqlite:coursework.db";
        int resnumber = number+(theme-1)*20;
        try {
            java.sql.Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt  = con.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM questions WHERE id = "+resnumber)) {
                rs.next();
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getType(int theme, int number) {
        final String connectionUrl = "jdbc:sqlite:coursework.db";
        int resnumber = number+(theme-1)*20;
        try {
            java.sql.Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt  = con.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT type FROM questions WHERE id = "+resnumber)) {
                rs.next();
                return rs.getInt("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getRightVar(int theme, int number) {
        final String connectionUrl = "jdbc:sqlite:coursework.db";
        int resnumber = number+(theme-1)*20;
        try {
            java.sql.Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt  = con.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM rightvar WHERE id = "+resnumber)) {
                rs.next();
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList <String> getAns(int theme, int number) {
        final String connectionUrl = "jdbc:sqlite:coursework.db";
        int resnumber = number+(theme-1)*20;
        ArrayList <String> res = new ArrayList<>();
        try {
            java.sql.Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt  = con.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM variants WHERE idquestion = "+resnumber)) {
                while (rs.next()) {
                    res.add(rs.getString("name"));
                }
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int getStatistics(int theme) {
        final String connectionUrl = "jdbc:sqlite:coursework.db";
        try {
            java.sql.Connection con = DriverManager.getConnection(connectionUrl);
            Statement stmt  = con.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT result FROM theories WHERE id = "+theme)) {
                rs.next();
                return rs.getInt("result");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
