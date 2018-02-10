package pms.com.domain.common.dao;

import pms.com.domain.common.entity.FileInfo;

public interface FileUploadDao {
	
	public int addFile(FileInfo fileInfo);

	public FileInfo getFileInfoById(Long fileId);
}
