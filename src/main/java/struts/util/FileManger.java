package struts.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

public class FileManger {
	
	public static Map<String, String>upload(FormFile formFile,String filePath) throws IOException{
		
		Map<String, String> result = new HashMap<String, String>();
		int extensionIdx = formFile.getFileName().lastIndexOf(".");
		String extension = formFile.getFileName().substring(extensionIdx + 1);
		
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = System.currentTimeMillis() + "_" + formFile.getFileName();
        File file = new File(filePath, fileName);
        try (InputStream stream = formFile.getInputStream();
             FileOutputStream out = new FileOutputStream(file)) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = stream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
        	e.printStackTrace();
		}
        
        result.put("fileName", fileName);
        result.put("fileOrigin", formFile.getFileName());
        result.put("localPath", "uploads/" + fileName);
        result.put("extension", extension);
        
        return result;
	}

}
