package com.ycsys.smartmap.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.dao.LogDao;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.LogService;
import com.ycsys.smartmap.sys.util.ActionContext;
import com.ycsys.smartmap.sys.util.NetWorkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志service
 *
 * @author lixiaoxin
 * @date 2015年1月14日
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Resource
    private LogDao logDao;

    /**
     * 批量删除日志
     *
     * @param idList
     */
    public void deleteLog(List<Integer> idList) {
        logDao.deleteBatch(idList);
    }

    /***
     * 查询所有日志
     * */
    @Override
    public List<Log> findAll(PageHelper page) {
        return logDao.find("from Log order by createTime desc", page);
    }

    @Transactional
    @Override
    public void save(Log log) {
        logDao.save(log);
    }

    /**
     * 保存日志
     *
     * @param name     日志内容，标题
     * @param type     日志类型
     * @param remark   备注
     * @param status   状态
     * @param res      响应结果
     * @param usedTime 耗费时间
     **/
    @Override
    public void saveLogInfo(String name, LogType type, String remark, int status, Object res, long usedTime) {
        ActionContext context = ActionContext.getContext();
        HttpServletRequest request = context.getRequest();
        User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
        String ip = NetWorkUtil.getIpAddress(request);
        Log log = new Log();
        log.setRequestIp(ip);
        log.setCreateTime(new Date());
        log.setOperationName(name);
        log.setOperationType(type.getType());
        log.setRemark(remark);
        String request_params = JSONObject.toJSONString(request.getParameterMap());
        log.setRequest_params(request_params);
        log.setResponse_params(JSONObject.toJSONString(res));
        log.setStatus(status);
        log.setUsername(user.getName());
        log.setUser(user);
        log.setUsedTime(usedTime);
        logDao.save(log);
    }

    @Override
    public long countAll() {
        return logDao.count("select count(*) from Log");
    }


	@Override
	public Object[] findArrValue(String hql, List<Object> params, Integer page,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return logDao.findArrValue(hql, params, page, pageSize);
	}

    @Override
    public List<Log> findBySolution(Date date, Date date1, int operationType, int status, int num) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setPage(1);
        if(num == -1){
            num = 500;
        }else if(num > 10000){
            num = 10000;
        }
        pageHelper.setPagesize(num);
        List<Object> pl = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("from Log where 1=1 ");
        if(date!= null){
            pl.add(date);
            pl.add(date1);
            sb.append(" and createTime >= ? and createTime <= ?");
        }
        if(operationType != -1){
            pl.add(operationType);
            sb.append(" and operationType = ?");
        }
        if(status != -1){
            pl.add(status);
            sb.append(" and status = ?");
        }
        sb.append(" order by createTime desc");
        return logDao.find( sb.toString(),pl.toArray(),pageHelper);
    }

}
