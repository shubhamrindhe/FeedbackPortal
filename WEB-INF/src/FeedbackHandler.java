
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class FeedbackHandler extends HttpServlet{

	public Connection con = null ;
	public ResultSet rs = null ;	
	public PreparedStatement ps = null ;
	public static PrintWriter pw = null;

	public static String database = "2016BCS038";
	public static String table = "feedback";
	
	public boolean savior(HttpServletRequest req,HttpServletResponse res){
		
		String regNo,name,eMail,contactNo,product,review;
		
		String[] Q = {"one","two","three","four","five","six","seven","eight","nine"};
		int[] rating = new int[Q.length];
		float stars = 0;
		
		regNo = req.getParameter("reg");
		name = req.getParameter("name");
		eMail = req.getParameter("email");
		contactNo = req.getParameter("contact");
		product = req.getParameter("product");
		review = req.getParameter("review");
		
		for(int i=0;i<Q.length;++i) {
			String r = req.getParameter(Q[i]);
			rating[i] = Integer.parseInt( r == null ? "0" : r );
		}
		
		stars = 0;
		for(int i=0;i<rating.length;++i)
			stars+=rating[i];
		stars /= rating.length;
			
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"?autoReconnect=true&useSSL=false","root","root");
			ps = con.prepareStatement("insert into "+table+" values(?,?,?,?,?,?  ,?,  ?,?,?,?,?,?,?,?,?)");
			
			ps.setString(1,regNo);
			ps.setString(2,name);
			ps.setString(3,eMail);
			ps.setString(4,contactNo);
			ps.setString(5,product);
			ps.setString(6,review);
			ps.setFloat(7,stars);
			
			for(int i=0;i<rating.length;++i) {
				ps.setInt(i+8, rating[i]);
			}
			
			if(ps.executeUpdate()==1)
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
		
	}
	public void init(){
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res){
		try{
			
			pw = res.getWriter();
			if(savior(req,res)) {
				RequestDispatcher rd = req.getRequestDispatcher("./feedbacks");
				rd.forward(req,res);
				
				
//				String[] products = {	"Dell Inspiron 15 5577",
//										"Acer Swift 3",
//										"Dell Alianware 17",
//										"Dell XPS 13",
//										"Acer Predator 21X",
//										"Razer Blade 15",
//										"HP Omen 15",
//										"lenovo Y50"
//									};
//				ps = con.prepareStatement("select * from "+table);
//				rs = ps.executeQuery();
//				
//				
//				pw.println("<html><head><link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css'/><script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js'></script><link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">");
//				
//				pw.println("</head><body>");
//					
//					pw.println("<div class='row'>");
//					pw.println("<ul class='collapsible col s12 m10 l8 offset-l2 offset-m1'>");
//					
//					for(String p : products) {
//						pw.println("<li>");
//						pw.println("<div class='collapsible-header'><i class='material-icons'></i>"+p+"</div>");
//						pw.println("<div class='collapsible-body'>");
//						pw.println("<div class='row'>");
//						ps = con.prepareStatement("select * from "+table+" where product='"+p+"'");
//						rs = ps.executeQuery();
//						while(rs.next()) {
//							
//							pw.println("<div class='col s12 m6'>");
//							pw.println("<div class='card blue-grey darken-1'>");
//							pw.println("<div class='card-content white-text'>");
//							pw.println("<span class='card-title'>"+rs.getString("Name")+"</span>");
//							pw.println("<p>"+rs.getString("review")+"</p>");
//							pw.println("</div>");
//							pw.println("<div class='card-action'>");
//							pw.println("<span>"+rs.getFloat("stars")+"/5</span>");
//							pw.println("</div>");
//							pw.println("</div>");
//							pw.println("</div>");							
//					
//						}
//						pw.println("</div>");
//						pw.println("</div>");
//					    pw.println("</li>");
//					}
//					
//					pw.println("</ul>");
//					pw.println("</div>");
//					
//				pw.println("<script>M.AutoInit();document.addEventListener('DOMContentLoaded', function() {var elems = document.querySelectorAll('.collapsible');var instances = M.Collapsible.init(elems, options);});</script>");
//				pw.println("</body></html>");
				
			}
			
			
			
			
			
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
