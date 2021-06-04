package com.labs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PostgreSQLDAO {
    public LinkedHashSet<Student> getStudentsByFields(String[] searchArgs) {
        LinkedHashSet<Student> students = new LinkedHashSet<Student>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            for (String searchArg : searchArgs) {
                // Для поиска по конкретному совпадению с полем из БД можно изменить шаблон в запросе SELECT
                ResultSet rs = stmt.executeQuery("SELECT t.* FROM students t WHERE (t.*)::text LIKE '%" + searchArg + "%'");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    int age = rs.getInt("age");
                    int student_id = rs.getInt("student_id");
                    String mark = rs.getString("mark");
                    Student student = new Student(name, surname, age, student_id, mark);
//                    System.out.println(student.hashCode());
                    students.add(student);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    public String createStudent(String name, String surname, int age, int studentId, String mark) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            System.out.println("INSERT INTO students(name, surname, age, student_id, mark) values ('" +
                    name + "', '" + surname + "', " + age + ", " + studentId + ", '" + mark + "');");
            int rs = stmt.executeUpdate("INSERT INTO students(name, surname, age, student_id, mark) values ('" +
                    name + "', '" + surname + "', " + age + ", " + studentId + ", '" + mark + "');");
            status = Integer.toString(rs);

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public String deleteStudent(int rowId) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("DELETE FROM students WHERE id='" + rowId + "';");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;

    }

    public String updateStudent(int rowId, List<String> updateArgs) {
        String status = "0";
        String updateFields = String.join(", ", updateArgs);

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE students SET " + updateFields + " WHERE id=" + rowId + ";");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

}