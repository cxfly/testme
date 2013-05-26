/**
 * Project: com.alibaba.testme.core
 * 
 * File Created at 2013-5-22
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
package com.alibaba.testme.core.testunitflowcase.context;

import java.util.Date;

/**
 * 测试实例上下文
 * 
 * @author chongan.wangca
 */
public class TestunitFlowCaseContext {

    //测试实例ID
    private Long                     testunitFlowCaseId;

    //测试实例状态
    private String                   testunitFlowCaseStatus;

    //用户ID
    private Long                     userId;

    //测试实例节点ID
    private Long                     testunitFlowCaseDetailId;

    //测试流程节点ID
    private Long                     testunitFlowDetailId;

    //测试实例节点状态
    private String                   testunitFlowCaseDetailStatus;

    //输入参数
    private InputParams              inputParams;

    //测试单元定义管理器
    private TestunitDefParamsManager testunitDefParamsManager = new TestunitDefParamsManager();

    //测试单元类路径
    private String                   testunitClassQualifiedName;

    //开始测试单元开始执行时间
    private Date                     gmtStart;

    //系统环境变量
    private Long                     systemEnvId;

    public String getTestunitFlowCaseStatus() {
        return testunitFlowCaseStatus;
    }

    public void setTestunitFlowCaseStatus(String testunitFlowCaseStatus) {
        this.testunitFlowCaseStatus = testunitFlowCaseStatus;
    }

    public String getTestunitFlowCaseDetailStatus() {
        return testunitFlowCaseDetailStatus;
    }

    public void setTestunitFlowCaseDetailStatus(String testunitFlowCaseDetailStatus) {
        this.testunitFlowCaseDetailStatus = testunitFlowCaseDetailStatus;
    }

    public InputParams getInputParams() {
        return inputParams;
    }

    public void setInputParams(InputParams inputParams) {
        this.inputParams = inputParams;
    }

    public String getTestunitClassQualifiedName() {
        return testunitClassQualifiedName;
    }

    public void setTestunitClassQualifiedName(String testunitClassQualifiedName) {
        this.testunitClassQualifiedName = testunitClassQualifiedName;
    }

    public TestunitDefParamsManager getTestunitDefParamsManager() {
        return testunitDefParamsManager;
    }

    public Long getTestunitFlowCaseId() {
        return testunitFlowCaseId;
    }

    public void setTestunitFlowCaseId(Long testunitFlowCaseId) {
        this.testunitFlowCaseId = testunitFlowCaseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTestunitFlowCaseDetailId() {
        return testunitFlowCaseDetailId;
    }

    public void setTestunitFlowCaseDetailId(Long testunitFlowCaseDetailId) {
        this.testunitFlowCaseDetailId = testunitFlowCaseDetailId;
    }

    public Long getTestunitFlowDetailId() {
        return testunitFlowDetailId;
    }

    public void setTestunitFlowDetailId(Long testunitFlowDetailId) {
        this.testunitFlowDetailId = testunitFlowDetailId;
    }

    public Date getGmtStart() {
        return gmtStart;
    }

    public void setGmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
    }

    public Long getSystemEnvId() {
        return systemEnvId;
    }

    public void setSystemEnvId(Long systemEnvId) {
        this.systemEnvId = systemEnvId;
    }

}