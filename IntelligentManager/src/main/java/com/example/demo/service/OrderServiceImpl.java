package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.mapper.DeviceMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.myexcept.MyExceptionHandler;
import com.example.demo.pojo.DValueEnum;
import com.example.demo.pojo.Device;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.PageRequest;
import com.example.demo.pojo.PageResult;
import com.example.demo.pojo.PageUtil;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	DeviceMapper deviceMapper;
	
	/**
	 * 添加订单
	 */
	@Override
	public Result<String> addOrder(Order order, HttpServletRequest request) {
		int cid = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.LOGIN_USER_ID.getValue()).toString());
		//int fid = Integer.parseInt(request.getSession().getAttribute(DValueEnum.CHECKED_FACTORY_ID.getValue()).toString());
		
		order.setClient_id(cid);
		//order.setFac_id(fid);
		order.generateOrd_name();
		order.setPay_status("未支付");
		order.setStatus("未进行");
		order.setProgress(0);
		
		orderMapper.insertOrder(order);
		return ResultUtil.successRes();
	}
	
	
	/**
	 * code:20000 删除成功
	 * code:30006 有设备已经开始生产
	 */ 
	@Override
	public Result<String> deleteOrder(HttpServletRequest request) {
		String ord_name = request.getParameter("ord_name");
		
		List<Device> deviceList = orderMapper.getDevicesByOrdName(ord_name);
		
		if (deviceList.size() == 0) {
			return ResultUtil.null_dataRes("设备为空");
		}
		
		for (Device item : deviceList) {
			
			if (item.getProgress() != 0) {
				return ResultUtil.data_not_allowedRes("有设备已经开始生产");
			}
			
			item.setOrd_id(null);
			item.clearDevice();
			//deviceMapper.updateDevice(item);
		}
		
		for (Device item : deviceList) {
			deviceMapper.updateDevice(item);
		}
		
		orderMapper.deleteOrderByOrdName(ord_name);
		
		return ResultUtil.successRes();
	}
	
	
	
	@Override
	public PageResult getOrdersOnPageByFacId(Map<String, String> requestMap, HttpServletRequest request) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNum(Integer.parseInt(requestMap.get("pageNum")));
		pageRequest.setPageSize(Integer.parseInt(requestMap.get("pageSize")));
		
		return PageUtil.getPageResult(pageRequest, getOrdersInfoByFacId(requestMap, request));
	}
	
	
	/**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
	private PageInfo<Order> getOrdersInfoByFacId(Map<String, String> requestMap, HttpServletRequest request) {
		
		try {
			int pageNum = Integer.parseInt(requestMap.get("pageNum"));
			int pageSize = Integer.parseInt(requestMap.get("pageSize"));
			int fac_id = Integer.parseInt(requestMap.get("selectIndex"));
			String prod_name = null;
			
			if (requestMap.containsKey("condition")) {
				prod_name = requestMap.get("condition");
			}
			
			if (pageNum <= 0 || pageSize <= 0) {
				throw new IllegalArgumentException("非法分页数据");
			}
			
			List<Order> ordersList = null;
			PageHelper.startPage(pageNum, pageSize);
			if (prod_name == null || prod_name.equals("0")) {
				ordersList = orderMapper.getOrderByFacIdOnPage(fac_id);
			} else {
				ordersList = orderMapper.getOrderByFacIdAndProdnameOnPage(fac_id, prod_name);
			}
			
			
			return new PageInfo<Order>(ordersList);
		} catch (IllegalArgumentException e1) {
			MyExceptionHandler.takeLog(e1);
			return null;
		}
	}
	
	
	@Override
	public PageResult getAllOrdersOnPage(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNum(Integer.parseInt(requestMap.get("pageNum")));
		pageRequest.setPageSize(Integer.parseInt(requestMap.get("pageSize")));
		
		return PageUtil.getPageResult(pageRequest, getAllOrdersInfo(requestMap, request));
	}
	
	
	private PageInfo<Order> getAllOrdersInfo(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
		try {
			int pageNum = Integer.parseInt(requestMap.get("pageNum"));
			int pageSize = Integer.parseInt(requestMap.get("pageSize"));
			String prod_name = null;
			Integer cid = Integer.parseInt(request.getSession().
					getAttribute(DValueEnum.LOGIN_USER_ID.getValue()).toString());
			
			if (requestMap.containsKey("condition")) {
				prod_name = requestMap.get("condition");
			}
			
			if (pageNum <= 0 || pageSize <= 0) {
				throw new IllegalArgumentException("非法分页数据");
			}
			
			List<Order> ordersList = null;
			PageHelper.startPage(pageNum, pageSize);
			if (prod_name == null || prod_name.equals("0")) {
				ordersList = orderMapper.getAllOrders(cid);
			} else {
				ordersList = orderMapper.getAllOrdersByProdname(cid, prod_name);
			}
			
			return new PageInfo<Order>(ordersList);
		} catch (IllegalArgumentException e1) {
			MyExceptionHandler.takeLog(e1);
			return null;
		}
	}
	
	
	/**
	 * 增加订单
	 * code:20000 创建成功
	 * code:30006 订单重复
	 * code:10000 未知错误
	 */
	@Override
	public Result<String> addOrderToFactory(Order order, HttpServletRequest request) {
		try {
			//HttpSession session = request.getSession();
			//Integer fid = Integer.parseInt(session.getAttribute(DValueEnum.CHECKED_FACTORY_ID.getValue()).toString());
			
			Integer cid = Integer.parseInt(request.getSession().
					getAttribute(DValueEnum.LOGIN_USER_ID.getValue()).toString());
			
			//重新设定prodname和ordname
			String prodName = order.getOrd_name();
			order.setOrd_name(null);
			Product product = orderMapper.getProductByPordName(prodName);
			List<Device> deviceList = deviceMapper.findDeviceByFacAndDevId(order.getFac_id(), order.getDev_id());
			
			
			//计算需要多少台设备
			int devnum = order.getProd_num() / product.getNum();
			//是否是正好相等
			boolean equal_flag = true;
			if (product.getNum() * devnum < order.getProd_num()) {
				devnum += 1;
				equal_flag = false;
			}
			
			if (deviceList.size() < devnum) {
				return ResultUtil.data_not_allowedRes("没有可使用设备");
			}
			
			
			order.setClient_id(cid);
			order.setProd_id(product.getProduct_id());
			order.setProd_name(prodName);
			order.generateOrd_name();
			order.setPay_status("未支付");
			order.setStatus("未进行");
			order.setPrice(product.getPrice() * order.getProd_num());
			order.setProgress(0);
			//System.out.println(order.toString());
			orderMapper.insertOrder(order);
			
			
			for (int i = 0; i < devnum; i++) {
				deviceList.get(i).setOrd_id(order.getOrder_id());
				deviceList.get(i).setProd_id(product.getProduct_id());
				deviceList.get(i).setProdname(order.getProd_name());
				deviceList.get(i).setOrdname(order.getOrd_name());
				deviceList.get(i).setStatus("未运行");
				deviceList.get(i).setProgress(0);
				
				if (i != devnum - 1) {
					deviceList.get(i).setProd_num(product.getNum());
				} else {
					if (equal_flag) {
						//如果正好相等，那么最后一个设备仍然生产相同数量
						deviceList.get(i).setProd_num(product.getNum());
					} else {
						//否则最后一个设备只生产剩下的余量
						deviceList.get(i).setProd_num(order.getProd_num() - product.getNum() * (devnum-1));
					}
				}

				deviceMapper.updateDevice(deviceList.get(i));
			}
			
			
			
//			if (device.getOrd_id() != null) {
//				return ResultUtil.data_not_allowedRes("设备正在使用");
//			}
//			
//			//处理订单
//			//order.setFac_id(fid);
//			order.setClient_id(cid);
//			order.setOrder_id(device.getCid());
//			order.setProd_id(product.getProduct_id());
//			order.setProd_name(prodName);
//			order.generateOrd_name();
//			order.setPay_status("未支付");
//			order.setStatus("未进行");
//			order.setPrice(product.getPrice() * order.getProd_num());
//			order.setProgress(0);
//			//System.out.println(order.toString());
//			orderMapper.insertOrder(order);
//			
//			//处理设备
//			device.setOrd_id(order.getOrder_id());
//			device.setProd_id(product.getProduct_id());
//			device.setProd_num(order.getProd_num());
//			device.setProdname(order.getProd_name());
//			device.setOrdname(order.getOrd_name());
//			device.setStatus("未运行");
//			device.setProgress(0);
//			deviceMapper.updateDevice(device);
			
			return ResultUtil.successRes();
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return ResultUtil.unknown_errorReult("错误");
		}
	}
	
	
	/**
	 * code:30005:没找到订单
	 */
	@Override
	public Result<String> changeOrder(HttpServletRequest request) {
		String ord_name = request.getParameter("ord_name");
		String description = request.getParameter("description");
		String receiver = request.getParameter("receiver");
		String phone = request.getParameter("phone");
		
		Order order = orderMapper.getOrderByOrdName(ord_name);
		
		//System.out.println(description);
		//System.out.println(receiver);
		//System.out.println(phone);
 		
		if (order == null) {
			return ResultUtil.data_not_foundRes("未找到订单");
		}
		
		if (description.length() > 0) {
			order.setDescription(description);
		} else if (receiver.length() > 0) {
			order.setReceiver(receiver);
		} else if (phone.length() > 0) {
			order.setPhone(phone);
		}
		
		orderMapper.updateOrder(order);
		return ResultUtil.successRes();
	}
	
	
	
	@Override
	public Result<String> deliveResultOrder(HttpServletRequest request) {
		String ord_name = request.getParameter("ord_name");
		
		Order order = orderMapper.getOrderByOrdName(ord_name);
		
		if (order == null) {
			return ResultUtil.null_dataRes("未找到订单");
		}
		
		if (order.getProgress() != 100 || order.getStatus() != "待交付") {
			return ResultUtil.data_not_allowedRes("交付失败");
		}
		
		orderMapper.deleteOrderByOrdName(ord_name);
		return ResultUtil.successRes();
	}
	
}
