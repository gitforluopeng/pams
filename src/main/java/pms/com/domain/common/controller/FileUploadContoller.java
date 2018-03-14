package pms.com.domain.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.common.entity.FileInfo;
import pms.com.domain.common.service.FileUploadInter;
import pms.com.utils.SystemUtil;


@Controller
@Scope("prototype")
public class FileUploadContoller {
	
	@Resource
	private FileUploadInter fileUploadInter;
	
	private static String DATA_NULL = "DATA_NULL";
	private static int EXCEED_MAX_SIZE = 4;
	private String basePath = SystemUtil.getSystemFileAbsoultePath();
	
	
	@RequestMapping(value="/upload_file",produces="application/json ; charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 200);
		System.out.println("[FileUploadContoller ----> uploadFile] request is MultipartContent "+ServletFileUpload.isMultipartContent(request));
		Map<String, Object> result = fileUploadInter.saveData(request);
		int resultInt=Integer.parseInt(result.get("status").toString());		
		if(resultInt == -1 ){
			map.put("status", "ServiceStaticField.PARAM_LOSE");
			map.put("info", DATA_NULL);
			return map;
		}
		if(resultInt == 3 ){
			map.put("status", 500);
			map.put("info", "ServiceStaticField.SERVICES_ERROR");
			return map;
		}
		if(resultInt == 4){
			map.put("status", "ServiceStaticField.PARAM_LOSE");
			map.put("info", EXCEED_MAX_SIZE);
			return map;
		}
		if(resultInt == 0){
			map.put("status", "success");
			map.put("file_info", result.get("file_info"));
			return map;
		}
		return map;
	}
	
	/**
	 * Task : 附件下载请求路径
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param attachmentName 附件名称
	 * date :2017年11月15日
	 * @author luopeng
	 */
	@RequestMapping(value="/download_file",method=RequestMethod.GET)
	@ResponseBody
	public void downloadattachment(HttpServletRequest request,HttpServletResponse response,Long fileId){
		FileInfo fileInfo = fileUploadInter.getFileInfo(fileId);
		String fileName = fileInfo.getFileName()+"."+fileInfo.getFileType();
		String filePath = fileInfo.getFilePath();
		InputStream in = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")); 
            in = new FileInputStream(new File(basePath+"WEB-INF"+File.separator+"fileSave"+File.separator+filePath));
            byte [] b=new byte[1024];
            int len=-1;
            while((len=in.read(b))!=-1){
            	out.write(b, 0, len);
            }
            out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				
			}
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				
			}
		}
		return;
	}
	
}
