package com.itsc;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Class-wide, static SQL query
    private static final String QUERY =
        "INSERT INTO books (bookname, bookedition, bookprice) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Set content type
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Get the book info from request parameters
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookPriceParam = req.getParameter("bookPrice");

        if (bookName == null || bookEdition == null || bookPriceParam == null) {
            pw.println("<h2>Error: All fields are required!</h2>");
            return;
        }

        float bookPrice;
        try {
            bookPrice = Float.parseFloat(bookPriceParam);
        } catch (NumberFormatException e) {
            pw.println("<h2>Error: Book price must be a valid number!</h2>");
            return;
        }

        // Database connection and query execution
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // JDBC connection details
            String url = "jdbc:mysql://localhost:3306/bookregister";
            String user = "root";
            String password = "K@lkidan1995";

            // Establish connection
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                PreparedStatement ps = conn.prepareStatement(QUERY);
                ps.setString(1, bookName);
                ps.setString(2, bookEdition);
                ps.setFloat(3, bookPrice);

                // Execute update
                int count = ps.executeUpdate();
                if (count == 1) {
                	pw.println("<!DOCTYPE html>");
        		    pw.println("<html>");
        		    pw.println("<head>");
        		    pw.println("<link rel='stylesheet' type='text/css' href='css/bootstrap.css'>");
        		    pw.println("<style>");
        		    pw.println("body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 20px; border: 1px solid green; width: 40rem;  height: 600px; }");
        		    
        		    pw.println("</style>");
        		    pw.println("</head>");
        		   
        		    pw.println("<body class= 'container' >");
        		    pw.println("<h2>Book registered successfully.</h2>");
        		    pw.println("<br>");
        		    pw.println("<br>");

        		    
        		    	
        		    
        		pw.println("</body>");
        		
        		pw.println("</html>");
        		
                   
                } else {
                    pw.println("<h2>Book registration failed.</h2>");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>Error: " + se.getMessage() + "</h2>");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            pw.println("<h2>Error: MySQL Driver not found!</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
        pw.println("<a href='home.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='booklist'>Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Redirect POST requests to the doGet method
        doGet(req, resp);
    }
}


