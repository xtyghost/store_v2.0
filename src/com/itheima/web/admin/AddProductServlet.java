package com.itheima.web.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;

/**
 * 处理商品信息添加的servlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 创建磁盘文件项工程
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置缓存文件
		factory.setSizeThreshold(1024 * 1024 * 8);// 8m内存
		factory.setRepository(new File(request.getContextPath() + "/repository.txt"));
		// 解析requst从中拿去文件项
		//设置文件大小
		// 解决中乱码
		// 创建servletfileupload对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1024*1024*1024);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> list;
		try {
			list = upload.parseRequest(request);
			Product product = new Product();
			for (FileItem fileItem : list) {
				// 判断
				if (fileItem.isFormField()) {
					// 普通项
					// 获取普通项的name
					String name = fileItem.getFieldName();
					// 解决普通项的乱码
					String value = fileItem.getString("utf-8");
					if ("pname".equals(name)) {
						product.setPname(value);
					}
					if ("market_price".equals(name)) {
						product.setMarket_price(Integer.parseInt(value));
					}
					if ("shop_price".equals(name)) {
						product.setShop_price(Integer.parseInt(value));
					}
					if ("is_hot".equals(name)) {
						product.setIs_hot(Integer.parseInt(value));
					}
					if ("pdesc".equals(name)) {
						product.setPdesc(value);
					}
					if ("cid".equals(name)) {
						product.setCid(Integer.parseInt(value));
						System.out.println(value);
					}

				} else {
					// 文件项
					String name = fileItem.getName();
					product.setPimage( "products/1/" +name);
					String[] hz = { "png", "jpg" };
					List<String> list2 = Arrays.asList(hz);
					int i = name.lastIndexOf(".");
					String hz1 = name.substring(i + 1, name.length());
					if (list2.contains(hz1)) {
						// 文件上上传
						InputStream stream = fileItem.getInputStream();
						OutputStream os = new FileOutputStream(
								request.getServletContext().getRealPath("products") + "/1/" + name);
						byte[] barr = new byte[1024];
						int len;
						while ((len = stream.read(barr)) != -1) {
							os.write(barr, 0, len);
						}
						os.close();
						stream.close();
					}
				}
			}
			// 调用底层方法完成文件添加
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.addProduct(product);
		} catch (FileUploadException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.sendRedirect("/store_v2.0/AdminProductServelet?method=chekproduct&currPage=1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
