package pms.com.domain.personResumeManage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pms.com.domain.common.entity.FileInfo;
import pms.com.domain.common.entity.WordModel;
import pms.com.domain.common.service.FileUploadInter;
import pms.com.domain.common.service.WordModelServiceInter;
import pms.com.domain.personResumeManage.model.Nation;
import pms.com.domain.personResumeManage.model.PersonResume;
import pms.com.domain.personResumeManage.services.NationServiceInter;
import pms.com.domain.personResumeManage.services.PerSonResumeServiceInter;
import pms.com.system.shiro.annotate.BeanParam;
import pms.com.system.shiro.util.UserUtil;

@Controller
public class PersonResumeController {
	@Resource
	private NationServiceInter nationServer;
	@Resource
	private PerSonResumeServiceInter personResumeServer;
	@Resource
	private FileUploadInter fileUploadServer;
	@Resource
	private WordModelServiceInter wordModelServer;
	//首页映射
	@RequestMapping(value="/personResume_index",method=RequestMethod.GET)
	public String loadUserPersonResumeIndex(){
		return "pms/personResumeManage/personResume_index.jsp";
	}

	//添加映射
	@RequestMapping(value="/personResume_add",method=RequestMethod.GET)
	public String loadUserPersonResumeAdd(){
		return "pms/personResumeManage/personResume_add.jsp";
	}

	//添加映射
	@RequestMapping(value="/personResume_update",method=RequestMethod.GET)
	public String loadUserPersonResumeUpdate(){
		return "pms/personResumeManage/personResume_update.jsp";
	}
	//加载民族信息
	@RequestMapping(value="/load_all_nations",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadAllNations(){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Nation> nations=nationServer.getNations();
		result.put("nations", nations);
		return result;
	}

	//创建简历请求接口
	//
	@RequestMapping(value="/add_personResume",method=RequestMethod.GET)
	@ResponseBody
	public String addPersonResume(@BeanParam PersonResume personResume){
		JSONObject json = new JSONObject();
		int status=-1;
		status=personResumeServer.addPersonResume(personResume);
		json.put("status", status);
		return json.toString();
	}

	//加载简历信息
	@RequestMapping(value="/load_personResume",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadPersonResum(){
		Map<String, Object> result = new HashMap<String, Object>();
		PersonResume personResume=new PersonResume();
		personResume.setShiroUserId(UserUtil.getShiroUser().getId());
		PersonResume resume=personResumeServer.getPersonResume(personResume);
		if (resume==null) {
			result.put("status", 0);
			return result;
		}
		FileInfo fileInfo=fileUploadServer.getFileInfo(resume.getChatUploadId());
		result.put("status", 1);
		result.put("updoadFile", fileInfo);
		result.put("personResume",resume);
		return result;
	}

	//编辑简历请求接口
	//
	@RequestMapping(value="/update_personResume",method=RequestMethod.GET)
	@ResponseBody
	public String updatePersonResume(@BeanParam PersonResume personResume){
		JSONObject json = new JSONObject();
		int status=-1;
		status=personResumeServer.updatePersonResumeById(personResume);
		json.put("status", status);
		return json.toString();
	}

	//下载简历请求路径
	@RequestMapping(value="/down_file",method=RequestMethod.GET)
	public void downFile(HttpServletResponse response){
		PersonResume personResume=new PersonResume();
		personResume.setShiroUserId(UserUtil.getShiroUser().getId());
		PersonResume resume=personResumeServer.getPersonResume(personResume);
		Map<String, String> params = new HashMap<String, String>();
		params.put("personName", resume.getPersonName());
		params.put("sex",resume.getSex());
		params.put("nation", resume.getNation());
		params.put("address", resume.getAddress());
		params.put("birthday", resume.getBirthday());
		params.put("university", resume.getUniversity());
		params.put("education", resume.getEducation());
		params.put("professional", resume.getProfessional());
		params.put("expectJob", resume.getExpectJob());
		params.put("professionalSkills", resume.getProfessionalSkills());
		params.put("workYear", resume.getWorkYear());
		params.put("phone", resume.getPhone().toString());
		params.put("email", resume.getEmail());
		params.put("workExperience", resume.getWorkExperience());
		params.put("showYourSelf", resume.getShowYourSelf());
		File file = wordModelServer.createWordModel(WordModel.PersonResume, params, 1);
		InputStream in = null;
		OutputStream out = null;
		try {
			System.out.println(file.getName());
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int index = -1;
			while((index = in.read(buffer)) != -1){
				out.write(buffer,0,index);
				out.flush();
			}
		} catch (IOException e) {
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
		return;
	}
}
