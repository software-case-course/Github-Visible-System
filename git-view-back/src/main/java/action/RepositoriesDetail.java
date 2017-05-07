package action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.SearchDetail;

@Controller
@CrossOrigin
@RequestMapping("/repos")
public class RepositoriesDetail{
    @Autowired
    SearchDetail searchDetail;

    @ResponseBody
    @RequestMapping("/Contributor")
    public String ContributorStat(String fullname){
        return searchDetail.getContributorStat(fullname);
    }

    @ResponseBody
    @RequestMapping("/WeeklyCommit")
    public String WeeklyCommit(String fullname){
        return searchDetail.getWeeklyCommit(fullname);
    }

    @ResponseBody
    @RequestMapping("/CommitActivity")
    public String CommitActivity(String fullname){
        return searchDetail.getCommitActivity(fullname);
    }

    @ResponseBody
    @RequestMapping("/PunchCard")
    public String PunchCard(String fullname){
        return searchDetail.getPunchCard(fullname);
    }

    @ResponseBody
    @RequestMapping("/CodeFrequency")
    public String CodeFrequency(String fullname){
        return searchDetail.getCodeFrequency(fullname);
    }
}