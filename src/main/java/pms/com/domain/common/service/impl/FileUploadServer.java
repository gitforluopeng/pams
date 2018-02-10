package pms.com.domain.common.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pms.com.domain.common.entity.FileInfo;
import pms.com.domain.common.dao.FileUploadDao;
import pms.com.domain.common.service.FileUploadInter;
import pms.com.system.shiro.util.UserUtil;

/**
 * Task:上传文件服务
 * init:安装此服务需要注入tempPath(以webapp为根路径创建的缓存文件夹)
 * 和savePath(以webapp为根路径创建的储存文件夹)
 * 和maxFileSize(最大文件大小,以字节来计算)
 * 
 *
 */
@Service
public class FileUploadServer implements FileUploadInter,InitializingBean {

	
	@Resource
	private SqlSessionFactory sessionFactory;
	private static long FILE_CACHE_SIZE= new Long(10*1024*1024);
	private static String encode = "UTF-8";
	private String tempPath;
	private String savePath;
	private static long maxFileSize = 0;
	public static String UPLOAD_SESSION_KEY = "FILE_UPLOAD_INFO";
	public void setFILE_CACHE_SIZE(long fILE_CACHE_SIZE) {
		FILE_CACHE_SIZE = fILE_CACHE_SIZE;
	}

	public void setEncode(String encode) {
		FileUploadServer.encode = encode;
	}
	
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setMaxFileSize(String maxFileSize) {
		System.out.println("[UploadServer ----> setMaxFileSize] dependency maxFileSize "+maxFileSize);
		FileUploadServer.maxFileSize = Long.parseLong(maxFileSize);
	}

	public static long getFILE_CACHE_SIZE() {
		return FILE_CACHE_SIZE;
	}

	public static String getEncode() {
		return encode;
	}

	public static long getMaxFileSize() {
		return maxFileSize;
	}

	@Override
	public Map<String, Object> saveData(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 0);
		System.out.println("[UploadServer ----> saveData] Content-Length is "+request.getHeader("Content-Length"));
		if(Long.parseLong(request.getHeader("Content-Length")) >= maxFileSize){//如果上传的文件大于最大文件返回4
			map.put("status", 4);
			return map;
		}
		String basePath = request.getServletContext().getRealPath("/");
		System.out.println("[UploadServer ----> saveData] dependecy tempPath "+tempPath);
		tempPath = basePath+File.separator+tempPath;
		System.out.println("[UploadServer ----> saveData] tempPath is "+tempPath);
		Map<String, String> paramsNamesMap = new HashMap<String, String>();
		FileItem dataItem = null;
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold((int)FILE_CACHE_SIZE);
		fileItemFactory.setRepository(new File(tempPath));
		
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		request.getSession().setAttribute("CurrentUpload", fileUpload);
		fileUpload.setFileSizeMax(maxFileSize);
		fileUpload.setHeaderEncoding(encode);
		
		
		SqlSession session=sessionFactory.openSession();
		FileUploadDao fileUploadDao = session.getMapper(FileUploadDao.class);
		FileInfo fileInfo = new FileInfo();
		
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		Date date=new Date();
		String userName=UserUtil.getUserName();
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			System.out.println("[UploadServer ----> saveData] items size "+items.size());
			Iterator<FileItem> iterator = items.iterator();
			Long fileSize=null;
			while(iterator.hasNext()){
				FileItem item = iterator.next();
				if(item.isFormField()){
					System.out.println("[UploadServer ----> saveData] param name is "+item.getFieldName());
					paramsNamesMap.put(item.getFieldName(), item.getString());
				}else {
					System.out.println("[UploadServer ----> saveData] get the data");
					dataItem = item;
					System.out.println("[UploadServer ----> saveData] dataItem size "+item.getSize());
					fileSize=item.getSize();
				}
			}
			
			if(dataItem != null && dataItem.getSize() <= maxFileSize && dataItem.getSize() != 0){
				String fileName = dataItem.getName();
				System.out.println("[UploadServer ----> saveData] basic fileName "+fileName);
				System.out.println("[UploadServer ----> saveData] start write file, file name is "+fileName);
				String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				System.out.println("[UploadServer ----> saveData] fileType is "+fileType);
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1,fileName.lastIndexOf("."));
				if(dataItem.getSize() < maxFileSize){
					String filePath = basePath+savePath+File.separator+uuid+"."+fileType;
					File file = new File(filePath);
					System.out.println("[UploadServer ----> saveData] start write file, file path is "+filePath);
					fileInfo.setFileName(fileName);
					fileInfo.setFilePath(uuid+"."+fileType);
					fileInfo.setFileOwner(userName);
					fileInfo.setFileType(fileType);
					fileInfo.setUploadTime(date);
					fileInfo.setFileSize(fileSize);
					fileUploadDao.addFile(fileInfo);
					
					dataItem.write(file);
					session.commit();
					map.put("file_info", fileInfo);
					map.put("status", 0);
				}else {
					map.put("status", 1);
				}
			}else {
				if(dataItem == null){
					map.put("status", -1);
				}else {
					map.put("status", 4);
				}
			}
			
		} catch (FileUploadException e) {
			map.put("status", 2);
			session.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			map.put("status", 2);
			session.rollback();
			e.printStackTrace();
		}finally{
			dataItem.delete();
			session.close();
		}
		
		return map;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isEmpty(tempPath)){
			throw new BeanCreationException("[UploadServer ----> afterPropertiesSet] must set string tempPath property");
		}
		
		if(StringUtils.isEmpty(savePath)){
			throw new BeanCreationException("[UploadServer ----> afterPropertiesSet] must set string savePath property");
		}
		
		if(maxFileSize == 0){
			throw new BeanCreationException("[UploadServer ----> afterPropertiesSet] must set long maxFileSize property");
		}
	}

	@Override
	public FileInfo getFileInfo(Long fileId) {
		FileInfo fileInfo = new FileInfo();
		SqlSession session =sessionFactory.openSession();
		FileUploadDao fileUploadDao =session.getMapper(FileUploadDao.class);
		fileInfo = fileUploadDao.getFileInfoById(fileId);
		session.close();
		return fileInfo;
	}

}
