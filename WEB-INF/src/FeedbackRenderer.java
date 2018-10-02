
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class FeedbackRenderer extends HttpServlet{

	public Connection con = null ;
	public ResultSet rs = null ;	
	public PreparedStatement ps = null ;
	public static PrintWriter pw = null;

	public static String table = "feedback";
	public static String database = "2016BCS038";
	
	public void init(){
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res){
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"?autoReconnect=true&useSSL=false","root","root");
			ps = con.prepareStatement("insert into "+table+" values(?,?,?,?,?,?  ,?,  ?,?,?,?,?,?,?,?,?)");
			
			
			pw = res.getWriter();
				
			String[] products = {	"Dell Inspiron 15 5577",
									"Acer Swift 3",
									"Dell Alianware 17",
									"Dell XPS 13",
									"Acer Predator 21X",
									"Razer Blade 15",
									"HP Omen 15",
									"lenovo Y50"
								};
			ps = con.prepareStatement("select * from "+table);
			rs = ps.executeQuery();
				
				
			pw.println("<html><head><link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css'/><script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js'></script><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">");
				
			pw.println("</head><body>");
					
				pw.println("<div class='row'>");
				pw.println("<ul class='collapsible col s12 m10 l8 offset-l2 offset-m1'>");
					
				for(String p : products) {
					pw.println("<li>");
					pw.println("<div class='collapsible-header'><i class='material-icons'></i>"+p+"</div>");
					pw.println("<div class='collapsible-body'>");
					pw.println("<div class='row'>");
					ps = con.prepareStatement("select * from "+table+" where product='"+p+"'");
					rs = ps.executeQuery();
					while(rs.next()) {
							
						pw.println("<div class='col s12 m6'>");
						pw.println("<div class='card blue-grey darken-1'>");
						pw.println("<div class='card-content white-text'>");
						pw.println("<span class='card-title'>"+rs.getString("Name")+"</span>");
						pw.println("<p>"+rs.getString("review")+"</p>");
						pw.println("</div>");
						pw.println("<div class='card-action'>");
						pw.println("<span>"+rs.getFloat("stars")+"/5");
						
						pw.println("</span>");
						pw.println("</div>");
						pw.println("</div>");
						pw.println("</div>");							
				
					}
					pw.println("</div>");
					pw.println("</div>");
					pw.println("</li>");
				}
					
				pw.println("</ul>");
				pw.println("</div>");
					
				pw.println("<script>M.AutoInit();document.addEventListener('DOMContentLoaded', function() {var elems = document.querySelectorAll('.collapsible');var instances = M.Collapsible.init(elems, options);});</script>");
				pw.println("</body></html>");
				
			
			
			
			
			
		}catch(Exception e){
		}
	}

	public void destroy() {
		try {
			con.close();
			rs.close();
			ps.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
