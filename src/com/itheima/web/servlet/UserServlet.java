package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MailUtils;
import com.itheima.store.utils.MyDateConverter;
import com.itheima.store.utils.UUIDUtils;

/**
 * 一个用户模块的servlet
 */
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 跳转都注册页面的的方法
	 * 
	 * @return
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) {
		return "jsp/register.jsp";
	}

	/**
	 * 跳转都登录页面的的方法
	 * 
	 * @return
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) {
		return "jsp/login.jsp";
	}

	/**
	 * 跳转都购物栏页面的的方法
	 * 
	 * @return
	 */
	public String cartUI(HttpServletRequest req, HttpServletResponse resp) {
		return "jsp/cart.jsp";
	}

	/**
	 * 一个用户异步校验用户名是否可用的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	public String checkUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 从表单获取要查询的用户信息
		String name = req.getParameter("name");
		if (name != null && !"".equals(name) && name.length() <= 32 && !name.contains("傻逼")) {
			boolean flag;
			try {
				UserService us = (UserService) new BeanFactory().getBean("UserService");
				flag = us.checkUserName(name);
				if (flag) {
					// 查到了
					resp.getWriter().print(1);
				} else {
					// 查不到
					resp.getWriter().print(0);
				}
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			resp.getWriter().print(1);
		}
		return null;
	}

	/**
	 * 一个用户退出的方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String exit(HttpServletRequest req, HttpServletResponse resp) {
		// 从表单获取要查询的用户信息
		req.getSession().removeAttribute("exsitUser");
		// 清除cookie中的自动登录信息
		Cookie cookie = new Cookie("autoLogin", null);
		cookie.setPath("/store_v2.0");
		cookie.setMaxAge(0);
		// 将cookie信息返给navigation
		resp.addCookie(cookie);
		//交监听器保存未提交啊的购物车
		req.getSession().removeAttribute("exsitUser");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将session中的数据清空防止下一个用户看到
		req.getSession().invalidate();
		return "index.jsp";
	}

	/**
	 * 一个关于用户注册的方法
	 * 
	 * @throws IOException
	 * 
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
		// 判断验证码是否正确
		if (!req.getParameter("testcode")
				.equalsIgnoreCase((String) req.getSession().getAttribute("checkcode_session"))) {
			// 安全起见清空session
			req.getSession().removeAttribute("checkcode_session");
			req.setAttribute("testmsg", "验证码输入错误");

			return "/jsp/register.jsp";
		}
		String token = (String) req.getSession().getAttribute("token");
		if (token.equals(req.getParameter("token"))) {
			// email的校验
			if (!req.getParameter("email").matches("\\w*@store.com")) {
				req.setAttribute("emsg", "邮箱格式有误");

				return "/jsp/register.jsp";
			}
			// 性别的校验
			if (!req.getParameter("sex").equals("男") && !req.getParameter("sex").equals("女")) {
				req.setAttribute("esex", "总想搞个大新闻");
				return "/jsp/register.jsp";
			}
			// 校验密码长度
			if (req.getParameter("password").length() > 10) {
				req.setAttribute("epassword", "密码长度不可以大于20位");
				return "/jsp/register.jsp";
			}
			// 电话号码的校验
			if (req.getParameter("telephone").length() != 11 || !req.getParameter("telephone").matches("\\d{11}")) {
				req.setAttribute("etelephone", "非法的电话号码");
				return "/jsp/register.jsp";
			}
			// 清空token数据保证只能用一次
			req.getSession().removeAttribute("token");
			Map<String, String[]> map = req.getParameterMap();
			User user = new User();
			try {
				// 注册一个类型转换器
				ConvertUtils.register(new MyDateConverter(), Date.class);
				BeanUtils.populate(user, map);
				user.setUid(UUIDUtils.getUuid());
				user.setCode(UUIDUtils.getUuid() + UUIDUtils.getUuid());
				// 设置激活状态
				user.setState(1);
				UserService us = (UserService) new BeanFactory().getBean("UserService");
				us.regist(user);
				// 发送激活邮件
				MailUtils.sendMail(user.getEmail(), user.getCode());
				// 向任务页面跳转
				req.setAttribute("msg", "用户注册成功,请尽快到邮箱激活");
				return "jsp/msg.jsp";
			} catch (IllegalAccessException | InvocationTargetException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		} else {
			return "jsp/msg.jsp";
		}

	}

	/**
	 * 这是一个用户激活的方法
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) {
		String code = req.getParameter("code");
		// 调用service层先根据code进行激活
		if (code != null) {
			boolean flag;
			try {
				UserService us = (UserService) new BeanFactory().getBean("UserService");
				flag = us.active(code);
				if (flag) {
					req.setAttribute("msg", "激活成功,请尽快进行您的第一次登陆!");
				} else {
					req.setAttribute("msg", "激活失败,激活信息已经过期,请再次注册");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return "jsp/msg.jsp";

	}

	/**
	 * 一个关于用户登录的方法
	 * 
	 * @throws IOException
	 * 
	 */
	public String Login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 判断验证码是否正确
		if (!req.getParameter("testcode")
				.equalsIgnoreCase((String) req.getSession().getAttribute("checkcode_session"))) {
			// 安全起见清空session
			req.getSession().removeAttribute("checkcode_session");
			req.setAttribute("testmsg", "验证码输入错误");
			return "/jsp/login.jsp";
		}
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		// 调用底层方法查询用户是否存在
		User exsitUser;
		try {
			UserService us = (UserService) new BeanFactory().getBean("UserService");
			exsitUser = us.login(username, password);
			if (exsitUser != null) {
				// 将查询到的用户存入session
				req.getSession().setAttribute("exsitUser", exsitUser);
				// 自动登录
				String autoLogin = req.getParameter("autoLogin");
				if ("yes".equals(autoLogin)) {
					String autoLogin1 = URLEncoder.encode(exsitUser.getUsername() + "#" + exsitUser.getPassword(),
							"utf-8");
					Cookie cookie = new Cookie("autoLogin", autoLogin1);
					// 只有访问本网站时带人cookie信息
					cookie.setPath("/store_v2.0");
					// 保存一个月
					cookie.setMaxAge(60 * 60 * 24 * 30);
					cookie.setHttpOnly(true);
					// 将cookie存入client
					resp.addCookie(cookie);
				}
				// 记住用户name
				if ("yes".equals(req.getParameter("remenbername"))) {
					Cookie cookie = new Cookie("remenbername", URLEncoder.encode(exsitUser.getUsername(), "utf-8"));
					cookie.setPath("/store_v2.0");
					// 保存7天
					cookie.setMaxAge(60 * 60 * 24 * 7);
					resp.addCookie(cookie);
				}
				resp.sendRedirect("/store_v2.0/index.jsp");
				// 跳转到主页60*60*24
				return null;
			} else {
				req.setAttribute("msg", "用户名或密码错误");
				return "/jsp/login.jsp";
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 一个验证码的功能向验证码页面跳转
	 * 
	 */

	public String testcode(HttpServletRequest req, HttpServletResponse resp) {

		return "/CheckImgServlet";

	}

}
