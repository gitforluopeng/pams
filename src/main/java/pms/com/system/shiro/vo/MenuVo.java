package pms.com.system.shiro.vo;

import java.util.Comparator;
import java.util.List;

import pms.com.system.shiro.model.ShiroResources;

public class MenuVo {
	
	private Long id;
	private String menuName;
	private Long parentId;
	private String url;
	private boolean hasParent;
	private Integer order;
	private List<MenuVo> childMenuVo;
	
	public MenuVo(ShiroResources shiroResources){
		this.id = shiroResources.getId();
		this.menuName = shiroResources.getName();
		this.parentId = shiroResources.getParentId();
		this.url = shiroResources.getUrl();
		this.order = shiroResources.getShowOrder();
		if(parentId == null || parentId != 0){
			this.hasParent = true;
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public List<MenuVo> getChildMenuVo() {
		if(childMenuVo != null)
			childMenuVo.sort(new MenuVoComparable());
		return childMenuVo;
	}

	public void setChildMenuVo(List<MenuVo> childMenuVo) {
		this.childMenuVo = childMenuVo;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	private class MenuVoComparable implements Comparator<MenuVo>{

		@Override
		public int compare(MenuVo o1, MenuVo o2) {
			if(o1 == null || o2 == null) throw new NullPointerException();
			if(o1.order == null || o2.order == null) throw new NullPointerException();
			if(o1.order > o2.order){
				return 1;
			}else{
				if(o1.order != o2.order) return -1;
			}
			return 0;
		}
		
	}
	
}
