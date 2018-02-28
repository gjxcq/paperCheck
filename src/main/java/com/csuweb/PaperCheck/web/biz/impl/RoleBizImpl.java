package com.csuweb.PaperCheck.web.biz.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csuweb.PaperCheck.web.biz.RoleBiz;
import com.csuweb.PaperCheck.web.dao.RoleMapper;
import com.csuweb.PaperCheck.web.dao.UserMapper;
import com.csuweb.PaperCheck.web.model.Role;
@Service
public class RoleBizImpl implements RoleBiz {

	@Resource
	private RoleMapper rolemapper;
	
	@Resource
	private UserMapper usermapper;
	@Override
	public Role selRoleByPrimaryKey(String id) {
		return rolemapper.selectByPrimaryKey(id);
	}
	@Override
	public List<Role> selAllowedRole(String permissionids) {
		//System.out.println(permissionids);
		List<Role> list= rolemapper.selAllRole();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getPermissionids()!=null&&!permissionids.contains(list.get(i).getPermissionids())){
					list.remove(list.get(i));
				}
			}
		}
		return list;
	}
	@Override
	public int addRole(Role role) {
		
		return rolemapper.insertSelective(role);
	}
	@Override
	public int upRole(Role role) {
		// TODO Auto-generated method stub
		if(!role.getId().equals("-1")){
			return rolemapper.updateByPrimaryKeySelective(role);
		}else{
			role.setId(UUID.randomUUID().toString());
			return rolemapper.insertSelective(role);
		}
	}
	@Override
	public int delRole(String roleid) {
		int count = usermapper.selectCountUserByRoleId(roleid);
		int state=0;
		if(count>0){
			state = usermapper.updateBydeleteRoleId(roleid);
		}
		if(count==0||state==1){
			state = rolemapper.deleteByPrimaryKey(roleid);
		}
		return state;
	}
	@Override
	public Role selRoleByRoleName(String rolename) {
		// TODO Auto-generated method stub
		return rolemapper.selRoleByRoleName(rolename);
	}

}