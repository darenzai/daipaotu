package com.qiguliuxing.dts.admin.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.qiguliuxing.dts.admin.shiro.AdminAuthorizingRealm;
import com.qiguliuxing.dts.admin.shiro.AdminWebSessionManager;

@Configuration
public class ShiroConfig {

	@Bean
	public Realm realm() {
		return new AdminAuthorizingRealm();
	}

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/demo/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/v2/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");

		filterChainDefinitionMap.put("/admin/auth/401", "anon");
		filterChainDefinitionMap.put("/admin/auth/index", "anon");
		filterChainDefinitionMap.put("/admin/auth/403", "anon");

		filterChainDefinitionMap.put("/admin/**", "anon");
		shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
		shiroFilterFactoryBean.setSuccessUrl("/admin/auth/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/admin/auth/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SessionManager sessionManager() {
		AdminWebSessionManager mySessionManager = new AdminWebSessionManager();
		return mySessionManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}
}
