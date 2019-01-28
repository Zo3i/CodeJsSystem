package com.jeesite.modules.js.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.common.utils.BeanUtils;
import com.jeesite.modules.common.utils.ExcuteScriptUtil;
import com.jeesite.modules.common.utils.PasswordUtil;
import com.jeesite.modules.common.utils.RedisUtils;
import com.jeesite.modules.js.entity.*;
import com.jeesite.modules.js.entity.other.*;
import com.jeesite.modules.js.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
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
    @Autowired
    private CommentService commentService;

    public static final String AUTHORIZATION = "Authorization";

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /***
     * 获取当前用户
     */
    private LoginRsp getUser() {
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
     * 通过token找用户
     */
    public JsUser getUserByToken(String token) {
        JsUser jsUser = new JsUser();
        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {
            jsUser.setMobile(loginRsp.getMobile());
            List<JsUser> list = jsUserService.findList(jsUser);
            return list.get(0);
        }
        return null;
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
        if (question != null) {
            String questionId = question.getId();
            questionTasks.setQuestionId(questionId);
            List<QuestionTasks> tasks = questionTasksService.findList(questionTasks);
            question.setQuestionTasksList(tasks);
        }
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
    }


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
            completed.setQuestionId(question.getId());
            //查询是否回答过题目
            List<Completed> c = completedService.findList(completed);
            if ( c.size() == 0) {
                completedService.save(completed);
                temp.setRank(temp.getRank() + rank);
                jsUserService.save(temp);
                answerService.save(answer);
            } else {
                return "您已经回答过了!";
            }
        }

        return "保存成功!";
    }

    /***
     * 登录
     */
    @RequestMapping("/login")
    public LoginRsp login(String password, String mobile) {

        JsUser temp = new JsUser();
        temp.setMobile(mobile);
        List<JsUser> dbUser = jsUserService.findList(temp);
        if (dbUser.size() != 0) {
            String DbPassWord = dbUser.get(0).getPassword();
            Boolean isPass = PasswordUtil.valid(password, Long.parseLong(mobile), DbPassWord);
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
    public String sign(String password, String mobile, String name) {
        JsUser user = new JsUser();
        user.setMobile(mobile);
        user.setPassword(password);
        user.setName(name);


        JsUser temp = new JsUser();
        temp.setMobile(mobile);
        if (jsUserService.findList(temp).size() > 0) {
            return "该手机号码已被使用!";
        }
        user.setRank(0);
        user.setCreateDate(new Date());
        user.setZoneId(UUID.randomUUID().toString());
        String md5Password = PasswordUtil.getMd5PasswordOnce(user.getPassword(), Long.parseLong(user.getMobile()));
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
    public List<AnswerRes> getAllAnwser(@RequestBody Answer answer) {

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
            Boolean isCollect = collectService.findList(new Collect(s.getId(), currentUser.getId(), s.getUserId())).size() > 0 ? true : false;
            s.setQuestion(question);
            s.setUser(user);
            s.setTotalCollect(collectNum);
            s.setTotalLike(likeNum);
            s.setLike(isLike);
            s.setCollect(isCollect);
        }

        //最佳答案排序
        Collections.sort(answerResList, new Comparator<AnswerRes>() {
            @Override
            public int compare(AnswerRes o1, AnswerRes o2) {
                return ((o1.getTotalCollect() + o1.getTotalLike()) - (o2.getTotalCollect() + o2.getTotalLike())) > 0 ? -1 : 1;
            }
        });
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
    public String collect(@RequestBody Collect collect) {
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
        TeamMember teamMember = new TeamMember();
        TeamInfo teamInfo = new TeamInfo();

        teamMember.setUserId(user.getId());
        List<TeamMember> list = teamMemberService.findList(teamMember);
        if (list.size() == 0) {
            return null;
        } else {
            teamInfo.setId(list.get(0).getTeamId());
            return teamInfoService.findList(teamInfo).get(0);
        }
    }

    /***
     * 查询队伍所有人
     */
    @ResponseBody
    @RequestMapping("/teamall")
    public List<TeamMember> teamall(String teamid) {
//        repass();
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
                    return (o1.getJsUser().getRank() - o2.getJsUser().getRank()) > 0 ? -1 : 1;
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
     * 加入小队
     */
    @ResponseBody
    @RequestMapping("/jointeam")
    public String jointeam(@RequestBody TeamInfo teamInfo) {

        if (StringUtils.isNotBlank(teamInfo.getTeamName())) {
            List<TeamInfo> teamList = teamInfoService.findList(teamInfo);
            TeamMember teamMember = new TeamMember();
            if (teamList.size() != 0) {
               TeamInfo team = teamList.get(0);
               if (StringUtils.isNotBlank(teamInfo.getToken())) {
                    JsUser user = getUserByToken(teamInfo.getToken());
                    teamMember.setUserId(user.getId());
                    List<TeamMember> teamMemberList = teamMemberService.findList(teamMember);
                    if (teamMemberList.size() == 0) {
                        teamMember.setTeamId(team.getId());
                        teamMemberService.insert(teamMember);
                    } else {
                        return "你已经在小队了!";
                    }
                } else {
                    return "token不能为空!";
                }
                }  else {
                return "此队伍不存在!";
            }
            }  else {
                    return "请填写你要加入的队伍!";
                }
        return "已加入小队!";
    }


    /***
     * 所有队伍
     */
    @ResponseBody
    @RequestMapping("/teamlist")
    public List<TeamRes> getTeamList() {
        List<TeamInfo> list = teamInfoService.findList(new TeamInfo());

        List<TeamRes> teamList = null;
        for (TeamInfo s : list) {
            TeamRes teamRes = new TeamRes();
//            teamRes.setTeamCreatorName();
        }
        return null;
    }


    /***
     * 当前用户所有答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMyAnwser")
    public List<AnswerRes> getMyAnwser(String token) {

        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {

            JsUser currentUser = getUserByMobile(loginRsp.getMobile());

            Answer answer = new Answer();
            answer.setUserId(currentUser.getId());

            List<Answer> list = answerService.findList(answer);
            List<AnswerRes> answerResList = BeanUtils.tran(list, AnswerRes.class);

            for (AnswerRes s : answerResList) {
                Question question = questionService.get(s.getQuestionId());
                JsUser user = jsUserService.get(s.getUserId());
                Integer collectNum = collectService.findList(new Collect(s.getId())).size();
                Integer likeNum = likeService.findList(new Like(s.getId())).size();

                Like like = likeService.isLike(s.getId(), currentUser.getId(), s.getUserId());

                Boolean isLike = like != null ? true : false;
                Boolean isCollect = collectService.findList(new Collect(s.getId(), currentUser.getId(), s.getUserId())).size() > 0 ? true : false;
                s.setQuestion(question);
                s.setUser(user);
                s.setTotalCollect(collectNum);
                s.setTotalLike(likeNum);
                s.setLike(isLike);
                s.setCollect(isCollect);
            }
            return answerResList;
        } else {
            return null;
        }

    }

     /***
     * 当前用户喜欢的答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLikeAnwser")
    public List<AnswerRes> getLikeAnwser(String token) {

        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {

            JsUser currentUser = getUserByMobile(loginRsp.getMobile());

            Answer answer = new Answer();
            answer.setUserId(currentUser.getId());
            Like likes = new Like();
            likes.setUserid(currentUser.getId());
            List<String> likeAnswerId = BeanUtils.getField(likeService.findList(likes), "answerid");
            List<Answer> list = answerService.queryLikeAnswer(likeAnswerId);
            List<AnswerRes> answerResList = BeanUtils.tran(list, AnswerRes.class);

            for (AnswerRes s : answerResList) {
                Question question = questionService.get(s.getQuestionId());
                JsUser user = jsUserService.get(s.getUserId());
                Integer collectNum = collectService.findList(new Collect(s.getId())).size();
                Integer likeNum = likeService.findList(new Like(s.getId())).size();

                Like like = likeService.isLike(s.getId(), currentUser.getId(), s.getUserId());

                Boolean isLike = like != null ? true : false;
                Boolean isCollect = collectService.findList(new Collect(s.getId(), currentUser.getId(), s.getUserId())).size() > 0 ? true : false;
                s.setQuestion(question);
                s.setUser(user);
                s.setTotalCollect(collectNum);
                s.setTotalLike(likeNum);
                s.setLike(isLike);
                s.setCollect(isCollect);
            }
            return answerResList;
        } else {
            return null;
        }
    }

     /***
     * 当前用户收藏答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCollctAnwser")
    public List<AnswerRes> getCollctAnwser(String token) {

        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {

            JsUser currentUser = getUserByMobile(loginRsp.getMobile());

            Answer answer = new Answer();
            answer.setUserId(currentUser.getId());

            Collect collect = new Collect();
            collect.setUserid(currentUser.getId());
            List<String> collectAnswer = BeanUtils.getField(collectService.findList(collect), "answerid");
            List<Answer> list = answerService.queryCollectAnswer(collectAnswer);

            List<AnswerRes> answerResList = BeanUtils.tran(list, AnswerRes.class);

            for (AnswerRes s : answerResList) {
                Question question = questionService.get(s.getQuestionId());
                JsUser user = jsUserService.get(s.getUserId());
                Integer collectNum = collectService.findList(new Collect(s.getId())).size();
                Integer likeNum = likeService.findList(new Like(s.getId())).size();

                Like like = likeService.isLike(s.getId(), currentUser.getId(), s.getUserId());

                Boolean isLike = like != null ? true : false;
                Boolean isCollect = collectService.findList(new Collect(s.getId(), currentUser.getId(), s.getUserId())).size() > 0 ? true : false;
                s.setQuestion(question);
                s.setUser(user);
                s.setTotalCollect(collectNum);
                s.setTotalLike(likeNum);
                s.setLike(isLike);
                s.setCollect(isCollect);
            }
            return answerResList;
        } else {
            return null;
        }
    }

     /***
     * 当前用户所有做过的答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllQuestion")
    public List<QuestionRes> getAllQuestion(String token) {

        List<QuestionRes> list = null;
        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {

            JsUser currentUser = getUserByMobile(loginRsp.getMobile());
            if (currentUser != null) {
                List<Question> questionlist = questionService.getAllQuestion(currentUser.getId());
                list = BeanUtils.tran(questionlist, QuestionRes.class);
            }
        }
        return  list;
    }

    /***
     * 信息中心数据
     */
    @ResponseBody
    @RequestMapping("/myInfo")
    public MyInfo myInfo(String token) {

        LoginRsp loginRsp = redisUtils.getSession(token);
        MyInfo myInfo = new MyInfo();
        if (loginRsp != null) {

            JsUser currentUser = getUserByMobile(loginRsp.getMobile());
            if (currentUser != null) {
                Like like = new Like();
                like.setAuthorid(currentUser.getId());

                Collect collect = new Collect();
                collect.setAuthorid(currentUser.getId());

                TeamInfo teamInfo = teamInfoService.queryByUserId(currentUser.getId());


                Integer totleLike = likeService.findList(like).size();
                Integer totleCollect = collectService.findList(collect).size();

                if (teamInfo != null) {
                    myInfo.setTeam(teamInfo.getTeamName());
                }  else {
                    myInfo.setTeam("暂无战队!");
                }
                myInfo.setTotleCollect(totleCollect);
                myInfo.setTotleLike(totleLike);
                myInfo.setTotleRank(currentUser.getRank());

            }
        }
       return myInfo;
    }

    /***
     * 查询当前空间的所有留言
     */
    @ResponseBody
    @RequestMapping("/getComment")
    public List<CommentRes> getComment (String zoneId) {
       Comment comment = new Comment();
       comment.setZone(zoneId);
       if (StringUtils.isBlank(zoneId)) {
           return null;
       }

       List<Comment> list = commentService.findList(comment);
       List<CommentRes> commentResList = BeanUtils.tran(list, CommentRes.class);

       for (CommentRes c : commentResList) {
           c.setFromName(jsUserService.get(c.getFromUserId()).getName());
           if (StringUtils.isNotBlank(c.getToUserId())) {
               c.setToName(jsUserService.get(c.getToUserId()).getName());
           }
       }
       return commentResList;
    }

    /***
     * 留言
     */
    @ResponseBody
    @RequestMapping("/leaveComment")
    public String leaveComment (@RequestBody Comment comment) {

        String token = comment.getToken();
        JsUser fromUser = getUserByToken(token);
        comment.setFromUserId(fromUser.getId());

        if (StringUtils.isBlank(comment.getComment())) {
            return "空的留言,不如不留";
        }
        commentService.save(comment);
        return "收到你的留言咯!";
    }

    /***
     * 队伍排行榜
     */
    @ResponseBody
    @RequestMapping("/rank")
    public List<RankRes> rank () {

       List<TeamInfo> teamInfos = teamInfoService.findList(new TeamInfo());
       List<RankRes> list = new ArrayList<>();

       for (TeamInfo t : teamInfos) {

        TeamMember s = new TeamMember();
        s.setTeamId(t.getId());
        List<TeamMember> teamMembers = teamMemberService.findList(s);

        //最佳队员
        Collections.sort(teamMembers, new Comparator<TeamMember>() {
            @Override
            public int compare(TeamMember o1, TeamMember o2) {
                return (jsUserService.get(o1.getUserId()).getRank() - jsUserService.get(o2.getUserId()).getRank()) > 0 ? -1 : 1;
            }
        });

        Integer sum = 0;
        for (TeamMember e : teamMembers) {
            JsUser jsUser = jsUserService.get(e.getUserId());
            jsUser.getName();
            Integer score = jsUser.getRank() == null ? 0 : jsUser.getRank();
            sum += score;
        }
        JsUser best = jsUserService.get(teamMembers.get(0).getUserId());

        RankRes r = new RankRes();
        r.setBest(best.getName());
        r.setName(t.getTeamName());
        r.setTotalRank(sum);
        r.setCount(teamMembers.size());
        list.add(r);
       }

        //总分排序
        Collections.sort(list, new Comparator<RankRes>() {
            @Override
            public int compare(RankRes o1, RankRes o2) {
                return compareRank(o1.getTotalRank(), o2.getTotalRank());
            }
        });
       return list;
    }

    /***
     * 按难度查询问题
     */
//    @ResponseBody
//    @RequestMapping("getQuestions")
//    public List<QuestionSearchRes> getQuestions(@RequestBody QuestionSearchRes res) {
//        JsUser user = getUserByMobile(res.getMobile());
//        List<QuestionSearchRes> list = questionService.queryByScore(user.getId(), res.getLowRank(), res.getHighRank());
//
//    }

    public int compareRank(Integer o1, Integer o2) {
        if(o1 > o2) {
            return -1;
        }
        if(o2 > o1) {
            return 1;
        }
        return 0;
    }


    //重置所有用户密码为123456
    public void repass() {
        String pass = "e10adc3949ba59abbe56e057f20f883e";
        List<JsUser> users = jsUserService.findList(new JsUser());
        for (JsUser s : users) {
            String md5Password = PasswordUtil.getMd5PasswordOnce(pass, Long.parseLong(s.getMobile()));
            s.setPassword(md5Password);
            jsUserService.update(s);
        }
    }


    /***
     * 判断用户回答是否正确;
     * @param userAnswerRes
     * @return
     */
    @ResponseBody
    @RequestMapping("/isRight")
    public ReturnRes isRight(@RequestBody UserAnswerRes userAnswerRes) {

        ReturnRes res = new ReturnRes();
        Boolean isWrong = true;
        Boolean isRight = null;
        String userAnswer = "";
        String rightAnswer = "";

        //获取问题信息
        Question question = questionService.get(userAnswerRes.getQuestionId());

        V8 runtime = V8.createV8Runtime();
        String task = "JSON.stringify(" + userAnswerRes.getTask() + ")";

        String userScript = userAnswerRes.getUseranswer() + task;
        String rightScript = question.getRightAnswer() +task;

        try {
            userAnswer = runtime.executeStringScript(userScript);
            rightAnswer = runtime.executeStringScript(rightScript);
            if (userAnswer.equals(rightAnswer)) {
                isRight = true;
            } else {
                isRight = false;
            }
        } catch (Exception e) {
            res.setWrong(isWrong);
            if (StringUtils.isBlank(e.getMessage())) {
               res.setAnswer("无返回值!");
            } else {
                res.setAnswer(e.getMessage());
            }
		    return res;
        }
        runtime.release();

        isWrong = false;
        res.setAnswer(userAnswer);
        res.setWrong(isWrong);
        res.setRight(isRight);
        return res;
    }

}
