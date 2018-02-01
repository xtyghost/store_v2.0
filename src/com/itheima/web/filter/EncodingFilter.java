package com.itheima.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 一个用于参数的中文乱码解决的filter
 */
public class EncodingFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public EncodingFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		final HttpServletRequest requet1 = (HttpServletRequest) request;
		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(),
				request.getClass().getInterfaces(), new InvocationHandler() {
					private boolean hasEncode = false;

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						if ("getParameterMap".equals(method.getName())) {
							if ("post".equalsIgnoreCase(requet1.getMethod())) {
								requet1.setCharacterEncoding("utf-8");
								return method.invoke(requet1, args);
							} else if ("get".equalsIgnoreCase(requet1.getMethod())) {
								return getParameterMap();
							}
						}
						if ("getParameter".equals(method.getName())) {
							if ("post".equalsIgnoreCase(requet1.getMethod())) {
								requet1.setCharacterEncoding("utf-8");
								return method.invoke(requet1, args);
							} else if ("get".equalsIgnoreCase(requet1.getMethod())) {
								return getParameter(args[0]);
							}
						}
						if ("getParameterValues".equals(method.getName())) {
							if ("post".equalsIgnoreCase(requet1.getMethod())) {
								requet1.setCharacterEncoding("utf-8");
								return method.invoke(requet1, args);
							} else if ("get".equalsIgnoreCase(requet1.getMethod())) {
								return getParameterValues(args[0]);
							}
						}
						return method.invoke(requet1, args);
					}

					private String[] getParameterValues(Object object) {
						// TODO Auto-generated method stub
						Map<String, String[]> map = getParameterMap();
						if (map.containsKey(object)) {
							return map.get(object);
						}
						return null;
					}

					private String getParameter(Object object) {
						// TODO Auto-generated method stub
						String[] values = getParameterValues(object);
						if (values != null) {
							return values[0];
						}
						return null;
					}

					private Map<String, String[]> getParameterMap() {
						// TODO Auto-generated method stub
						Map<String, String[]> map = requet1.getParameterMap();
						if (!hasEncode) {
							Set<Entry<String, String[]>> entrySet = map.entrySet();
							for (Entry<String, String[]> entry : entrySet) {
								String[] value = entry.getValue();
								for (int i = 0; i < value.length; i++) {
									try {
										value[i] = new String(value[i].getBytes("iso-8859-1"), "utf-8");
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}

						return map;
					}

				});

		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
