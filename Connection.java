import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Connection {
    public void create_test_info(int topic) {
        Set<Integer> number_questions = generate_test_numbers(topic);
        int i = 0;
        for (int number : number_questions)
        {
            TestWindow.questions[i] = get_question(number);
            TestWindow.Type[i] = get_type(number);
            ArrayList<String> answers = get_answers(number);
            if (TestWindow.Type[i] == 5) {
                int n = answers.size() / 2;
                TestWindow.columns[i] = new String[n];
                String [] tmp = new String[n + 1];
                tmp[0] = "";
                int size = 1;
                for (int j = 0; j < answers.size(); j++)
                {
                    if (j < n) TestWindow.columns[i][j] = answers.get(j);
                    else if (!answers.get(j).equals(""))
                    {
                        tmp[j - n + 1] = answers.get(j);
                        size++;
                    }
                }
                TestWindow.answers[i] = new String[size];
                for (int j = 0; j < size; j++)
                {
                    TestWindow.answers[i][j] = tmp[j];
                }
            }
            else
            {
                TestWindow.answers[i] = tranform(answers);
            }
            ArrayList<String> right_answers = get_right_answers(number);
            if (TestWindow.Type[i] == 1 || TestWindow.Type[i] == 2 || TestWindow.Type[i] == 4)
            {
                String s = "";
                for (int j = 0; j < right_answers.size(); j++) {
                    s += right_answers.get(j);
                }
                String[] arr = new String[1];
                arr[0] = s;
                TestWindow.right_answers[i] = arr;
            }
            else TestWindow.right_answers[i] = tranform(right_answers);
            i++;
        }
    }

    private Set<Integer> generate_test_numbers(int topic) {
        Set<Integer> st = new HashSet<Integer>();
        Random r = new Random();
        while (st.size() != 10) {
            int x;
            if (topic != 5) {
                x = r.nextInt(20) + 1;
                x = x + topic * 20;
            } else {
                x = r.nextInt(100) + 1;
            }
            st.add(x);
        }
        return st;
    }

    private String get_question(int number) {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM questions WHERE id = " + number)) {
                rs.next();
                return rs.getString("name");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return "";
    }

    private int get_type(int number) {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT type FROM questions WHERE id = " + number)) {
                rs.next();
                return rs.getInt("type");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return -1;
    }

    private ArrayList<String> get_answers(int number) {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        ArrayList<String> answers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM variants WHERE idquestion = " + number)) {
                while (rs.next()) {
                    answers.add(rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return answers;
    }

    private ArrayList<String> get_right_answers(int number) {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        ArrayList<String> right_answers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT name FROM rightvar WHERE idquestion = " + number)) {
                while (rs.next()) {
                    right_answers.add(rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return right_answers;
    }

    private String[] tranform(ArrayList<String> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public int get_statistics(int topic) {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try (ResultSet rs = stmt.executeQuery("SELECT result FROM theories WHERE id = " + topic)) {
                rs.next();
                return rs.getInt("result");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return -1;
    }

    public void set_statistics(int topic, int result)
    {
        java.sql.Connection conn = null;
        final String url = "jdbc:sqlite:coursework.db";
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE theories SET result = " + result + " WHERE id = " + (topic + 1));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
