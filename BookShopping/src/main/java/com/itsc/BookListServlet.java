package com.itsc;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {

    private static final String QUERY =
            "select id, bookname, bookedition, bookprice from books";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/bookregister";
            String user = "root";
            String password = "K@lkidan1995";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                PreparedStatement ps = conn.prepareStatement(QUERY);
                ResultSet rs = ps.executeQuery();

                // Start HTML and include Bootstrap
                pw.println("<!DOCTYPE html>");
                pw.println("<html lang='en'>");
                pw.println("<head>");
                pw.println("<meta charset='UTF-8'>");
                pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
                pw.println("<title>Book List</title>");
                pw.println("</head>");
                pw.println("<body class='bg-light'>");

                // Page container
                pw.println("<div class='container my-5'>");
                pw.println("<h1 class='text-center mb-4'>Book List</h1>");

                // Table with Bootstrap classes
                pw.println("<table class='table table-striped table-bordered shadow'>");
                pw.println("<thead class='table-dark'>");
                pw.println("<tr>");
                pw.println("<th>Book ID</th>");
                pw.println("<th>Book Name</th>");
                pw.println("<th>Book Edition</th>");
                pw.println("<th>Book Price</th>");
                pw.println("<th>Edit</th>");
                pw.println("<th>Delete</th>");
                pw.println("</tr>");
                pw.println("</thead>");
                pw.println("<tbody>");

                while (rs.next()) {
                    pw.println("<tr>");
                    pw.println("<td>" + rs.getInt(1) + "</td>");
                    pw.println("<td>" + rs.getString(2) + "</td>");
                    pw.println("<td>" + rs.getString(3) + "</td>");
                    pw.println("<td>" + rs.getFloat(4) + "</td>");
                    pw.println("<td><a href='editScreen?id=" + rs.getInt(1) +
                            "' class='btn btn-warning btn-sm'>Edit</a></td>");
                    pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) +
                            "' class='btn btn-danger btn-sm'>Delete</a></td>");
                    pw.println("</tr>");
                }

                pw.println("</tbody>");
                pw.println("</table>");

                pw.println("<a href='home.html' class='btn btn-primary mt-3'>Home</a>");
                pw.println("</div>"); // Close container

                pw.println("</body>");
                pw.println("</html>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 class='text-danger'>Error: " + se.getMessage() + "</h2>");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            pw.println("<h2 class='text-danger'>Error: MySQL Driver not found!</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 class='text-danger'>Error: " + e.getMessage() + "</h2>");
        } finally {
            if (pw != null) pw.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
