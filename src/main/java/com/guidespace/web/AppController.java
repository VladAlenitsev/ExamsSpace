package com.guidespace.web;

import com.guidespace.domain.*;
import com.guidespace.repository.UserRepository;
import com.guidespace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private ExamQuestionAnswerService examQuestionAnswerService;

    @Autowired
    private ClassificatorService classificatorService;

    @Autowired
    private ExamResultService examResultService;
    @Value("${exam.limit}")
    private int timeLimit;

    @RequestMapping("/")
    public String index() {
        return "html/index.html";
    }

    @RequestMapping("/kkk")
    public String kkk() {
        return "html/kkk.html";
    }

    @RequestMapping("/kontakt")
    public String kontakt() {
        return "html/kontakt.html";
    }

    @RequestMapping("/examresults")
    public String examResults() {
        return "html/result.html";
    }

    @RequestMapping("/adminpanel")
    public String panel() {
        return "html/adminpanel.html";
    }

    @RequestMapping("/exam")
    public String exam() {
        return "html/exam.html";
    }

    @RequestMapping("/examreg")
    public String examreg(){ return "html/examreg.html"; }

    @RequestMapping("/question")
    public String question() {
        return "html/question.html";
    }

    @RequestMapping("/questionedit")
    public String questionedit(){return "html/questionedit.html";}

    @RequestMapping(value = "/isAuth", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    @ResponseBody
    public int timeLimit() {
        return timeLimit;
    }

    @RequestMapping(value = "/isAdmin", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        if (leo.getUser_role_id() == 2){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/isVerified", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isVerified() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        if (leo.getUser_role_id() == 4){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/isUnVerified", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isUnVerified() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            Person leo = userService.getUser(authentication.getName());
            if (leo.getUser_role_id() == 1){
                return true;
            }
        }
        catch (Exception e){}
        return false;
    }

    @RequestMapping(value = "/isQuestionAdder", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isQuestionAdder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        if (leo.getUser_role_id() == 6){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/examDone", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isDone() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        Examination openedExam = examinationService.getOpenedExam();
        return examResultService.isNotDone(leo,openedExam);
    }

    @RequestMapping(value = "/giveAdminToSomeone", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String isAuthenticated22(@RequestBody Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(id);
        leo.setUser_role_id(2);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveUnverifiedToSomeone", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String isAuthenticated11(@RequestBody Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(id);
        leo.setUser_role_id(1);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }




    @RequestMapping(value = "/giveVerifiedToSomeone", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String isAuthenticated44(@RequestBody Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(id);
        leo.setUser_role_id(4);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveQuestionAdderToSomeone", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String isAuthenticated66(@RequestBody Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(id);
        leo.setUser_role_id(6);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveAdmin", method = RequestMethod.GET)
    @ResponseBody
    public String isAuthenticated2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        leo.setUser_role_id(2);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }


    @RequestMapping(value = "/isTest", method = RequestMethod.GET)
    @ResponseBody
    public String isAuthenticated3() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveVerified", method = RequestMethod.GET)
    @ResponseBody
    public String isAuthenticated4() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        leo.setUser_role_id(4);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveUnverified", method = RequestMethod.GET)
    @ResponseBody
    public String isAuthenticated1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        leo.setUser_role_id(1);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }

    @RequestMapping(value = "/giveQuestionAdder", method = RequestMethod.GET)
    @ResponseBody
    public String isAuthenticated6() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        leo.setUser_role_id(6);
        userService.update(leo);
        return authentication.getAuthorities().toString();
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void authenticate(String username, String password, String email, String name, String surname,
                             String userBirthDate, String certWorkLangs, String active_cert_location,
                             String cert_exp_date) throws Exception {
        userService.register(username, password, email, name, surname, userBirthDate, certWorkLangs, active_cert_location, cert_exp_date);
    }

    @RequestMapping(value = "/getAnswers", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<String> getAnswers(String question) {
        ExamQuestion uus = examQuestionService.getQuestion(question);
        ArrayList<String> result = new ArrayList<String>();
        for (ExamQuestionAnswer eq : uus.getAnswers()) {
            result.add(eq.getAnswer());
        }
        return result;
    }

    //saves question and 4 answers
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void addQuestion(@RequestBody Map<String, List<String>> params) {

        Classificator classif = classificatorService.getClassifById(Long.valueOf(params.get("classif").get(0)));
        ExamQuestion q = new ExamQuestion(params.get("question").get(0));
        List<ExamQuestionAnswer> answers = new ArrayList<>();

        for (String s : params.get("correctAnswers")) {
            answers.add(new ExamQuestionAnswer(true, s, q));
        }
        for (String s : params.get("wrongAnswers")) {
            answers.add(new ExamQuestionAnswer(false, s, q));
        }
        q.setClassificator(classif);
        q.setAnswers(answers);

        examQuestionService.addQuestion(q);
        for (ExamQuestionAnswer a : answers) {
            examQuestionAnswerService.addQuestionAnswer(a);
        }
        System.out.println("Hibernate: New exam question saved. Question id: " + q.getId());
    }

    @RequestMapping(value = "/updateQuestion", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void updateQuestion(@RequestBody Map<String, List<String>> params) {
        ExamQuestion eq = examQuestionService.getQuestionById(Long.valueOf(params.get("id").get(0)));
        eq.setQuestion(params.get("question").get(0));
        eq.setClassificator(classificatorService.getClassifById(Long.valueOf(params.get("classif").get(0))));

        for(ExamQuestionAnswer eqa: eq.getAnswers()){
            examQuestionAnswerService.deleteQuestionAnswer(eqa);
        }

        List<ExamQuestionAnswer> answers = new ArrayList<>();
        for(String s: params.get("correctAnswers")){
            answers.add(new ExamQuestionAnswer(true, s, eq));
        }
        for(String s: params.get("wrongAnswers")){
            answers.add(new ExamQuestionAnswer(false, s, eq));
        }
        eq.setAnswers(answers);

        eq.setChangedAt(new Date());
        examQuestionService.addQuestion(eq);
        for (ExamQuestionAnswer a : answers) {
            examQuestionAnswerService.addQuestionAnswer(a);
        }
        System.out.println("Hibernate: New exam question updated. Question id: " + eq.getId());

    }

    @RequestMapping(value = "/deleteQuestion", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void deleteQuestion(@RequestBody Map<String, List<String>> params) {
        ExamQuestion eq = examQuestionService.getQuestionById(Long.valueOf(params.get("id").get(0)));
        for(ExamQuestionAnswer eqa: eq.getAnswers()){
            examQuestionAnswerService.deleteQuestionAnswer(eqa);
        }
        examQuestionService.deleteQuestion(eq);
        System.out.println("Hibernate: exam question deleted.");
    }

    @RequestMapping(value = "/addExamination", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void addExamination(@RequestBody Map<String, String> params) throws ParseException {

        Examination e = new Examination(params.get("startdate"), params.get("enddate"));
        e.setClassif_id(Long.valueOf(params.get("classif")));
        e.setIs_open(false);
        examinationService.addExamination(e);
        System.out.println("Hibernate: New examination saved. Examination id: " + e.getId());
    }

    @RequestMapping(value = "/getExaminations", method = {RequestMethod.GET} , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<HashMap<String, String>> getExaminations() {
        ArrayList<HashMap<String, String>> r = new ArrayList<>();
        for(Examination e: examinationService.getExaminations()){
           HashMap<String, String> map = new HashMap<>();
            map.put("id", e.getId().toString());
            map.put("startdate", e.getStart_date().toString());
            map.put("enddate", e.getEnd_date().toString());
            map.put("participants", e.getParticipants_amount().toString());
            map.put("classif_id", e.getClassif_id().toString());
            map.put("classif_name", classificatorService.getClassifById(e.getClassif_id()).getClassif_name());
            map.put("is_open", e.getIs_open().toString());
            map.put("is_deactivated", e.getIs_deactivated().toString());
            r.add(map);
        }
        return r;
    }

    @RequestMapping(value = "/startExamination", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void startExamination(@RequestBody Map<String, String> params) throws ParseException {
        Examination e = examinationService.getById(Long.valueOf(params.get("id")));
        e.setIs_open(true);
        examinationService.addExamination(e);
    }

    @RequestMapping(value = "/closeExamination", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void closeExamination(@RequestBody Map<String, String> params) throws ParseException {
        Examination e = examinationService.getById(Long.valueOf(params.get("id")));
        e.setIs_open(false);
        examinationService.addExamination(e);
    }

    @RequestMapping(value = "/deleteExamination", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void deleteExamination(@RequestBody Map<String, String> params) throws ParseException {
        Examination e = examinationService.getById(Long.valueOf(params.get("id")));
        e.setIs_deactivated(true);
        e.setIs_open(false);
        examinationService.addExamination(e);
    }

    @RequestMapping(value = "/getQuestions", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<String> getQuestions() {
        ArrayList<String> result = new ArrayList<String>();
        for (ExamQuestion eq : examQuestionService.getQuestions()) {
            result.add(eq.getQuestion());
        }
        return result;
    }

    @RequestMapping(value = "/getClassificators", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<Classificator> getClassificators() {
        ArrayList<Classificator> result = new ArrayList<Classificator>();
        for (Classificator eq : classificatorService.getClassificators()) {
            result.add(eq);
        }
        return result;
    }

    @RequestMapping(value = "/getPersons", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<Person> getPersons() {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (Person p : userService.getUsers()) {
            persons.add(p);
        }
        return persons;
    }

    @RequestMapping(value = "/getPerson/{username}", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Person getByUsername(
            @PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @RequestMapping(value = "/findQuestionById/{id}", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ExamQuestion findQuestionById(@PathVariable("id") String id) {
        return examQuestionService.getQuestionById(Long.valueOf(id));
    }

    @RequestMapping(value = "/findAllQuestions", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Long> findAllQuestions(@RequestBody Map<String, String> params) {
        Classificator classif = classificatorService.getClassifById(Long.valueOf(params.get("classif")));
        HashMap<String, Long> m = new HashMap<>();
        // no text, has classif
        if(params.get("searchText").isEmpty() && !params.get("classif").equals("-1")){
            for(ExamQuestion eq:examQuestionService.getQuestions()) {
                if (eq.getClassificator().equals(classif)) {
                    m.put(eq.getQuestion(), eq.getId());
                }
            }
        // has text, has classif
        }else if(!params.get("searchText").isEmpty() && !params.get("classif").equals("-1")){
            for(ExamQuestion eq:examQuestionService.getQuestions()){
                if(eq.getQuestion().contains(params.get("searchText")) &&
                        eq.getClassificator().equals(classif)){
                    m.put(eq.getQuestion(), eq.getId());
                }
            }
        //has text, no classif
        }else if(!params.get("searchText").isEmpty() && params.get("classif").equals("-1")){
            for(ExamQuestion eq:examQuestionService.getQuestions()){
                if(eq.getQuestion().contains(params.get("searchText"))){
                    m.put(eq.getQuestion(), eq.getId());
                }
            }
        //no text no classif
        }else{
            for(ExamQuestion eq: examQuestionService.getQuestions()){
                m.put(eq.getQuestion(), eq.getId());
            }
        }
        return m;
    }

    @RequestMapping(value = "/getAllQuestions", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, List<String>> getAllQuestions() {
        Examination exam = examinationService.getOpenedExam();
        HashMap<String, List<String>> uus = new HashMap<>();
        for (ExamQuestion eq : examQuestionService.getQuestions()) {
            if(Objects.equals(eq.getClassificator().getId(), exam.getClassif_id())) {
                ArrayList<String> result = new ArrayList<String>();
                for (ExamQuestionAnswer eq2 : eq.getAnswers()) {
                    result.add(eq2.getAnswer());
                }
                uus.put(eq.getQuestion(), result);
            }
        }
        return uus;
    }


    @RequestMapping(value = "/showResultsAdmin", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<HashMap<String, String>> showAdminResults() {
        ArrayList<HashMap<String, String>> r = new ArrayList<>();
        for(ExamResult ex: examResultService.getExamResults()){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(ex.getId()));
            map.put("passed", String.valueOf(ex.getPassed()));
            map.put("score", String.valueOf(ex.getScore()));
            map.put("name", ex.getPerson().getName()+ " "+ ex.getPerson().getSurname());
            map.put("examstart", String.valueOf(ex.getExamination().getStart_date()));
            map.put("examend", String.valueOf(ex.getExamination().getEnd_date()));
            map.put("examclassif", classificatorService.getClassifById(ex.getExamination().getClassif_id()).getClassif_name());
            r.add(map);
        }
        return r;
    }
    @RequestMapping(value = "/showResultsUser", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<HashMap<String, String>> showUserResults() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        ArrayList<HashMap<String, String>> r = new ArrayList<>();
        for(ExamResult ex: examResultService.getExamResults()){
            if(ex.getPerson().equals(leo)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(ex.getId()));
                map.put("passed", String.valueOf(ex.getPassed()));
                map.put("score", String.valueOf(ex.getScore()));
                map.put("name", ex.getPerson().getName() + " " + ex.getPerson().getSurname());
                map.put("examstart", String.valueOf(ex.getExamination().getStart_date()));
                map.put("examend", String.valueOf(ex.getExamination().getEnd_date()));
                map.put("examclassif", classificatorService.getClassifById(ex.getExamination().getClassif_id()).getClassif_name());
                r.add(map);
            }
        }
        return r;
    }

    //[id, q, clas, an1, a1tf, ]
    @RequestMapping(value = "/findQuestionWithAnswers/{id}", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<String> findQuestionWithAnswers(@PathVariable("id") String id){
        ArrayList<String> lst = new ArrayList<>();

        for(ExamQuestion eq : examQuestionService.getQuestions()){
            if(eq.getId().toString().equals(id)){

                lst.add(eq.getId().toString());
                lst.add(eq.getQuestion());
                lst.add(eq.getClassificator().getId().toString());

                for(int i =0; i<4; i++) {
                    lst.add(eq.getAnswers().get(i).getAnswer());
                    lst.add(eq.getAnswers().get(i).getIsCorrect().toString());
                }
            }
        }
        return lst;
    }

    @RequestMapping(value = "/getUsers", method = {RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<String> getUsers() {
        ArrayList<String> result = new ArrayList<String>();
        for (Person p : userService.getUsers()) {
            result.add(p.getUsername());
        }
        return result;
    }


    @RequestMapping(value = "/userAnsw", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String getValues(@RequestBody Map<String, List<String>> hmap) {
        int size = hmap.size();
        double counter = 0;
        for (String key : hmap.keySet()) {
            ExamQuestion examQuestion = examQuestionService.getQuestion(key);
            List<String> trueQuestionAnswers = examQuestion.getRightAnswers();
            List<String> gotQuestionAnswers = hmap.get(key);
            if (trueQuestionAnswers.size() == gotQuestionAnswers.size()) {
                for (String gotQuest : gotQuestionAnswers) {
                    if (!trueQuestionAnswers.contains(gotQuest)) {
                        counter += 1;
                        break;
                    }
                }
            } else counter += 1;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person leo = userService.getUser(authentication.getName());
        Examination ex = examinationService.getOpenedExam();
        Integer passPercent = Math.toIntExact(Math.round(((hmap.size() - counter) / hmap.size()) * 100));
        ExamResult examResult;
        if(!examResultService.isNotDone(leo,ex)) return "You have already done that exam!";
        if(passPercent>75){
            examResult = new ExamResult(true,passPercent,leo,ex);
            examResultService.save(examResult);
            return "Good Job! You passed! You got: "+passPercent+"%";
        }
        examResult = new ExamResult(false,passPercent,leo,ex);
        examResultService.save(examResult);
        return "Sorry, but you did not pass. You got: "+passPercent+"%";
    }


    /**
     * this method use for developing adding
     * classificator
     * question
     * answer
     * etc
     * to local database which is blown up after app restart
     *
     * keep method empty on master & heroku or if you
     * don't want to add anything
     *
     * */

    /**
    @RequestMapping(value = "/addQuests")
    @ResponseBody
    public void addQuests() throws ParseException {

        Classificator classifi = new Classificator("type1","code2","name3");
        classificatorService.save(classifi);
        Classificator classifii = new Classificator("1111t","2222c","3333n");
        classificatorService.save(classifii);
        Examination e = new Examination("29-06-2016 10:30", "29-06-2016 11:30");
        e.setClassif_id(classifi.getId());
        e.setIs_open(true);
        examinationService.addExamination(e);
        System.out.println("Hibernate: New examination saved. Examination id: " + e.getId());

        //Admin123 Admin123
        Person p = new Person("Admin123", "fY6ITvPFf/+aY8+0XiuTjCkI+cGT1uIJM1lVweLN4gXFpHTUepJFJVRQEp37OoF7/F+ve6FWoV2DbLx5yFEX6w==",
                "12nkMg7gZbVWn8WqODi3DcrU46yZ3EWbUvh/miAlHHE=", "admin@gmail.com",
                "admin", "surname", new Date(), "wurk lengs", "activ cert locs", new Date());
        p.setUser_role_id(2);
        userRepository.save(p);

         ExamQuestion b = new ExamQuestion("Esimene Küsimus(first 2 are correct)");
         ExamQuestion b1 = new ExamQuestion("Teine Küsimus(first 2 are correct)");
         ExamQuestion b2 = new ExamQuestion("Kolmas Küsimus(first 2 are correct)");
         String a1 = "Yes this is.";
         String a2 = "No, it's not.";
         String a3 = "Maybe.";
         String a4 = "It's classified.";
         String c1 = "Yes this is.";
         String c2 = "No, it's not.";
         String c3 = "Maybe.";
         String c4 = "It's classified.";
         String d1 = "Yes this is.";
         String d2 = "No, it's not.";
         String d3 = "Maybe.";
         String d4 = "It's classified.";
         Boolean tf = true;
         Boolean mf = false;
         ExamQuestionAnswer ea1 = new ExamQuestionAnswer(tf, a1);
         ExamQuestionAnswer ea2 = new ExamQuestionAnswer(tf, a2);
         ExamQuestionAnswer ea3 = new ExamQuestionAnswer(mf, a3);
         ExamQuestionAnswer ea4 = new ExamQuestionAnswer(mf, a4);
         ExamQuestionAnswer ec1 = new ExamQuestionAnswer(tf, c1);
         ExamQuestionAnswer ec2 = new ExamQuestionAnswer(tf, c2);
         ExamQuestionAnswer ec3 = new ExamQuestionAnswer(mf, c3);
         ExamQuestionAnswer ec4 = new ExamQuestionAnswer(mf, c4);
         ExamQuestionAnswer ed1 = new ExamQuestionAnswer(tf, d1);
         ExamQuestionAnswer ed2 = new ExamQuestionAnswer(tf, d2);
         ExamQuestionAnswer ed3 = new ExamQuestionAnswer(mf, d3);
         ExamQuestionAnswer ed4 = new ExamQuestionAnswer(mf, d4);
         b.getAnswers().add(ea1);
         b.getAnswers().add(ea2);
         b.getAnswers().add(ea3);
         b.getAnswers().add(ea4);
         b1.getAnswers().add(ec1);
         b1.getAnswers().add(ec2);
         b1.getAnswers().add(ec3);
         b1.getAnswers().add(ec4);
         b2.getAnswers().add(ed1);
         b2.getAnswers().add(ed2);
         b2.getAnswers().add(ed3);
         b2.getAnswers().add(ed4);
         ea1.setExamQuestion(b);
         ea2.setExamQuestion(b);
         ea3.setExamQuestion(b);
         ea4.setExamQuestion(b);
         ec1.setExamQuestion(b1);
         ec2.setExamQuestion(b1);
         ec3.setExamQuestion(b1);
         ec4.setExamQuestion(b1);
         ed1.setExamQuestion(b2);
         ed2.setExamQuestion(b2);
         ed3.setExamQuestion(b2);
         ed4.setExamQuestion(b2);
         examQuestionService.addQuestion(b);
         examQuestionService.addQuestion(b1);
         examQuestionService.addQuestion(b2);
         examQuestionAnswerService.addQuestionAnswer(ea1);
         examQuestionAnswerService.addQuestionAnswer(ea2);
         examQuestionAnswerService.addQuestionAnswer(ea3);
         examQuestionAnswerService.addQuestionAnswer(ea4);
         examQuestionAnswerService.addQuestionAnswer(ec1);
         examQuestionAnswerService.addQuestionAnswer(ec2);
         examQuestionAnswerService.addQuestionAnswer(ec3);
         examQuestionAnswerService.addQuestionAnswer(ec4);
         examQuestionAnswerService.addQuestionAnswer(ed1);
         examQuestionAnswerService.addQuestionAnswer(ed2);
         examQuestionAnswerService.addQuestionAnswer(ed3);
         examQuestionAnswerService.addQuestionAnswer(ed4);
    }*/
}