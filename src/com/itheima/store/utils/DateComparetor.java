package com.itheima.store.utils;

import java.util.Comparator;

import com.itheima.store.domain.Product;
/**
 * 这是一个用于两个product比较先后的类
 * @author xt
 *
 */
public class DateComparetor implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		return -o1.getPdate().compareTo(o2.getPdate());
	}

	

}
