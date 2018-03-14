package pms.com.domain.common.entity;

public enum WordModel {
	
	PersonResume("resume//个人简历.docx","个人简历.docx");

	public String path;
	public String name;
	
	WordModel (String path, String name){
		this.name = name;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
	
}
