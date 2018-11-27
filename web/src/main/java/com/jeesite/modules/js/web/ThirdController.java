package com.jeesite.modules.js.web;

import com.jeesite.modules.common.utils.PasswordUtil;
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.entity.Question;
import com.jeesite.modules.js.entity.QuestionTasks;
import com.jeesite.modules.js.service.JsUserService;
import com.jeesite.modules.js.service.QuestionService;
import com.jeesite.modules.js.service.QuestionTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    @Autowired
    private JsUserService jsUserService;
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
    /***
     * 获取指定题目
     */
    @RequestMapping("/getQuestion/{id}")
    public Question getQuestion(@PathVariable String id) {
        QuestionTasks questionTasks = new QuestionTasks();
        Question question = questionService.get(id);
        //获取题目的tasks/.
        questionTasks.setQuestionId(id);
        List<QuestionTasks> tasks = questionTasksService.findList(questionTasks);
        question.setQuestionTasksList(tasks);
        return question;
    };

    /***
     * 保存前端传来的答案 18.11.27
     */
    @RequestMapping("/saveAnswer")
    public void saveAnswer() {

    }

    /***
     * 登录
     */
    @RequestMapping("/login")
    public String login(@RequestBody JsUser user) {

        JsUser temp = new JsUser();
        temp.setMobile(user.getMobile());
        List<JsUser> dbUser = jsUserService.findList(temp);
        if (dbUser.size() != 0) {
            String DbPassWord = dbUser.get(0).getPassword();
            Boolean isPass = PasswordUtil.valid(user.getPassword(), Long.parseLong(user.getMobile()), DbPassWord);
            if (isPass) {
                return "登录成功!";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /***
     * 注册
     */
    @RequestMapping("/sign")
    public String sign(@RequestBody JsUser user) {
        if (jsUserService.findList(user).size() > 0) {
            return "手机号码已存在";
        }
        user.setRank(0);
        user.setCreateDate(new Date());
        String md5Password =  PasswordUtil.getMd5PasswordOnce(user.getPassword(), Long.parseLong(user.getMobile()));
        user.setPassword(md5Password);
        jsUserService.save(user);
        return "注册成功咯,请登录!";
    }
}
