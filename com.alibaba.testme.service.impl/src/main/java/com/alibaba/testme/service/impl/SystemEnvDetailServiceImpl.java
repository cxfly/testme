package com.alibaba.testme.service.impl;

import java.util.List;

import com.alibaba.testme.dao.SystemEnvDetailDao;
import com.alibaba.testme.domain.dataobject.SystemEnvDetailDO;
import com.alibaba.testme.service.SystemEnvDetailService;

/**
 * SystemEnvDetail Service Implement
 * 
 * @author xiaopenzi
 */
public class SystemEnvDetailServiceImpl implements SystemEnvDetailService {

    private SystemEnvDetailDao systemEnvDetailDao;

    public void setSystemEnvDetailDao(SystemEnvDetailDao systemEnvDetailDao) {
        this.systemEnvDetailDao = systemEnvDetailDao;
    }

    /**
     * @param systemEnvDetailDO
     * @return
     */
    @Override
    public int addSystemEnvDetailDO(SystemEnvDetailDO systemEnvDetailDO) {
        if (systemEnvDetailDO == null) {
            return 0;
        }
        return systemEnvDetailDao.addSystemEnvDetailDO(systemEnvDetailDO);
    }

    /**
     * @param systemEnvDetailDO
     * @return
     */
    @Override
    public int updateSystemEnvDetailDO(SystemEnvDetailDO systemEnvDetailDO) {
        if (systemEnvDetailDO == null) {
            return 0;
        }
        return systemEnvDetailDao.updateSystemEnvDetailDO(systemEnvDetailDO);

    }

    /**
     * @param id
     * @return
     */
    @Override
    public int deleteSystemEnvDetailDO(Integer id) {
        if (id == null || id == 0L) {
            return 0;
        }
        return systemEnvDetailDao.deleteSystemEnvDetailDO(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public SystemEnvDetailDO findById(Integer id) {
        if (id == null || id == 0L) {
            return null;
        }
        return systemEnvDetailDao.findById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<SystemEnvDetailDO> findList(SystemEnvDetailDO systemEnvDetailDO) {
        return systemEnvDetailDao.findList(systemEnvDetailDO);
    }

}
