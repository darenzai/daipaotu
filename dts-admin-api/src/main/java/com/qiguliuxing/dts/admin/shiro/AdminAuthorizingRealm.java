package com.qiguliuxing.dts.admin.shiro;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.qiguliuxing.dts.core.util.bcrypt.BCryptPasswordEncoder;
import com.qiguliuxing.dts.db.domain.DtsAdmin;
import com.qiguliuxing.dts.db.service.DtsAdminService;
import com.qiguliuxing.dts.db.service.DtsPermissionService;
import com.qiguliuxing.dts.db.service.DtsRoleService;

/**
 * 授权相关服务-shiro
 * 
 * @author darenzai
 * @since 1.0.0
 */
public class AdminAuthorizingRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(AdminAuthorizingRealm.class);
	@Autowired
	private DtsAdminService adminService;
	@Autowired
	private DtsRoleService roleService;
	@Autowired
	private DtsPermissionService permissionService;

	//该方法主要是用于当前登录用户授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {


			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		//能进入到这里，表示账号已经通过验证了(到这一步说身份验证通过，获取身份)
		DtsAdmin admin = (DtsAdmin) getAvailablePrincipal(principals);
		//进行权限验证
		Integer[] roleIds = admin.getRoleIds();
		//到数据查询角色Id 例如【1】
		Set<String> roles = roleService.queryByIds(roleIds);
		//查询这个角色都有什么
		Set<String> permissions = permissionService.queryByRoleIds(roleIds);

		//授权权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//把查出来的角色和权限都放进去
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}


	// 对发送进来的用户名 和 密码 进行验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取账号密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());
		//账号密码进行非空校验
		if (StringUtils.isEmpty(username)) {
			throw new AccountException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountException("密码不能为空");
		}

		//查询数据库账号密码
		List<DtsAdmin> adminList = adminService.findAdmin(username);
		//进行断言 是否存在两个用户名  安全策略
		Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
		//如果用户不存在抛出异常
		if (adminList.size() == 0) {
			logger.error("找不到用户（" + username + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
		}
		//拿到第一个查出来的用户
		DtsAdmin admin = adminList.get(0);

		//进行 BCryptPasswordEncoder解密 然后跟密码进行校验
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//如果不匹配抛出异常
		if (!encoder.matches(password, admin.getPassword())) {
			logger.error("找不到用户（" + username + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
		}
		//认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
		return new SimpleAuthenticationInfo(admin, password, getName());
	}

}
