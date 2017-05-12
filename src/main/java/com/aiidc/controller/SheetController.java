package com.aiidc.controller;

import com.aiidc.entity.ActIdUser;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        variables.put("manager", "xjy");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefKey, variables);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().list();
        taskService.complete(taskList.get(0).getId());
    }

    //query the current user's personal task
    @RequestMapping("personalTasklist")
    @ResponseBody
    public List<Task> findPersonalTask(HttpServletRequest servletRequest)
    {
        ActIdUser currentUser = (ActIdUser) servletRequest.getSession().getAttribute("currentuser");
        String assignee = currentUser.getFirst();
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        return taskList;

    }

    public void setAssignees(HttpServletRequest servletRequest)
    {

    }

    public String processdefAssignessQuery()
    {

        return null;
    }


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
            jsonArray.put(new JSONObject().put("processDefName", entry.getKey()).put("processDefId", entry.getValue()));
        }
        return jsonArray.toString();
    }





    @RequestMapping("test")
    public void test(HttpServletRequest servletRequest)
    {
        /*List<Task> taskList = taskService.createTaskQuery().active().list();

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition("onBusiness:4:57504");
        System.out.println(processDefinitionEntity.getId());
        System.out.println(processDefinitionEntity.getName());
        System.out.println(processDefinitionEntity.getKey());
        System.out.println(processDefinitionEntity.getProperties().toString());
        System.out.println(processDefinitionEntity.getVariables().isEmpty());
        System.out.println(processDefinitionEntity.getTaskDefinitions().toString());*/
        /*Map<String,Object> map = new HashMap<String ,Object>();
        map.put("key","1");
        map.put("key","2");
        System.out.println(map.toString());
*/


       /* for (Task task : taskList)
        {

            System.out.println(task.getId());
            System.out.println(task.getExecutionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getName());
            System.out.println(task.getTaskDefinitionKey());
        }*/
    }


}
