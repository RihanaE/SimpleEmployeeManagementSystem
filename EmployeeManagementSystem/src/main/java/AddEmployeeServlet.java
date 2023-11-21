import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class AddEmployeeServlet extends HttpServlet {
private final static String query = "insert into employee(name, designation, salary ) values (?, ?, ?)";
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		
		res.setContentType("text/html");
		
		pw.println("<link rel = 'stylesheet' href = 'css/bootstrap.css' ></link>");
		
		String name = req.getParameter("userName");
		String designation = req.getParameter("position");
		float salary = Float.parseFloat(req.getParameter("salary"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///employeeman","root", "harun123!" );
				PreparedStatement ps = con.prepareStatement(query);
				){
			
			ps.setString(1, name);
			ps.setString(2,  designation);
			ps.setFloat(3, salary);
			
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
			if (count == 1) {
				pw.println("<h2 class='bg-black text-center'>Created Successfully</h2>");
			} else {
				pw.println("<h2 class='bg-danger text-light text-center'>Not created</h2>");
			}
			
		} catch (SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() +"</h2>");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button></a>");
		
		pw.println("</div>");
		pw.close();
		
		 
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
