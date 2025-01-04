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

@WebServlet("/editScreen")
public class editScreenServlet extends HttpServlet {
    private static final String query = 
        "SELECT bookname, bookedition, bookprice FROM books WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter pw = null;

        try {
            // Set content type
            resp.setContentType("text/html");
            pw = resp.getWriter();

            // Get book ID from request
            int id = Integer.parseInt(req.getParameter("id"));

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql:///bookregister", "root", "K@lkidan1995");

            // Prepare and execute SQL statement
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

           
            pw.println("<!DOCTYPE html>");
            pw.println("<html lang='en'>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
            pw.println("<title>Edit Book</title>");
            pw.println("</head>");
            pw.println("<body class='bg-light'>");

           
            pw.println("<div class='container my-5'>");

            if (rs.next()) {
                pw.println("<div class='card shadow-lg'>");
                pw.println("<div class='card-header bg-primary text-white text-center'>");
                pw.println("<h2>Edit Book Details</h2>");
                pw.println("</div>");
                pw.println("<div class='card-body'>");
                
                pw.println("<form action='editurl?id=" + id + "' method='post'>");

                
                pw.println("<div class='mb-3'>");
                pw.println("<label for='bookName' class='form-label'>Book Name</label>");
                pw.println("<input type='text' id='bookName' name='bookName' class='form-control' value='" + rs.getString(1) + "' required>");
                pw.println("</div>");

               
                pw.println("<div class='mb-3'>");
                pw.println("<label for='bookEdition' class='form-label'>Book Edition</label>");
                pw.println("<input type='text' id='bookEdition' name='bookEdition' class='form-control' value='" + rs.getString(2) + "' required>");
                pw.println("</div>");

                // Book Price
                pw.println("<div class='mb-3'>");
                pw.println("<label for='bookPrice' class='form-label'>Book Price</label>");
                pw.println("<input type='text' id='bookPrice' name='bookPrice' class='form-control' value='" + rs.getFloat(3) + "' required>");
                pw.println("</div>");

                // Buttons
                pw.println("<div class='d-flex justify-content-between'>");
                pw.println("<button type='submit' class='btn btn-success'>Save Changes</button>");
                pw.println("<a href='home.html' class='btn btn-secondary'>Cancel</a>");
                pw.println("</div>");

                pw.println("</form>");
                pw.println("</div>");
                pw.println("</div>");
            } else {
              
                pw.println("<div class='alert alert-danger text-center'>");
                pw.println("<h4>No book found with the given ID.</h4>");
                pw.println("</div>");
            }

            pw.println("</div>"); 
            pw.println("</body>");
            pw.println("</html>");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (pw != null) {
                pw.println("<div class='alert alert-danger'>Error: " + e.getMessage() + "</div>");
            }
        }

        if (pw != null) {
            pw.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }
}
