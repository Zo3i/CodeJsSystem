package com.jeesite.modules.js.web;

import com.eclipsesource.v8.V8;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.common.utils.*;
import com.jeesite.modules.common.utils.limit.RequestLimit;
import com.jeesite.modules.common.utils.limit.RequestLimitException;
import com.jeesite.modules.js.entity.*;
import com.jeesite.modules.js.entity.other.*;
import com.jeesite.modules.js.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.regex.Pattern;


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
    @Autowired
    private ResultRecordService resultRecordService;

    public static final String AUTHORIZATION = "Authorization";

    public String getToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AUTHORIZATION);
    }

    /***
     * 获取当前用户
     */
    private LoginRsp getUser() {
        LoginRsp loginRsp = null;
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("Header参数有误");
        }
        loginRsp = redisUtils.getSession(token);
        if (loginRsp == null) {
            throw new ServiceException("请重新登录!");
        } else {
            JsUser temp = getUserByMobile(loginRsp.getMobile());
            loginRsp = new LoginRsp(token, temp);
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
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

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

        if ("undefined".equals(mobile)) {
            return null;
        }
        //获取随机题目
        String userId = getUserByMobile(mobile).getId();
        question = questionService.getRandomQuestion(userId);
        //获取题目的tasks
        if (question != null) {
            String questionId = question.getId();
            questionTasks.setQuestionId(questionId);
            question.setRightAnswer("别想偷看答案了!");
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
        // 获取题目的tasks
        questionTasks.setQuestionId(id);
        List<QuestionTasks> tasks = questionTasksService.findList(questionTasks);
        question.setQuestionTasksList(tasks);
        question.setRightAnswer("别想偷看答案了!");
        return question;
    }


    /***
     * 保存前端传来的答案 18.12.4
     */
    @RequestMapping("/saveAnswer")
    public String saveAnswer(@RequestBody Answer answer) {

        /***
         * 后端验证答案是否正确;19.4.3 by jo
         */
        String mobile = answer.getUserMobile();
        String questionId = answer.getQuestionId();

        ResultRecord record = new ResultRecord();
        Completed completed = new Completed();
        record.setQuestionId(questionId);
        record.setMobile(mobile);

        List<ResultRecord> records = resultRecordService.findList(record);

        if (records.size() == 0) {
            return "别想那么多花里胡哨的东西啦,安心答题!";
        }

        // 用户提交的答案
        ResultRecord real = records.get(0);

        JsUser user = new JsUser();
        user.setMobile(answer.getUserMobile());
        JsUser temp = jsUserService.findList(user).get(0);
        Question question = questionService.get(answer.getQuestionId());

        completed.setUserId(temp.getId());
        completed.setQuestionId(question.getId());
        List<Completed> c = completedService.findList(completed);
        if (c.size() == 0) {
            return "你怎么进来的?";
        }

        if (StringUtils.isNotBlank(temp.getId())) {
            answer.setUserId(temp.getId());
            answer.setAnswer(real.getResult());
            List<Answer> userAnswer =  answerService.findList(new Answer(temp.getId(), questionId));
            if (userAnswer.size() == 0) {
                // 保存
                answer.setAnswer(real.getResult());
                answerService.save(answer);
            } else {
                // 替换前端传来的答案
                Answer tempAnswer = userAnswer.get(0);
                tempAnswer.setAnswer(real.getResult());
                answerService.update(tempAnswer);
            }
        }
        return "保存成功!";
    }

    /***
     * 登录
     */
    @RequestMapping("/login")
    public LoginRsp login(String password, String mobile) {

         // 检测手机格式
        String pattern = "^1[34578]\\d{9}$";
        boolean isMatch = Pattern.matches(pattern, mobile);

        JsUser temp = new JsUser();
        temp.setMobile(mobile);
        List<JsUser> dbUser = jsUserService.findList(temp);
        if (dbUser.size() != 0 && isMatch) {
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
    @RequestLimit(maxCount = 5)
    @RequestMapping("/sign")
    public String sign(String password, String mobile, String name) {
        JsUser user = new JsUser();
        user.setMobile(mobile);
        user.setPassword(password);
        user.setName(name);

        // 检测手机格式
        String pattern = "^1[34578]\\d{9}$";
        boolean isMatch = Pattern.matches(pattern, mobile);

        if (!isMatch) {
            return "你的手机号有点问题呀!";
        }

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

        List<TempAnswerRes> list = answerService.queryAnswerByQuestionId(answer.getQuestionId());
        List<AnswerRes> answerResList = BeanUtils.tran(list, AnswerRes.class);
        List<String> likeAnswers = answerService.likeAnswers(currentUser.getId());
        List<String> collectAnswer = answerService.collectAnswers(currentUser.getId());

         for (AnswerRes s : answerResList) {
                s.setLike(likeAnswers.contains(s.getId()));
                s.setCollect(collectAnswer.contains(s.getId()));
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
        String token = getToken();
        JsUser user = getUserByToken(token);
        if (like != null) {
            like.setUserid(user.getId());
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
        String token = getToken();
        JsUser user = getUserByToken(token);
        if (like != null) {
            like.setUserid(user.getId());
            if (likeService.findList(like).size() > 0) {
                likeService.del(likeService.findList(like).get(0));
            }
        }
    }

    /***
     * 收藏功能18.12.13
     */
    @ResponseBody
    @RequestMapping("/collect")
    public String collect(@RequestBody Collect collect) {
        String token = getToken();
        JsUser user = getUserByToken(token);
        if (collect != null) {
            collect.setUserid(user.getId());
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
        String token = getToken();
        JsUser user = getUserByToken(token);
        if (collect != null) {
            collect.setUserid(user.getId());
            if (collectService.findList(collect).size() > 0) {
                collectService.del(collectService.findList(collect).get(0));
            }
        }
    }


    /***
     * 查询队伍
     */
    @ResponseBody
    @RequestMapping("/teaminfo")
    public TeamInfo teaminfo(String mobile) {
        if ("undefined".equals(mobile)) {
            return null;
        }
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
    public List<JsUser> teamall(String teamid) {
        if (StringUtils.isNotBlank(teamid)) {
            List<JsUser> list = teamMemberService.list(teamid);
            for (JsUser user : list) {
                user.setMobile("想看手机号？");
            }
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
            if (teamInfoService.queryByName(teamInfo.getTeamName()) == null && teamInfoService.queryByUserId(jsUser.getId()) == null) {
                teamInfo.setRank(Long.valueOf(0));
                teamInfo.setTeamCreatorId(jsUser.getId());
                teamInfoService.insert(teamInfo);

                //自动添加
                TeamMember teamMember = new TeamMember();
                teamMember.setTeamId(teamInfoService.get(teamInfo).getId());
                teamMember.setUserId(jsUser.getId());
                teamMemberService.insert(teamMember);
                 //用户设置队伍ID
                jsUser.setTeamId(teamInfo.getId());
                jsUserService.update(jsUser);
            } else {
                return "已创建团队,请勿重复创建!";
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
                        //用户设置队伍ID
                        user.setTeamId(team.getId());
                        jsUserService.update(user);
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
        }
        return null;
    }


    /***
     * 当前用户所有答案
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMyAnwser")
    public List<AnswerRes> getMyAnwser() {
        String token = getToken();
        LoginRsp loginRsp = redisUtils.getSession(token);

        if (loginRsp != null) {
            JsUser currentUser = getUserByMobile(loginRsp.getMobile());
            List<TempAnswerRes> tempAnswerRes = answerService.queryAnswerByUserId(currentUser.getId());
            Answer answer = new Answer();
            answer.setUserId(currentUser.getId());
            List<AnswerRes> answerResList = BeanUtils.tran(tempAnswerRes, AnswerRes.class);
            List<String> likeAnswers = answerService.likeAnswers(currentUser.getId());
            List<String> collectAnswer = answerService.collectAnswers(currentUser.getId());
            for (AnswerRes s : answerResList) {
                s.setLike(likeAnswers.contains(s.getId()));
                s.setCollect(collectAnswer.contains(s.getId()));
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
    public List<AnswerRes> getLikeAnwser() {
        String token = getToken();
        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {
            JsUser currentUser = getUserByMobile(loginRsp.getMobile());
            List<TempAnswerRes> tempAnswerRes = answerService.queryLikeAnswerByQuestionId(currentUser.getId());
            List<AnswerRes> answerResList = BeanUtils.tran(tempAnswerRes, AnswerRes.class);
            List<String> likeAnswers = answerService.likeAnswers(currentUser.getId());
            List<String> collectAnswer = answerService.collectAnswers(currentUser.getId());
            for (AnswerRes s : answerResList) {
                s.setLike(likeAnswers.contains(s.getId()));
                s.setCollect(collectAnswer.contains(s.getId()));
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
    public List<AnswerRes> getCollctAnwser() {
        String token = getToken();
        LoginRsp loginRsp = redisUtils.getSession(token);
        if (loginRsp != null) {
            JsUser currentUser = getUserByMobile(loginRsp.getMobile());
            List<TempAnswerRes> tempAnswerRes = answerService.queryCollectAnswerByQuestionId(currentUser.getId());
            List<AnswerRes> answerResList = BeanUtils.tran(tempAnswerRes, AnswerRes.class);
            List<String> likeAnswers = answerService.likeAnswers(currentUser.getId());
            List<String> collectAnswer = answerService.collectAnswers(currentUser.getId());
            for (AnswerRes s : answerResList) {
                s.setLike(likeAnswers.contains(s.getId()));
                s.setCollect(collectAnswer.contains(s.getId()));
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
    public List<QuestionRes> getAllQuestion() {
        String token = getToken();
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
    public MyInfo myInfo() {
        String token = getToken();
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
    @RequestLimit(maxCount = 2)
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
        return teamInfoService.rank();
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
     * 判断用户回答是否正确(修改版);
     * @param userAnswerRes
     * @return
     */
    @ResponseBody
    @RequestMapping("/isAllRight")
    public List<ReturnRes> isAllRight(@RequestBody UserAnswerRes1 userAnswerRes) {
        String token = getToken();
        JsUser user = getUserByToken(token);
        List<ReturnRes> returnRes = new ArrayList<>();
        Boolean isWrong = true;
        Integer right = 0;
        String userAnswer = "";
        String rightAnswer = "";

        // 获取问题信息
        Question question = questionService.get(userAnswerRes.getQuestionId());

        // 获取问题列表
        List<QuestionTasks> questionTasks = questionTasksService.findList(new QuestionTasks(question.getId()));

        V8 runtime = V8.createV8Runtime();

        for (QuestionTasks teak : questionTasks) {
            //定时释放内存
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                       Thread.sleep(3000);
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                }
                System.out.println("释放内存");
                runtime.terminateExecution(); }
            }).start();

            ReturnRes res = new ReturnRes();
            String task = "JSON.stringify(" + teak.getTaskQuestion() + ")";
            String userScript = userAnswerRes.getUseranswer() + task;
            String rightScript = question.getRightAnswer() + task;
            try {
                userAnswer = runtime.executeStringScript(userScript);
                rightAnswer = runtime.executeStringScript(rightScript);
                if (userAnswer.equals(rightAnswer)) {
                    res.setAnswer(userAnswer);
                    res.setRight(true);
                    res.setWrong(false);
                    right++;
                } else {
                    res.setAnswer(userAnswer);
                    res.setRight(false);
                    res.setWrong(false);
                }
                returnRes.add(res);
            } catch (Exception e) {
                res.setWrong(isWrong);
                if (StringUtils.isBlank(e.getMessage())) {
                   res.setAnswer("无返回值!");
                } else {
                    if ("null".equals(e.getMessage())) {
                        res.setAnswer("编译超时了,检查下代码吧;");
                    } else {
                        res.setAnswer(e.getMessage());
                    }
                }
                returnRes.add(res);
                return returnRes;
            }
        }
        // 判断用户是否答对
        if (right == questionTasks.size()) {
            String questionId = question.getId();
            // 判断用户是否做过
            Completed completed = new Completed();
            completed.setQuestionId(questionId);
            completed.setUserId(user.getId());
            Long count = completedService.findCount(completed);
            if (count == 0) {
                // 记录用户完成
                completedService.save(completed);
                // 加分逻辑
                user.setRank(user.getRank() + question.getScore());
                jsUserService.update(user);
            }

            // 原来的答案
            ResultRecord record = new ResultRecord();
            record.setMobile(user.getMobile());
            record.setQuestionId(questionId);
            List<ResultRecord> orgAnswers = resultRecordService.findList(record);
            if (orgAnswers.size() > 0) {
                // 修改原来的答案
                ResultRecord orgAnswer = orgAnswers.get(0);
                orgAnswer.setResult(userAnswerRes.getUseranswer());
                resultRecordService.save(orgAnswer);
            } else {
                // 记录用户答案
                record.setResult(userAnswerRes.getUseranswer());
                resultRecordService.save(record);
            }
        }
        runtime.release();
        return returnRes;
    }

    /***
     * 所有人排行榜
     */
    @ResponseBody
    @RequestMapping("/userRank")
    public List<UserRankRes> userRank() {
        return jsUserService.getUserRank();
    }

    /***
     * 按时间排序
     */
    @ResponseBody
    @RequestMapping("/orderByArgs")
    public List<QuestionSearchRes> orderByArgs(@RequestBody QuestionSearchRes res) {
        JsUser user = getUserByMobile(res.getMobile());
        res.setUserId(user.getId());
        if (user != null) {
            List<QuestionSearchRes> list = questionService.queryByArgs(res);
            return list;
        } else {
            return null;
        }
    }

    /***
     * 获取短信验证码
     */
    @RequestLimit(maxCount = 2)
    @ResponseBody
    @RequestMapping("/code")
    public void code (String mobile) {

        String host = "http://cowsms.market.alicloudapi.com";
	    String path = "/intf/smsapi";
	    String method = "GET";
	    String appcode = "1e2e2a3e59cc432587818a3eb1b2f219";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    if (!mobile.isEmpty()) {
	        querys.put("mobile", mobile);
        }
	    String code = RandomStringUtils.random(4,"0123456789");
	    querys.put("paras", code + ",2");
	    querys.put("sign", "消息通");
	    querys.put("tpid", "009");
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
	    }
	    catch (Exception e) {
//	    	e.printStackTrace();
	    	throw new ServiceException("请勿频繁访问接口！");
	    }
    }
}
