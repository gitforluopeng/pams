package pms.com.domain.personResumeManage.services;

import pms.com.domain.personResumeManage.model.PersonResume;

public interface PerSonResumeServiceInter {
	public int addPersonResume(PersonResume personResume);
	public int updatePersonResumeById(PersonResume personResume);
	public int updatePersonResumeByShiroUserId(PersonResume personResume);
	public int updatePersonResumeByShiroUserName(PersonResume personResume);
	public PersonResume getPersonResume(PersonResume personResume);	
}
