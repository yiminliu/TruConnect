package com.trc.manager;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.trc.dao.UserDao;
import com.trc.exception.management.AccountManagementException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.user.AnonymousUser;
import com.trc.user.User;
import com.trc.user.authority.Authority;
import com.trc.user.authority.ROLE;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.context.SecurityContextFacade;
import com.trc.web.session.SessionManager;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.TruConnect;

@Service
public class UserManager implements UserManagerModel {
	public static final String USER_KEY = "user";
	public static final String CONTROLLING_USER_KEY = "controlling_user";
	public static SecurityContextFacade securityContext;
	private UserDao userDao;
	private AccountManager accountManager;
	private TruConnect port;

	@Autowired
	public void init(UserDao userDao, AccountManager accountManager, SecurityContextFacade securityContextFacade, TruConnectGateway truConnectGateway) {
		this.userDao = userDao;
		this.accountManager = accountManager;
		this.port = truConnectGateway.getPort();
		securityContext = securityContextFacade;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Transactional(readOnly = true)
	public List<String> getAllUserNames() {
		return userDao.getAllUserNames();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllAdmins() {
		return userDao.getAllUsersWithRole(ROLE.ROLE_ADMIN.toString());
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllManagers() {
		return userDao.getAllUsersWithRole(ROLE.ROLE_MANAGER.toString());
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllServiceReps() {
		return userDao.getAllUsersWithRole(ROLE.ROLE_SERVICEREP.toString());
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> searchByEmail(String email) {
		return userDao.searchByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> searchById(int id) {
		return userDao.searchById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User searchByAccountNo(int accountNo) {
		Account account = port.getAccountInfo(accountNo);
		List<User> users = searchByEmail(account.getContactEmail());
		return users != null && users.size() > 0 ? users.get(0) : new User();
	}

	@Transactional(readOnly = true)
	public List<User> searchByEmailAndDate(String email, DateTime startDate, DateTime endDate) {
		return userDao.searchByEmailAndDate(email, startDate, endDate);
	}

	@Transactional(readOnly = true)
	public List<User> searchByNotEmailAndDate(String email, DateTime startDate, DateTime endDate) {
		return userDao.searchByNotEmailAndDate(email, startDate, endDate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> searchByUsername(String username) {
		return userDao.searchByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> search(String param) {
		return userDao.search(param);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = securityContext.getContext().getAuthentication();
		boolean isAnon1 = authentication == null || !(authentication.getPrincipal() instanceof UserDetails);
		boolean isAnon2 = authentication == null || isAnonymousUser(authentication);
		if (isAnon2) {
			return new AnonymousUser();
		} else {
			return (User) authentication.getPrincipal();
		}
	}

	private boolean isAnonymousUser(Authentication authentication) {
		return authentication.getName().equals("anonymousUser");
	}

	@Override
	public User getCurrentUser() {
		return getSessionUser();
	}

	@Override
	@Transactional(readOnly = false)
	public Serializable saveUser(User user) {
		if (user.getAuthorities().isEmpty()) {
			user.getRoles().add(new Authority(user, ROLE.ROLE_USER));
		}
		return userDao.saveUser(user);
	}

	@Override
	@Transactional
	public void saveOrUpdateUser(User user) {
		userDao.saveOrUpdateUser(user);
	}

	@Override
	@Transactional
	public void persistUser(User user) {
		userDao.persistUser(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	@Override
	@Transactional(readOnly = false)
	@PreAuthorize("isAuthenticated() and hasPermission(#user, 'canUpdate')")
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void enableUser(User user) {
		userDao.enableUser(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void disableUser(User user) {
		userDao.disableUser(user);
	}

	@Override
	public boolean isUsernameAvailable(String username) {
		return getUserByUsername(username) == null;
	}

	@Override
	public boolean isEmailAvailable(String email) {
		return getUserByEmail(email) == null;
	}

	public User getSessionUser() {
		User user = (User) SessionManager.get(USER_KEY);
		return user == null ? new AnonymousUser() : user;
	}

	public void setSessionUser(User user) {
		SessionManager.set(USER_KEY, user);
	}

	public void setSessionControllingUser(User user) {
		SessionManager.set(CONTROLLING_USER_KEY, user);
	}

	public User getSessionControllingUser() {
		return (User) SessionManager.get(CONTROLLING_USER_KEY);
	}

	@Loggable(value = LogLevel.TRACE)
	public void getUserRealName(User user) {
		if (user.isAdmin()) {
			user.getContactInfo().setFirstName(user.getUsername());
			user.getContactInfo().setLastName("Administrator");
		} else if (user.isManager()) {
			user.getContactInfo().setFirstName(user.getUsername());
			user.getContactInfo().setLastName("Manager");
		} else if (user.isUser()) {
			try {
				CustInfo custInfo = accountManager.getCustInfo(user);
				if (custInfo != null) {
					user.getContactInfo().setFirstName(custInfo.getFirstName());
					user.getContactInfo().setLastName(custInfo.getLastName());
				} else {
					user.getContactInfo().setFirstName(user.getUsername());
				}
			} catch (AccountManagementException e) {
				user.getContactInfo().setFirstName(user.getUsername());
			}
		} else {
			user.getContactInfo().setFirstName(user.getUsername());
		}
	}

	public void autoLogin(User user, AuthenticationManager authenticationManager) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		autoLogin(user, request, authenticationManager);
	}

	public void autoLogin(User user, HttpServletRequest request, AuthenticationManager authenticationManager) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		// setting role to the session
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	}

}