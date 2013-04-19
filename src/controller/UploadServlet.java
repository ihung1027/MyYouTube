package controller;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import manager.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.*;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		boolean isMulti = ServletFileUpload.isMultipartContent(request);
		if (isMulti) {
			//parse the file and upload it
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			
			try {
				AmazonS3Manager s3Manager = new AmazonS3Manager();
				RDSManager rdsManager = new RDSManager();
				
				String fileName = "";
				List list = upload.parseRequest(request);
				Iterator iter = list.iterator();
				
				while (iter.hasNext()) 
				{					
					FileItem item = (FileItem)iter.next();					
					if (item.isFormField()) {
						System.out.println("Form field.");
					} else {
						fileName = item.getName();
						File fileData = new File(fileName);
//						InputStream inputStream = item.getInputStream();
						
						item.write(fileData);
                        
                        s3Manager.addItem(fileName, fileData);
					}
				}
				if(!fileName.equals(""))
				{
					rdsManager.addObject(fileName);
					rdsManager.close();
				}
				System.out.println("Upload of "+fileName+" completed!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}		
		response.sendRedirect("list.jsp");
	}
}
