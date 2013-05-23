/**
 * Project: com.alibaba.testme.core
 * 
 * File Created at 2013-5-23
 * $Id$
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.testme.core.testunitflow.context;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.testme.core.testunitflow.dto.ParamsMatchCheckResult;

/**
 * 测试单元定义的参数管理器
 * 
 * @author chongan.wangca
 */
public class TestunitDefParamsManager {

    private Map<String, TestunitDefParam> testunitDefParamsMap = new HashMap<String, TestunitDefParam>();

    public void addParam(TestunitDefParam testunitDefParam) {
        this.testunitDefParamsMap.put(testunitDefParam.getParamName(), testunitDefParam);
    }

    public Map<String, TestunitDefParam> getTestunitDefParamsMap() {
        return testunitDefParamsMap;
    }

    public void setTestunitDefParamsMap(Map<String, TestunitDefParam> testunitDefParamsMap) {
        this.testunitDefParamsMap = testunitDefParamsMap;
    }

    /**
     * 判断输入参数是否全部满足执行测试单元必需的参数
     * 
     * @param inputParams
     * @return
     */
    public ParamsMatchCheckResult isRequiredParamsMatch(InputParams inputParams) {
        ParamsMatchCheckResult paramsMatchCheckResult = new ParamsMatchCheckResult();
        for (Map.Entry<String, TestunitDefParam> entry : testunitDefParamsMap.entrySet()) {
            TestunitDefParam testunitDefParam = entry.getValue();
            //只校验必需参数
            if (testunitDefParam.isRequired()) {
                boolean isHas = inputParams.hasParam(testunitDefParam.getParamName());
                if (!isHas) {
                    paramsMatchCheckResult.addAbsentParam(testunitDefParam);
                }
            }
        }
        return paramsMatchCheckResult;
    }

}