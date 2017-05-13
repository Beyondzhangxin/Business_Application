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
                String initializer = "myself";
                if ((ActIdUser) task.getProcessVariables().get("loginuser") != null)
                {
                    initializer = ((ActIdUser) task.getProcessVariables().get("loginUser")).getFirst();
                }
                String processName = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult().getName();
                jsonArray.put(new JSONObject().put("processId", task.getProcessInstanceId()).put("initializer", initializer)
                        .put("creatTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime())).put("processName", processName));
            }
        }

        return jsonArray.toString();

    }


    @RequestMapping("test")
    @ResponseBody
    public void test(HttpServletRequest servletRequest)
    {
       /* List<Task> taskList = taskService.createTaskQuery().executionId("75005").list();
        for (Task task : taskList)
        {
            System.out.println(task.getName());
        }

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition("onBusiness:5:75004");
//        System.out.println(processDefinitionEntity.getId());
//        System.out.println(processDefinitionEntity.getName());
//        System.out.println(processDefinitionEntity.getKey());
//        System.out.println(processDefinitionEntity.getProperties().toString());
//        System.out.println(processDefinitionEntity.getVariables().isEmpty());
//        System.out.println(processDefinitionEntity.getTaskDefinitions().get("userTask1").getKey());
//        System.out.println(processDefinitionEntity.getTaskDefinitions().get("userTask1").getAssigneeExpression());
//        System.out.println(processDefinitionEntity.getTaskDefinitions().get("userTask1").getNameExpression());
        Map<String, TaskDefinition> taskDefinitions = processDefinitionEntity.getTaskDefinitions();
        for (Map.Entry<String, TaskDefinition> entry : taskDefinitions.entrySet())
        {
            entry.getValue().setAssigneeExpression(new ExpressionManager().createExpression("Jason"));
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "1");
        map.put("key", "2");
        for (TaskDefinition taskDefinition : processDefinitionEntity.getTaskDefinitions().values())
        {
            System.out.println(taskDefinition.getAssigneeExpression().getExpressionText());
        }
*/
//        System.out.println(processDefinitionEntity.getTaskDefinitions().get("userTask1").getNameExpression().getExpressionText());
//        Expression xjy = new ExpressionManager().createExpression("xjy");
//        System.out.println(xjy.getExpressionText());


//        return processDefinitionEntity.getTaskDefinitions().get("userTask1").getNameExpression().getExpressionText();
//        System.out.println(map.toString());


       /* for (Task task : taskList)
        {

            System.out.println(task.getId());
            System.out.println(task.getExecutionId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getName());
            System.out.println(task.getTaskDefinitionKey());
        }*/

        String assignee = ((ActIdUser) servletRequest.getSession().getAttribute("loginUser")).getFirst();


        List<Task> list = taskService//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
//查询条件（where部分）

                .taskAssignee(assignee)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
//排序

                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
//返回结果集

//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
        if (list != null && list.size() > 0)
        {
            for (Task task : list)
            {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
//                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("########################################################");
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()).getClass());
                System.out.println(repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult().getName());
            }
        }





    }


}
