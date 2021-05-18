package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.DeviceMapper;
import com.example.demo.myexcept.MyExceptionHandler;
import com.example.demo.pojo.DValueEnum;
import com.example.demo.pojo.Device;
import com.example.demo.pojo.MyCommonFunc;
import com.example.demo.pojo.PageRequest;
import com.example.demo.pojo.PageResult;
import com.example.demo.pojo.PageUtil;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class DeviceServiceImpl implements DeviceService {
	
	
	@Autowired
	DeviceMapper deviceMapper;
	
	
	@Override
	public Result<Map<String, String>> findDeviceinfo(HttpServletRequest request) {
		int fac_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_FACTORY_ID.getValue()).toString());
		
		List<Device> deviceList = deviceMapper.findDevicesByFacId(fac_id);
		Map<String, String> deviceMaps = new HashMap<String, String>();
		
		if (deviceList == null) {
			return ResultUtil.data_not_foundRes(null);
		} else {
			for (Device item : deviceList) {
				deviceMaps.put(item.getDevname(), item.getDev_num().toString());
			}
			
			return ResultUtil.successRes(deviceMaps);
		}
	}
	
	
	@Override
	public Result<String> addDeviceToFactory(HttpServletRequest request) {
		try {
			//int fac_id = Integer.parseInt(request.getSession().getAttribute(DValueEnum.CHECKED_FACTORY_ID.getValue()).toString());
			String dname = request.getParameter("dname");
			int num = Integer.parseInt(request.getParameter("number"));
			int fac_id = Integer.parseInt(request.getParameter("facid"));
			int dev_id = deviceMapper.findDevIdByName(dname);
			Integer cid = Integer.parseInt(request.getSession().
					getAttribute(DValueEnum.LOGIN_USER_ID.getValue()).toString());
			//检测数据合法性
			if (num < 0 || num > 100) {
				return ResultUtil.invalid_data("非法数据");
			}
					
			Device device = deviceMapper.findDeviceByFacAndDevId(fac_id, dev_id);
			
			//如果工厂内没有该设备
			if (device == null) {
				device = new Device(dname, num, dev_id);
				device.setFac_id(fac_id);
				device.setClient_id(cid);
				deviceMapper.insertDevice(device);
			} else {
				//如果已经有该设备，则增加相应数量
				int tmpnum = device.getDev_num();
				device.setDev_num(tmpnum + num);
				deviceMapper.updateDevice(device);
			}
					
			return ResultUtil.successRes();
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return ResultUtil.data_not_foundRes("未找到对应设备");
		}
	}
	
	
	@Override
	public PageResult getDevicesOnPageByFacId(PageRequest pageRequest, HttpServletRequest request) {
		return PageUtil.getPageResult(pageRequest, getDevicesInfoByFacId(pageRequest, request));
	}
	
	
	private PageInfo<Device> getDevicesInfoByFacId(PageRequest pageRequest, HttpServletRequest request) {
		try {
			//HttpSession session = request.getSession();
			int pageNum = pageRequest.getPageNum();
			int pageSize = pageRequest.getPageSize();
			//int fac_id = Integer.parseInt(session.getAttribute(DValueEnum.CHECKED_FACTORY_ID.getValue()).toString());
			int fac_id = pageRequest.getSelectIndex();
			
			if (pageNum <= 0 || pageSize <= 0) {
				throw new IllegalArgumentException("非法分页数据");
			}
			
			PageHelper.startPage(pageNum, pageSize);
			List<Device> devicesList = deviceMapper.getDeviceByFacIdOnPage(fac_id);
			
			//for (Device item : devicesList) {
			//	System.out.println(item.toString());
			//}
			
			return new PageInfo<Device>(devicesList);
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return null;
		}
	}
	
	
	@Override
	public PageResult getAllDevicesOnPage(PageRequest pageRequest, HttpServletRequest request) {
		return PageUtil.getPageResult(pageRequest, getAllDevicesInfo(pageRequest, request));
	}
	
	
	private PageInfo<Device> getAllDevicesInfo(PageRequest pageRequest, HttpServletRequest request) {
		try {
			int pageNum = pageRequest.getPageNum();
			int pageSize = pageRequest.getPageSize();
			Integer cid = Integer.parseInt(request.getSession().
					getAttribute(DValueEnum.LOGIN_USER_ID.getValue()).toString());
			
			if (pageNum <= 0 || pageSize <= 0) {
				throw new IllegalArgumentException("非法分页数据");
			}
			
			PageHelper.startPage(pageNum, pageSize);
			List<Device> devicesList = deviceMapper.getAllDevices(cid);
			
			//for (Device item : devicesList) {
			//	System.out.println(item.toString());
			//}
			
			return new PageInfo<Device>(devicesList);
		} catch (Exception e) {
			MyExceptionHandler.takeLog(e);
			return null;
		}
	}
	
	
	@Override
	public Result<String> selectedDeviceSession(HttpServletRequest request) {
		//String fac_id = request.getParameter("factory_id");
		//String devname = request.getParameter("devname");
		String dev_id = request.getParameter("dev_id");
		HttpSession session = request.getSession();
		
		session.setAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue(), dev_id);
		
		//if (fac_id == null || devname == null || dev_id == null) {
		//	return ResultUtil.invalid_data("非法数据");
		//}
		
		//if (dev_id.equals("-1")) {
		//	Integer new_dev_id = deviceMapper.findDevIdByFacIdAndDevName(Integer.parseInt(fac_id), devname);
		//	session.setAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue(), new_dev_id);
		//} else {
		//	session.setAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue(), dev_id);
		//}
		
		return ResultUtil.successRes();
	}
	
	
	@Override
	public Result<String> delayStartProduce(HttpServletRequest request) {
		Integer dev_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue()).toString());
		
		if (dev_id == null || dev_id < 0) {
			return ResultUtil.data_not_foundRes("未找到设备");
		}
		
		
		String rdname = MyCommonFunc.getRandomString(6);
		String delaytime = request.getParameter("delaytime");
		String datestr = MyCommonFunc.getDelayDateOnSecond(Integer.parseInt(delaytime));	
		
		String sqlstr = "CREATE EVENT " + rdname +"\n"
				+ "ON SCHEDULE AT \""+ datestr + "\"\n"
				+ "ON COMPLETION NOT PRESERVE\n"
				+ "DO CALL start_produce(" + dev_id.toString() + ");";
		
		
		deviceMapper.delayStartProduce(sqlstr);
		
		System.out.println("delay start");
		System.out.println(sqlstr);
		
		return ResultUtil.successRes();
	}
	
	
	@Override
	public Result<String> straightStartProduce(HttpServletRequest request) {
		Integer dev_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue()).toString());
		
		if (dev_id == null || dev_id < 0) {
			return ResultUtil.data_not_foundRes("未找到设备");
		}
		
		deviceMapper.updateDeviceStatusByDevId("进行中", dev_id);
		
		System.out.println("straight start");
		
		return ResultUtil.successRes();
	}
	
	
	@Override
	public Result<String> delayStopProduce(HttpServletRequest request) {
		Integer dev_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue()).toString());
		
		if (dev_id == null || dev_id < 0) {
			return ResultUtil.data_not_foundRes("未找到设备");
		}
		
		
		String rdname = MyCommonFunc.getRandomString(6);
		String delaytime = request.getParameter("delaytime");
		String datestr = MyCommonFunc.getDelayDateOnSecond(Integer.parseInt(delaytime));	
		
		
		String sqlstr = "CREATE EVENT " + rdname +"\n"
				+ "ON SCHEDULE AT \""+ datestr + "\"\n"
				+ "ON COMPLETION NOT PRESERVE\n"
				+ "DO CALL stop_produce(" + dev_id.toString() + ");";
		
		
		deviceMapper.delayStopProduce(sqlstr);
		
		System.out.println("delay stop");
		System.out.println(sqlstr);

		return ResultUtil.successRes();
	}
	
	
	@Override
	public Result<String> straightStopProduce(HttpServletRequest request) {
		Integer dev_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue()).toString());
		
		if (dev_id == null || dev_id < 0) {
			return ResultUtil.data_not_foundRes("未找到设备");
		}
		
		deviceMapper.updateDeviceStatusByDevId("未运行", dev_id);
		
		System.out.println("straight stop");
		
		return ResultUtil.successRes();
	}
	
	
	@Override
	public Result<Map<String, String>> showDeviceProgressOnCharts(HttpServletRequest request) {
		Integer dev_id = Integer.parseInt(request.getSession().
				getAttribute(DValueEnum.CHECKED_DEVICE_ID.getValue()).toString());
		
		Device device = deviceMapper.findDeviceById(dev_id);
		
		if (device == null) {
			return ResultUtil.null_dataRes(null);
		}
		
		Map<String, String> deviceMaps = new HashMap<String, String>();
		deviceMaps.put("status", device.getStatus());
		deviceMaps.put("progress", device.getProgress().toString());
		
		return ResultUtil.successRes(deviceMaps);
	}
}
