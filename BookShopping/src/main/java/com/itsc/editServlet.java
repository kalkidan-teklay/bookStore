package com.itsc;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class editServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "UPDATE books SET bookname = ?, bookedition = ?, bookprice = ? WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = null;
        Connection conn = null;

        try {
            // Get PrintWriter
            pw = resp.getWriter();

            // Set content type
            resp.setContentType("text/html");

            // Get the ID of the record
            int id = Integer.parseInt(req.getParameter("id"));

            // Get the edited data
            String bookName = req.getParameter("bookName");
            String bookEdition = req.getParameter("bookEdition");
            float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Generate the connection
            conn = DriverManager.getConnection(
                "jdbc:mysql:///bookregister", 
                "root", 
                "K@lkidan1995"
            );

            // Prepare the SQL query
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            ps.setInt(4, id);

            // Execute the update
            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h2>Record is edited successfully.</h2>");
            } else {
                pw.println("<h2>Record not edited.</h2>");
            }

        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h3>Error: Unable to load database driver.</h3>");
        } catch (SQLException se) {
            se.printStackTrace();
            if (pw != null) {
                pw.println("<h3>Database Error: " + se.getMessage() + "</h3>");
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            if (pw != null) {
                pw.println("<h3>Error: Invalid input data.</h3>");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ServletException("Error while getting PrintWriter", ioe);
        } finally {
            // Close the connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

            if (pw != null) {
                // Add links for navigation
                pw.println("<a href='home.html'>Home</a>");
                pw.println("<br>");
                pw.println("<a href='booklist'>Book List</a>");
                pw.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

