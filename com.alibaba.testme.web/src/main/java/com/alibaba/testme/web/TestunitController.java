/**
 * Project: com.alibaba.testme.web
 * 
 * File Created at 2013-6-9
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
package com.alibaba.testme.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.testme.common.constants.CommonConstants;
import com.alibaba.testme.common.enums.FormCtrlTypeEnum;
import com.alibaba.testme.common.ibatispage.Page;
import com.alibaba.testme.domain.dataobject.SystemDO;
import com.alibaba.testme.domain.dataobject.TestunitDO;
import com.alibaba.testme.domain.dataobject.TestunitParamDO;
import com.alibaba.testme.domain.dataobject.TestunitParamExtDO;
import com.alibaba.testme.domain.dataobject.WorkSpaceDO;
import com.alibaba.testme.domain.query.TestunitQuery;
import com.alibaba.testme.domain.vo.TestunitParamVO;
import com.alibaba.testme.domain.vo.TestunitVO;
import com.alibaba.testme.service.SystemService;
import com.alibaba.testme.service.TestunitParamExtService;
import com.alibaba.testme.service.TestunitParamService;
import com.alibaba.testme.service.TestunitService;
import com.alibaba.testme.service.WorkSpaceService;
import com.alibaba.testme.web.common.SessionUtils;

/**
 * 测试单元控制器
 * 
 * @author xiaopenzi
 */
@Controller
@RequestMapping(value = "/testunitmanage/*")
public class TestunitController {
    @Resource
    private WorkSpaceService        workSpaceService;
    @Resource
    private SystemService           systemService;
    @Resource
    private TestunitService         testunitService;
    @Resource
    private TestunitParamService    testunitParamService;
    @Resource
    private TestunitParamExtService testunitParamExtService;
    private static final Logger     logger = LoggerFactory.getLogger(TestunitController.class);

    //页面初始化
    private void init(Model model) {
        //获取工作空间列表
        List<WorkSpaceDO> workSpaceDOList = workSpaceService.findList(new WorkSpaceDO());
        //获取系统列表
        List<SystemDO> systemDOList = systemService.findList(new SystemDO());
        model.addAttribute("workSpaceDOList", workSpaceDOList);
        model.addAttribute("systemDOList", systemDOList);
    }

    //页面初始化
    private void init(Model model, Long systemId) {
        //获取系统列表
        List<SystemDO> systemDOList = systemService.findList(new SystemDO());
        //获取工作空间列表
        Long id = null;
        if (systemId != null && systemId > 0L) {
            id = systemId;
        } else if (systemDOList != null && systemDOList.size() > 0) {
            id = systemDOList.get(0).getId();
        }
        //获取表单控件类型
        List<FormCtrlTypeEnum> formCtrlTypeEnumList = FormCtrlTypeEnum.getList();
        WorkSpaceDO query = new WorkSpaceDO();
        query.setSystemId(id);
        List<WorkSpaceDO> workSpaceDOList = workSpaceService.findList(query);
        model.addAttribute("formCtrlTypeEnumList", formCtrlTypeEnumList);
        model.addAttribute("workSpaceDOList", workSpaceDOList);
        model.addAttribute("systemDOList", systemDOList);
    }

    /**
     * 进入测试单元页面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String testunitList(Model model, HttpServletRequest request) {
        //初始化页面
        init(model);
        return "testunitmanage/testunitList";
    }

    /**
     * 测试单元分页查询
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String testunitListByPage(Model model, HttpServletRequest request,
                                     @ModelAttribute("testunitQuery") TestunitQuery testunitQuery) {
        String resultMsg = null;
        if (testunitQuery == null) {
            testunitQuery = new TestunitQuery();
        }
        Page<TestunitVO> resultPage = testunitService.queryPage(testunitQuery.getPageIndex(),
                testunitQuery.getSizePerPage(), testunitQuery);
        if (resultPage == null) {
            resultMsg = "温馨提醒：异常原因，查询失败！";
        }
        model.addAttribute("userId", SessionUtils.getLoginUser(request).getId());
        model.addAttribute("testunitVOPage", resultPage);
        model.addAttribute("resultMsg", resultMsg);
        model.addAttribute("testunitQuery", testunitQuery);

        return testunitList(model, request);
    }

    /**
     * 进入测试单元详情页面
     * 
     * @param model
     * @param request
     * @param testunitId
     * @return
     */
    @RequestMapping
    public String testunitDetail(Model model, HttpServletRequest request,
                                 @RequestParam("testunitId") Long testunitId) {
        if (testunitId == null || testunitId <= 0L) {
            model.addAttribute("resultMsg", "温馨提醒：测试单元Id为空！");
            return testunitList(model, request);
        }
        TestunitVO testunitVO = testunitService.findTestunitVOById(testunitId);
        TestunitParamDO query = new TestunitParamDO();
        query.setTestunitId(testunitId);
        List<TestunitParamDO> paramDOList = testunitParamService.findList(query);
        if (paramDOList == null || paramDOList.size() == 0) {
            model.addAttribute("testunitVO", testunitVO);
            return "testunitmanage/testunitDetail";
        }
        List<TestunitParamVO> testunitParamVOList = new ArrayList<TestunitParamVO>();
        for (TestunitParamDO paramDO : paramDOList) {
            TestunitParamVO testunitParamVO = new TestunitParamVO();
            testunitParamVO.setDefaultValue(paramDO.getDefaultValue());
            testunitParamVO.setFormCtrlType(paramDO.getFormCtrlType());
            testunitParamVO.setHelp(paramDO.getHelp());
            testunitParamVO.setId(paramDO.getId());
            testunitParamVO.setIsRequired(paramDO.getIsRequired());
            testunitParamVO.setLabelName(paramDO.getLabelName());
            testunitParamVO.setParamName(paramDO.getParamName());
            testunitParamVO.setRank(paramDO.getRank());
            testunitParamVO.setTestunitId(testunitId);
            if (FormCtrlTypeEnum.SELECT_TYPE.getKey().equalsIgnoreCase(paramDO.getFormCtrlType())) {
                TestunitParamExtDO extDO = new TestunitParamExtDO();
                extDO.setTestunitParamId(paramDO.getId());
                List<TestunitParamExtDO> paramExtList = testunitParamExtService.findList(extDO);
                if (paramExtList != null && paramExtList.size() > 0) {
                    StringBuffer testunitParamExt = new StringBuffer();
                    for (int i = 0; i < paramExtList.size(); i++) {
                        TestunitParamExtDO testunitParamExtDO = paramExtList.get(i);
                        testunitParamExt.append(testunitParamExtDO.getValueName()).append("=")
                                .append(testunitParamExtDO.getValue());
                        if (i != paramExtList.size() - 1) {
                            testunitParamExt.append(";");
                        }
                    }
                    testunitParamVO.setTestunitParamExt(testunitParamExt.toString());
                }
            }
            testunitParamVOList.add(testunitParamVO);
        }

        testunitVO.setTestunitParamVOList(testunitParamVOList);
        model.addAttribute("testunitVO", testunitVO);
        return "testunitmanage/testunitDetail";
    }

    /**
     * 进入新增测试单元页面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String addTestunit(Model model, HttpServletRequest request) {
        //页面初始化
        init(model, null);
        return "testunitmanage/addTestunit";
    }

    /**
     * 根据系统ID获取工作空间
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String getWorkSpaceListBySystemId(Model model, HttpServletRequest request,
                                             @RequestParam("systemId") Long systemId) {
        if (systemId != null && systemId > 0L) {
            //页面初始化
            init(model, systemId);
        }
        model.addAttribute("systemId", systemId);
        return "testunitmanage/addTestunit";
    }

    /**
     * 保存测试单元信息
     * 
     * @param model
     * @param request
     * @param systemId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public String saveTestunit(Model model,
                               HttpServletRequest request,
                               @ModelAttribute("testunitVO") TestunitVO testunitVO,
                               @RequestParam("configTableRownumberList") String configTableRownumberList) {
        //前置校验
        model.addAttribute("testunitVO", testunitVO);
        model.addAttribute("configTableRownumberList", configTableRownumberList);
        if (testunitVO == null) {
            model.addAttribute("resultMsg", "温馨提醒:参数校验失败！请联系技术解决！");
            return addTestunit(model, request);
        }
        List<TestunitParamVO> testunitParamVOList = null;
        try {
            testunitParamVOList = JSON.parseArray(testunitVO.getTestunitParamVOStr(),
                    TestunitParamVO.class);
        } catch (Exception e) {
            model.addAttribute("resultMsg", "温馨提醒：解析测试单元参数失败！" + e.getMessage());
            return addTestunit(model, request);
        }
        if (testunitParamVOList == null || testunitParamVOList.size() == 0) {
            model.addAttribute("resultMsg", "温馨提醒：测试单元参数为空！");
            return addTestunit(model, request);
        }
        model.addAttribute("testunitParamVOList", testunitParamVOList);
        String resultMsg = valideParams(testunitVO);
        if (resultMsg != null) {
            model.addAttribute("resultMsg", resultMsg);
            return addTestunit(model, request);
        }
        //唯一性校验，如果数据库中已经存在该测试单元，则忽略
        TestunitDO query = new TestunitDO();
        query.setName(testunitVO.getTestunitName());
        List<TestunitDO> testunitDOList = testunitService.findList(query);
        if (testunitDOList != null && testunitDOList.size() > 0) {
            model.addAttribute("resultMsg",
                    "温馨提醒:该测试单元名称已经存在！测试单元名称：" + testunitVO.getTestunitName());
            return addTestunit(model, request);
        }
        //保存测试单元信息
        resultMsg = buildAndSaveTestunit(testunitParamVOList, testunitVO, request);
        if (resultMsg != null) {
            model.addAttribute("resultMsg", resultMsg);
            return addTestunit(model, request);
        }
        //保存bundle文件
        resultMsg = saveBundleFile(request);
        if (resultMsg != null) {
            model.addAttribute("resultMsg", resultMsg);
            return addTestunit(model, request);
        }
        model.addAttribute("resultMsg", "温馨提醒：恭喜你！测试单元信息保存成功！");
        return addTestunit(model, request);
    }

    //保存bundle file文件
    private String saveBundleFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("bundleFile");
        if (multipartFile == null) {
            return "温馨提醒:bundle文件未上传！";
        }
        try {
            InputStream ins = multipartFile.getInputStream();
            FileOutputStream out = new FileOutputStream(CommonConstants.BUNDLE_FILE_PATH
                    + multipartFile.getOriginalFilename());
            FileCopyUtils.copy(ins, out);
        } catch (IOException e) {
            logger.error("save bundle file error", e);
            return e.getMessage();
        }

        return null;
    }

    //前置校验
    private String valideParams(TestunitVO testunitVO) {
        if (StringUtils.isBlank(testunitVO.getTestunitName())) {
            return "温馨提醒:测试单元名称为空！";
        }
        if (StringUtils.isBlank(testunitVO.getTestunitCode())) {
            return "温馨提醒:测试单元编码为空！";
        }
        if (testunitVO.getSystemId() == null || testunitVO.getSystemId() <= 0L) {
            return "温馨提醒:测试单元所属系统为空！";
        }
        if (StringUtils.isBlank(testunitVO.getTestunitTag())) {
            return "温馨提醒:测试单元标签为空！";
        }
        if (StringUtils.isBlank(testunitVO.getCustomWorSpaceName())
                && testunitVO.getWorkSpaceId() == null || testunitVO.getWorkSpaceId() <= 0L) {
            return "温馨提醒:测试单元所属工作空间为空！";
        }
        if (StringUtils.isBlank(testunitVO.getClassQualifiedName())) {
            return "温馨提醒:测试单元包名及类名为空！";
        }
        if (StringUtils.isBlank(testunitVO.getTestunitParamVOStr())) {
            return "温馨提醒:测试单元包含的参数为空！";
        }

        return null;
    }

    //组装并保存测试单元信息
    private String buildAndSaveTestunit(List<TestunitParamVO> testunitParamVOList,
                                        TestunitVO testunitVO, HttpServletRequest request) {
        //组装并保存工作空间
        Long workSpaceId = testunitVO.getWorkSpaceId();
        if (StringUtils.isNotBlank(testunitVO.getCustomWorSpaceName())
                && (testunitVO.getWorkSpaceId() == null || testunitVO.getWorkSpaceId() <= 0L)) {
            workSpaceId = assembleWorkSpaceDO(testunitVO, request);
            if (workSpaceId == null || workSpaceId <= 0L) {
                return "温馨提醒：工作空间保存失败！";
            }
        }
        //组装并保存测试单元基本信息
        Long testunitId = assembleTestunitDO(testunitVO, request, workSpaceId);
        if (testunitId == null || testunitId <= 0L) {
            return "温馨提醒：测试单元信息保存失败！";
        }
        //组装并保存测试单元参数信息
        String resultMsg = assembleTestunitParamDO(testunitParamVOList, request, testunitId);
        if (resultMsg != null) {
            return resultMsg;
        }

        return null;
    }

    //组装并保存工作空间
    private Long assembleWorkSpaceDO(TestunitVO testunitVO, HttpServletRequest request) {
        WorkSpaceDO workSpaceDO = new WorkSpaceDO();
        workSpaceDO.setCreator(SessionUtils.getLoginUser(request).getUserName());
        workSpaceDO.setModifier(SessionUtils.getLoginUser(request).getUserName());
        workSpaceDO.setName(testunitVO.getCustomWorSpaceName());
        workSpaceDO.setSystemId(testunitVO.getSystemId());
        workSpaceDO.setUserId(SessionUtils.getLoginUser(request).getId());

        return workSpaceService.addWorkSpaceDO(workSpaceDO);
    }

    //组装并保存测试单元基本信息
    private Long assembleTestunitDO(TestunitVO testunitVO, HttpServletRequest request,
                                    Long workSpaceId) {
        TestunitDO testunitDO = new TestunitDO();
        testunitDO.setClassQualifiedName(testunitVO.getClassQualifiedName());
        testunitDO.setCode(testunitVO.getTestunitCode());
        testunitDO.setCreator(SessionUtils.getLoginUser(request).getUserName());
        testunitDO.setModifier(SessionUtils.getLoginUser(request).getUserName());
        testunitDO.setName(testunitVO.getTestunitName());
        testunitDO.setRemark(testunitVO.getRemark());
        testunitDO.setTag(testunitVO.getTestunitTag());
        testunitDO.setWorkSpaceId(workSpaceId);
        testunitDO.setUserId(SessionUtils.getLoginUser(request).getId());

        return testunitService.addTestunitDO(testunitDO);
    }

    //组装并保存测试单元参数信息
    private String assembleTestunitParamDO(List<TestunitParamVO> testunitParamVOList,
                                           HttpServletRequest request, Long testunitId) {
        for (TestunitParamVO testunitParamVO : testunitParamVOList) {
            //组装测试单元参数
            TestunitParamDO testunitParamDO = assembleTestunitParamDO(testunitParamVO, request,
                    testunitId);
            Long testunitParamId = testunitParamService.addTestunitParamDO(testunitParamDO);
            if (testunitParamId == null || testunitParamId <= 0L) {
                return "温馨提醒：测试单元参数信息保存失败！";
            }
            if (FormCtrlTypeEnum.SELECT_TYPE.getKey().equalsIgnoreCase(
                    testunitParamVO.getFormCtrlType())) {
                //组装并保存测试单元包含的参数配置项信息
                String resultMsg = assembleTestunitParamExtDO(request,
                        testunitParamVO.getTestunitParamExt(), testunitParamId);
                if (resultMsg != null) {
                    return resultMsg;
                }
            }
        }
        return null;
    }

    //组装测试单元参数DO
    private TestunitParamDO assembleTestunitParamDO(TestunitParamVO testunitParamVO,
                                                    HttpServletRequest request, Long testunitId) {
        TestunitParamDO testunitParamDO = new TestunitParamDO();
        testunitParamDO.setCreator(SessionUtils.getLoginUser(request).getUserName());
        testunitParamDO.setModifier(SessionUtils.getLoginUser(request).getUserName());
        testunitParamDO.setDefaultValue(testunitParamVO.getDefaultValue());
        testunitParamDO.setFormCtrlType(testunitParamVO.getFormCtrlType());
        testunitParamDO.setHelp(testunitParamVO.getHelp());
        testunitParamDO.setIsRequired(testunitParamVO.getIsRequired());
        testunitParamDO.setLabelName(testunitParamVO.getLabelName());
        testunitParamDO.setParamName(testunitParamVO.getParamName());
        testunitParamDO.setRank(testunitParamVO.getRank());
        testunitParamDO.setTestunitId(testunitId);

        return testunitParamDO;
    }

    //组装并保存测试单元包含的参数配置项信息
    private String assembleTestunitParamExtDO(HttpServletRequest request, String testunitParamExt,
                                              Long testunitParamId) {
        List<TestunitParamExtDO> testunitParamExtDOList = new ArrayList<TestunitParamExtDO>();
        String[] keyvalues = testunitParamExt.split(";");
        if (keyvalues == null || keyvalues.length == 0) {
            return "温馨提醒：测试单元参数配置项保存失败！";
        }
        for (int i = 0; i < keyvalues.length; i++) {
            String keyAndValue[] = keyvalues[i].split("=");
            if (keyAndValue == null || keyAndValue.length != 2) {
                continue;
            }
            TestunitParamExtDO testunitParamExtDO = new TestunitParamExtDO();
            testunitParamExtDO.setCreator(SessionUtils.getLoginUser(request).getUserName());
            testunitParamExtDO.setModifier(SessionUtils.getLoginUser(request).getUserName());
            testunitParamExtDO.setTestunitParamId(testunitParamId);
            testunitParamExtDO.setValueName(keyAndValue[0]);
            testunitParamExtDO.setValue(keyAndValue[1]);
            testunitParamExtDOList.add(testunitParamExtDO);
        }

        testunitParamExtService.batchSaveTestunitParamExtDO(testunitParamExtDOList);
        return null;
    }

}
