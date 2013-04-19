package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.*;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;

import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;

public class RDSManager {
		private AWSCredentialsProvider credentialsProvider;
		private AmazonRDSClient rds;
		Connection conn = null;	
		Statement stmt = null;
	
		public RDSManager() {
	
			init();
		}
	
		private void init() {
			try {
			
				//read AWS credentials
				credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
				rds = new AmazonRDSClient(credentialsProvider);
				
				Class.forName("com.mysql.jdbc.Driver");
				//make connection to RDS			
				conn = DriverManager.getConnection("jdbc:mysql://videodb.cm1d6a3heqsc.us-east-1.rds.amazonaws.com:3306/Clouddb?" +
				                                   "user=&password=");
				stmt = conn.createStatement();
			//	stmt.executeUpdate("DROP TABLE video");
//				System.out.println("Table dropped");
//				stmt.executeUpdate("CREATE TABLE video (" +
//						"id int NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
//						"FileName VARCHAR(100),"+
//						"Rate float,"+
//						"Time VARCHAR(100),"+
//						"Times INT)");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
		public void addObject(String fileName)
		{
			Date date = new Date();
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			String sql = "insert into video (FileName, Time) values ('"+fileName+"','"+time+"')";
			try
			{
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				System.out.println(sql);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
		public void deleteObject (String fileName)
		{
			String sql = "delete from video where FileName ='"+fileName+"'";
			try
			{
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				System.out.println(sql);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	
		public ResultSet readObjectRatings() throws InterruptedException {
				//read the ratings of the videos
			String sql = "select FileName, Rate from video order by Rate DESC";
			try
			{
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				return rs;
				
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
				return null;
			}
		}

		public void updateRating(String fileName, int rate) {
			//update or create the rating of video
			String sql1 = "select Rate, Times from video where FileName='"+fileName+"'";
			
			try
			{
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql1);
				rs.next();
				int times = 0;
				float rating = 0;
				if(rs.getInt("Times")!= 0)
				{
					times = rs.getInt("Times");
					rating = rs.getFloat("Rate");
				}
				float ave = (rating*times+rate)/(times+1);
				String sql = "update video set rate="+ave+",Times="+(times+1)+" where FileName='"+fileName+"'";
				stmt.executeUpdate(sql);
				System.out.println(sql);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		public void close()
		{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
}
