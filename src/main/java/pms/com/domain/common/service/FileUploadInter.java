package pms.com.domain.common.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import pms.com.domain.common.entity.FileInfo;
public interface FileUploadInter {
	
	public Map<String, Object> saveData(HttpServletRequest request);
	
	public FileInfo getFileInfo(Long fileId);
}
