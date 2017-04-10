package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {
        Job jobs = jobData.findById(id);
        model.addAttribute("jobs", jobs);
        model.addAttribute("title", "Single Job");

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {


        Job newJob = new Job();



        if(errors.hasErrors()){
            return "new-job";
        }
        String nam = jobForm.getName();
        newJob.setName(nam);
        Employer anEmp = jobData.getEmployers().findById(jobForm.getEmployerId());
        newJob.setEmployer(anEmp);
        Location anLoc = jobData.getLocations().findById(jobForm.getLocationsId());
        newJob.setLocation(anLoc);
        PositionType anPos = jobData.getPositionTypes().findById(jobForm.getPositionTypesId());
        newJob.setPositionType(anPos);
        CoreCompetency anCore = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());
        newJob.setCoreCompetency(anCore);
        jobData.add(newJob);
        int jonum = newJob.getId();
        String strnum = String.valueOf(jonum);



        return "redirect:/job?id="+strnum;
        }


        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.




}
