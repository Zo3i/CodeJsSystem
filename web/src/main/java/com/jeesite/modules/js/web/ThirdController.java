package com.jeesite.modules.js.web;

import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.common.utils.BeanUtils;
import com.jeesite.modules.common.utils.PasswordUtil;
import com.jeesite.modules.common.utils.RedisUtils;
import com.jeesite.modules.js.entity.*;
import com.jeesite.modules.js.entity.other.AnswerRes;
import com.jeesite.modules.js.entity.other.LoginRsp;
import com.jeesite.modules.js.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CompletedService completedService;
    @Autowired
    private TeamInfoService teamInfoService;
    @Autowired
    private TeamMemberService teamMemberService;

    public static final String AUTHORIZATION = "Authorization";

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /***
     * 获取当前用户
     */
    private LoginRsp getUser () {
        LoginRsp loginRsp = null;
        HttpServletRequest request = getRequest();
        String sessionId = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(sessionId)) {
            throw new ServiceException("Header参数有误");
        }
        loginRsp = redisUtils.getSession(sessionId);
        if (loginRsp == null) {
            throw new ServiceException("请重新登录!");
        } else {
            JsUser temp = getUserByMobile(loginRsp.getMobile());
            loginRsp = new LoginRsp(sessionId, temp);
        }

        return loginRsp;
    }

    /***
     * 通过电话找用户
     */
    public JsUser getUserByMobile(String mobile) {
        JsUser jsUser = new JsUser();
        jsUser.setMobile(mobile);
        List<JsUser> list = jsUserService.findList(jsUser);
        return list.get(0);
    }

    /***
     * 获取随机一个题目
     */
    @RequestMapping("/getRandomQuestion")
    public Question getRandomQues(String mobile) {
        Question question = new Question();
        QuestionTasks questionTasks = new QuestionTasks();

        //获取随机题目
        String userId = getUserByMobile(mobile).getId();
        question = questionService.getRandomQuestion(userId);
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
     * 保存前端传来的答案 18.12.4
     */
    @RequestMapping("/saveAnswer")
    public String saveAnswer(@RequestBody Answer answer) {
        JsUser user = new JsUser();
        user.setMobile(answer.getUserMobile());
        JsUser temp = jsUserService.findList(user).get(0);
        //保存答题记录
        Completed completed = new Completed();
        completed.setQuestionId(answer.getQuestionId());

        //获取题目积分

        Question question = questionService.get(answer.getQuestionId());
        Integer rank = question.getScore();

        if (StringUtils.isNotBlank(temp.getId())) {
            answer.setUserId(temp.getId());
            completed.setUserId(temp.getId());
            //查询是否回答过题目
            if (completedService.get(completed) == null) {
                completedService.save(completed);
                temp.setRank(temp.getRank() + rank);
                jsUserService.save(temp);
            }
        }
        answerService.save(answer);
        return "保存成功!";
    }

    /***
     * 登录
     */
    @RequestMapping("/login")
    public LoginRsp login(@RequestBody JsUser user) {

        JsUser temp = new JsUser();
        temp.setMobile(user.getMobile());
        List<JsUser> dbUser = jsUserService.findList(temp);
        if (dbUser.size() != 0) {
            String DbPassWord = dbUser.get(0).getPassword();
            Boolean isPass = PasswordUtil.valid(user.getPassword(), Long.parseLong(user.getMobile()), DbPassWord);
            if (isPass) {
                String token = UUID.randomUUID().toString();
                LoginRsp loginRsp = new LoginRsp(token, dbUser.get(0));
                redisUtils.setSession(token, loginRsp);
                return loginRsp;
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
        JsUser temp = new JsUser();
        temp.setMobile(user.getMobile());
        if (jsUserService.findList(temp).size() > 0) {
            return "该手机号码已被使用!";
        }
        user.setRank(0);
        user.setCreateDate(new Date());
        String md5Password =  PasswordUtil.getMd5PasswordOnce(user.getPassword(), Long.parseLong(user.getMobile()));
        user.setPassword(md5Password);
        jsUserService.save(user);
        return "注册成功咯,请登录!";
    }

    @RequestMapping("/getUserInfo")
    public LoginRsp getUserInfo() {
        return getUser();
    }


    /***
     * 当前问题所有答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllAnwser")
    public List<AnswerRes> getAllAnwser (@RequestBody Answer answer) {

         JsUser currentUser = getUserByMobile(answer.getUserMobile());
        //校验是否有查看权限
        if (!answerService.havaPass(currentUser.getId(), answer.getQuestionId())) {
            return null;
        }

        List<Answer> list = answerService.findList(answer);
        List<AnswerRes> answerResList = BeanUtils.tran(list, AnswerRes.class);

        for (AnswerRes s : answerResList) {
            Question question = questionService.get(s.getQuestionId());
            JsUser user = jsUserService.get(s.getUserId());
            Integer collectNum = collectService.findList(new Collect(s.getId())).size();
            Integer likeNum = likeService.findList(new Like(s.getId())).size();

            Like like = likeService.isLike(s.getId(), currentUser.getId(), s.getUserId());

            Boolean isLike = like != null ? true : false;
            Boolean isCollect = collectService.findList(new Collect(s.getId(),currentUser.getId(), s.getUserId())).size() > 0 ? true : false;
            s.setQuestion(question);
            s.setUser(user);
            s.setTotalCollect(collectNum);
            s.setTotalLike(likeNum);
            s.setLike(isLike);
            s.setCollect(isCollect);
        }
        return answerResList;
    }

    /***
     * 点赞功能18.12.13
     */
    @ResponseBody
    @RequestMapping("/like")
    public String like(@RequestBody Like like) {
        if (like != null) {
            like.setUserid(getUserByMobile(like.getMobile()).getId());
            if (likeService.findList(like).size() > 0) {
               return "你已经点过赞了!";
            } else {
                likeService.save(like);
            }
        }
        return "success";
    }

    /***
     * 取消赞功能18.12.13
     */
    @ResponseBody
    @RequestMapping("/dislike")
    public void dislike(@RequestBody Like like) {
        if (like != null) {
            like.setUserid(getUserByMobile(like.getMobile()).getId());
            likeService.del(likeService.findList(like).get(0));
        }
    }

    /***
     * 收藏功能18.12.13
     */
    @ResponseBody
    @RequestMapping("/collect")
    public String  collect(@RequestBody Collect collect) {
        if (collect != null) {
            collect.setUserid(getUserByMobile(collect.getMobile()).getId());
            if (collectService.findList(collect).size() > 0) {
               return "你已经收藏过了!";
            } else {
                collectService.save(collect);
            }
        }
        return "success";
    }

    /***
     * 取消收藏功能18.12.13
     */
    @ResponseBody
    @RequestMapping("/discollect")
    public void discollect(@RequestBody Collect collect) {
        if (collect != null) {
            collect.setUserid(getUserByMobile(collect.getMobile()).getId());
            collectService.del(collectService.findList(collect).get(0));
        }
    }


    /***
     * 查询队伍
     */
    @ResponseBody
    @RequestMapping("/teaminfo")
    public TeamInfo teaminfo(String mobile) {
        JsUser user = getUserByMobile(mobile);
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamCreatorId(user.getId());
        if (teamInfoService.findList(teamInfo).size() == 0){
            return null;
        } else {
            return teamInfoService.findList(teamInfo).get(0);
        }
    }

    /***
     * 查询队伍所有人
     */
    @ResponseBody
    @RequestMapping("/teamall")
    public List<TeamMember> teamall(String teamid) {
        TeamMember teamMember = new TeamMember();
        if (StringUtils.isNotBlank(teamid)) {
            teamMember.setTeamId(teamid);
            List<TeamMember> list = teamMemberService.findList(teamMember);
            for (TeamMember s : list) {
                s.setJsUser(jsUserService.get(s.getUserId()));
            }
            Collections.sort(list, new Comparator<TeamMember>() {
                @Override
                public int compare(TeamMember o1, TeamMember o2) {
                    return (o1.getJsUser().getRank() - o2.getJsUser().getRank()) > 0 ? 1 : -1;
                }
            });
            return list;
        }
        return null;
    }

    /***
     * 创建团队
     */
    @ResponseBody
    @RequestMapping("/maketeam")
    public String maketeam(@RequestBody TeamInfo teamInfo) {

        JsUser jsUser = getUserByMobile(teamInfo.getMobile());
        if (StringUtils.isNotBlank(teamInfo.getTeamName())) {
            if (teamInfoService.queryByName(teamInfo.getTeamName()) == null) {
                teamInfo.setRank(Long.valueOf(0));
                teamInfo.setTeamCreatorId(jsUser.getId());
                teamInfoService.insert(teamInfo);

                //自动添加
                TeamMember teamMember = new TeamMember();
                teamMember.setTeamId(teamInfoService.get(teamInfo).getId());
                teamMember.setUserId(jsUser.getId());
                teamMemberService.insert(teamMember);
            } else {
                return "您已创建团队,请勿重复创建!";
            }
        } else {
            return "小队名字不能为空!";
        }
        return "小队创建成功!";
    }

    /***
     * 邀请队友
     */
    @ResponseBody
    @RequestMapping("/addmember")
    public String addmember(@RequestBody TeamMember teamMember) {

        if (StringUtils.isNotBlank(teamMember.getMobile())) {
            JsUser user = getUserByMobile(teamMember.getMobile());

            if (teamMemberService.findList(teamMember).size() == 0) {
                teamMember.setUserId(user.getId());
                teamMemberService.insert(teamMember);
            } else {
                return "你已经在小队了!";
            }
        } else {
            return "请填写你要邀请的用户!";
        }
        return "已加入小队!";
    }

}
