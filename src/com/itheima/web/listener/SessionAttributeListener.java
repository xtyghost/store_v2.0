package com.itheima.web.listener;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;

/**
 * Application Lifecycle Listener implementation class SessionAttributeListener
 *
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {

	/**
	 * Default constructor.
	 */
	public SessionAttributeListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		if (se.getValue() instanceof User) {
			HttpSession session = (HttpSession) se.getSource();
			// 从session中获取为提交订单
			Cart cart = (Cart) session.getAttribute("cart");
			// 调用方法存入数据库
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			if (cart != null) {
				os.saveCart(cart);
			}
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 * 
	 */
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		Object value = se.getValue();
		if (value instanceof User) {
			HttpSession session = se.getSession();
			User user = (User) session.getAttribute("exsitUser");
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Cart cart = os.takeCart(user);
			if (cart != null) {
				// 获取未登录之前的cart
				Cart cart1 = (Cart) session.getAttribute("cart");
				if (cart1 == null) {

					session.setAttribute("cart", cart);
				} else {
					cart1.setUser(user);
					// 内存中购物车
					Map<String, CartItem> map = cart1.getMap();
					// 登录前的购物车
					Map<String, CartItem> map2 = cart.getMap();
					Set<Entry<String, CartItem>> entrySet = map2.entrySet();
					for (Entry<String, CartItem> entry : entrySet) {
						if (map.containsKey(entry.getKey())) {
							int count1 = map.get(entry.getKey()).getCount();
							int count2 = entry.getValue().getCount();
							entry.getValue().setCount(count2 + count1);
							//将相同物品的和存入订单项
							map.put(entry.getKey(), entry.getValue());
						} else {
							map.put(entry.getKey(), entry.getValue());
						}
					}
					cart1.setMap(map);
					session.setAttribute("cart", cart1);
				}
			} else {
				// 没有上次的cart
				Cart cart1 = (Cart) session.getAttribute("cart");
				cart1.setUser(user);
			}
		}
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
	}

}
