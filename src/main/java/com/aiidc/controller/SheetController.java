package com.aiidc.controller;

import com.aiidc.entity.ActIdUser;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zhangx on 2017/5/5 at 13:45.
 */
@RequestMapping("onBusiness")
@Controller
public class SheetController
{
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    //start the process instance
    @RequestMapping("startProcess")
    public void startProcessInstance(HttpServletRequest servletRequest) throws Exception
    {
        String processDefKey = "onBusiness";
        ActIdUser currentUser = (ActIdUser) servletRequest.getSession().getAttribute("loginUser");
        Map<String, Object> variables = new HashMap<String, Object>();
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(servletRequest.getParameter("startDate"));
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(servletRequest.getParameter("endDate"));
        String message = servletRequest.getParameter("message");
        String city = servletRequest.getParameter("city");
        variables.put("startDate", startDate);
        variables.put("endDate", endDate);
        variables.put("city", city);
        variables.put("message", message);
        variables.put("loginUser", currentUser);
//        variables.put("manager", "xjy");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefKey, variables);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().list();
        taskService.complete(taskList.get(0).getId());
    }


    //query the latest process definition list
    @RequestMapping("processDefList")
    @ResponseBody
    public String processQuery()
    {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        Map<String, String> map = new HashMap<String, String>();
        JSONArray jsonArray = new JSONArray();
        for (ProcessDefinition processDefinition : processDefinitionList)
        {
            map.put(processDefinition.getKey(), processDefinition.getId());

        }
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            String name = repositoryService.createProcessDefinitionQuery().processDefinitionId(entry.getValue()).singleResult().getName();
            jsonArray.put(new JSONObject().put("processDefName", name).put("processDefId", entry.getValue()));
        }
        return jsonArray.toString();
    }

    @RequestMapping("taskDefList")
    @ResponseBody
    public String taskDefListByProcessDefId(HttpServletRequest servletRequest)
    {
        String processDefId = servletRequest.getParameter("processDefId");
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefId);
        Collection<TaskDefinition> taskDefinitions = processDefinitionEntity.getTaskDefinitions().values();
        JSONArray jsonArray = new JSONArray();
        for (TaskDefinition taskDefinition : taskDefinitions)
        {
            jsonArray.put(new JSONObject().put("taskId", taskDefinition.getKey()).put("taskName", taskDefinition.getNameExpression().getExpressionText()).put("assignee", taskDefinition.getAssigneeExpression().getExpressionText()));
        }
        return jsonArray.toString();
    }

    @RequestMapping("ProcdefAssignee")
    @ResponseBody
    public void ProcessDefAssigneeSet(HttpServletRequest servletRequest)
    {
        JSONObject jsonObject = new JSONObject(servletRequest.getParameter("task"));
        String processDefId = servletRequest.getParameter("processDefId");
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefId);
        Map<String, TaskDefinition> taskDefinitions = processDefinitionEntity.getTaskDefinitions();

        String taskKey = jsonObject.getString("taskId");
        String assignee = jsonObject.getString("assignee");
        taskDefinitions.get(taskKey).setAssigneeExpression(new ExpressionManager().createExpression(assignee));


    }

    @RequestMapping("waitingProcess")
    @ResponseBody
    public String waitingProcess(HttpServletRequest servletRequest)
    {
        String assignee = ((ActIdUser) servletRequest.getSession().getAttribute("loginUser")).getFirst();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().asc().list();
        JSONArray jsonArray = new JSONArray();

        if (taskList.size() > 0)
        {
            for (Task task : taskList)
            {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime());

                ActIdUser actIdUser = (ActIdUser) (runtimeService.getVariable(task.getExecutionId(), "loginUser"));             String initializer;
                if (actIdUser!=null){
                         initializer=actIdUser.getFirst();
                } else {
                      initializer="anonymous";
                }

                String processName = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult().getName();
                jsonArray.put(new JSONObject().put("processId", task.getProcessInstanceId()).put("initializer", initializer)
                        .put("creatTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime())).put("processName", processName).put("taskId", task.getId()));
            }
        }

        return jsonArray.toString();

    }

    @RequestMapping("getProcVars")
    @ResponseBody
    public String getProcessInstanceVars(HttpServletRequest servletRequest)
    {
        String procId = servletRequest.getParameter("procId");
        Map<String, Object> variables = runtimeService.getVariables(procId);
        Object loginUser = variables.get("loginUser");
        ActIdUser user = (ActIdUser)loginUser;
        String name=((ActIdUser) loginUser).getFirst();
        variables.put("loginUser",name);
        JSONObject jsonObject = new JSONObject(variables);
        return jsonObject.toString();

    }

    @RequestMapping("completeTask")
    public void completeTask(HttpServletRequest servletRequest)
    {

        String taskId = servletRequest.getParameter("taskId");
        String outcome = servletRequest.getParameter("outcome");
        String result = "批准";
        if (outcome == null)
        {
            result = "驳回";
        }
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("outcome", result);
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId, variables);
    }


    @RequestMapping("test")
    @ResponseBody
    public Object test(HttpServletRequest servletRequest)
    {
        Map<String, Object> variables = runtimeService.getVariables("80004");
        Object loginUser = variables.get("loginUser");
        ActIdUser user = (ActIdUser) loginUser;
        String name = ((ActIdUser) loginUser).getFirst();
        variables.put("loginUser", name);
        System.out.println(variables.toString());

        return "测试中";


    }


}
