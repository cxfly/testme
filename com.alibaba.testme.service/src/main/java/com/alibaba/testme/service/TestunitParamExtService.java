package com.alibaba.testme.service;

import java.util.List;

import com.alibaba.testme.domain.dataobject.TestunitParamExtDO;

/**
 * TestunitParamExt Service Interface
 * 
 * @author xiaopenzi
 */
public interface TestunitParamExtService {

    /**
     * @param testunitParamExtDO
     * @return
     */
    public Long addTestunitParamExtDO(TestunitParamExtDO testunitParamExtDO);

    /**
     * @param testunitParamExtDOList
     * @return
     */
    public void batchSaveTestunitParamExtDO(List<TestunitParamExtDO> testunitParamExtDOList);

    /**
     * @param DO
     * @return
     */
    public int updateTestunitParamExtDO(TestunitParamExtDO testunitParamExtDO);

    /**
     * @param id
     * @return
     */
    public int deleteTestunitParamExtDO(Long id);

    /**
     * @param id
     * @return
     */
    public TestunitParamExtDO findById(Long id);

    /**
     * @param id
     * @return
     */
    public List<TestunitParamExtDO> findList(TestunitParamExtDO testunitParamExtDO);

}
