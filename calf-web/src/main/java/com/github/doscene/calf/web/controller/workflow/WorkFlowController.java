package com.github.doscene.calf.web.controller.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.workflow.ModelService;
import com.github.doscene.calf.service.workflow.ProcessDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * <h1>com.github.doscene.calf.web.controller.workflow</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@Slf4j
@RequestMapping("workflow")
public class WorkFlowController extends BaseController {

    private final ModelService modelService;
    private final ProcessDefinitionService processDefinitionService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    public WorkFlowController(ModelService modelService, ProcessDefinitionService processDefinitionService, RuntimeService runtimeService, RepositoryService repositoryService) {
        this.modelService = modelService;
        this.processDefinitionService = processDefinitionService;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
    }

    /**
     * 模型列表
     *
     * @param modelEntity 模型实体
     * @return 视图
     */
    @RequestMapping("/model/toPageList")
    public String toModelList(@ModelAttribute("model") ModelEntity modelEntity) {
        return "workflow/model/model_list";
    }

    /**
     * 模型列表
     *
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping("/model/pageList")
    public RestfulResult modelList(ModelEntity modelEntity, Integer pageNum, Integer pageSize) {
        return execPage(modelService.getModelList(modelEntity, pageNum, pageSize));
    }


    /**
     * 创建模型
     *
     * @return 视图
     */
    @RequestMapping("/model/toSave")
    public String toCreateModel() {
        return "workflow/model/model_save";
    }

    /**
     * 创建模型
     *
     * @param name        名称
     * @param key         KEY
     * @param description 描述
     * @param namespace   命名空间
     * @param request     请求
     * @param response    响应
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping("/model/save")
    public RestfulResult createModel(String name, String key, String description, String namespace, HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", namespace);
            stencilSetNode.put("process_author", UserUtils.getUserName());
            stencilSetNode.put("process_id", key);

            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            editorNode.set("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);

            Model newModel = repositoryService.newModel();
            newModel.setMetaInfo(modelObjectNode.toString());
            newModel.setName(name);
            newModel.setKey(key);

            //保存模型
            repositoryService.saveModel(newModel);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
            return RestfulResult.ok();
        } catch (Exception e) {
            log.error("创建模型失败！！！", e);
            return RestfulResult.error();
        }
    }

    /**
     * 模型图片
     *
     * @param modelId  模型id
     * @param response 响应
     * @param request  请求
     * @throws IOException io
     */
    @RequestMapping("/model/img")
    public void modelImg(String modelId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] diagram = repositoryService.getModelEditorSourceExtra(modelId);
        if (diagram == null) {
            response.sendRedirect(request.getContextPath() + "/static/images/nopic.jpg");
            return;
        }
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        ServletOutputStream out = response.getOutputStream();
        out.write(diagram);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 部署模型
     *
     * @param modelId 模型id
     * @param mv      视图
     * @return 视图
     */
    @RequestMapping("/model/toDeploy")
    public ModelAndView toDeploy(String modelId, ModelAndView mv) {
        mv.addObject("model", modelService.getModelById(modelId));
        mv.setViewName("workflow/model/model_deploy");
        return mv;
    }

    /**
     * 部署模型
     *
     * @param modelId 模型id
     * @return 结果集
     */
    @RequestMapping("/model/deploy")
    @ResponseBody
    public RestfulResult deploy(String modelId) {
        try {
            Model modelData = modelService.getModelById(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                    .deploy();
            return RestfulResult.ok();
        } catch (Exception e) {
            return RestfulResult.error();
        }
    }

    /**
     * 流程定义分页
     *
     * @param processdef 流程定义
     * @param mv         视图
     * @return 视图
     */
    @RequestMapping("/processdef/toPageList")
    public ModelAndView processdefPageList(@ModelAttribute("processdef") ProcessDefinitionEntity processdef, ModelAndView mv) {
        mv.setViewName("workflow/processdef/processdef_list");
        return mv;
    }

    /**
     * 流程定义列表
     *
     * @param processdef 流程定义
     * @param pageNum    页码
     * @param pageSize   页长度
     * @return 结果集
     */
    @ResponseBody
    @RequestMapping("/processdef/pageList")
    public RestfulResult processdefList(ProcessDefinitionEntity processdef, Integer pageNum, Integer pageSize) {
        return execPage(processDefinitionService.getProcessList(processdef, pageNum, pageSize));
    }

    /**
     * 启动流程
     *
     * @param processdefKey 流程定义key
     * @param variables     属性
     * @return
     */
    @ResponseBody
    @RequestMapping("/processdef/start")
    public RestfulResult startProcessdef(String processdefKey, HashMap<String, Object> variables) {
        runtimeService
                .createProcessInstanceBuilder()
                .processDefinitionKey(processdefKey)
                .processInstanceName("请假")
                .addVariable("name", UserUtils.getUserName())
                //.businessKey("")
                .start();
        return RestfulResult.ok();
    }

}
