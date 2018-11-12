package com.jeesite.modules.js.web;

import com.jeesite.modules.js.entity.Question;
import com.jeesite.modules.js.entity.QuestionTasks;
import com.jeesite.modules.js.service.QuestionService;
import com.jeesite.modules.js.service.QuestionTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/***
 * 前端专用接口
 * 2018.11.5
 * author: jo
 */
@RestController
@RequestMapping(value = "${adminPath}/third")
public class ThirdController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionTasksService questionTasksService;
    /***
     * 获取随机一个题目
     */
    @RequestMapping("/getRandomQuestion")
    public Question getRandomQues(String userId) {
        Question question = new Question();
        QuestionTasks questionTasks = new QuestionTasks();

        //获取随机题目
        question = questionService.getRandomQuestion("aaa");
        //获取题目的tasks
        String questionId = question.getId();
        questionTasks.setQuestionId(questionId);
        List<QuestionTasks> tasks = questionTasksService.findList(questionTasks);
        question.setQuestionTasksList(tasks);
        return question;
    }
}
