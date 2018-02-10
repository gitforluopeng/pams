package pms.com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class wordUtil {
	public static File createWordFile(String contextString,String fileNameString){
		StringBuilder mate = new StringBuilder("<html xmlns:v=\"urn:schemas-microsoft-com:vml\"\n");
		mate.append("xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n");
		mate.append("xmlns:w=\"urn:schemas-microsoft-com:office:word\"\n");
		mate.append("xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\"\n");
		mate.append("xmlns=\"http://www.w3.org/TR/REC-html40\">\n");
		mate.append(contextString);
		mate.append("\n");
		mate.append("</html>");
		OutputStream out = null;
		File newFile = new File(fileNameString+".doc");
		String string = mate.toString();
		byte[] b = string.getBytes();
		try{
			out = new FileOutputStream(newFile);
			out.write(b);
			out.flush();
			return newFile;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
		
	}
}
