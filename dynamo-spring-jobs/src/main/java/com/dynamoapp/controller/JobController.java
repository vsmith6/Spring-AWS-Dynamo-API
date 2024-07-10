package com.dynamoapp.controller;



import com.dynamoapp.model.Job;
import com.dynamoapp.repository.JobRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping
    public void createJob(@RequestBody Job job) {
        jobRepository.save(job);
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable String id) {
        return jobRepository.getById(id);
    }

    @PutMapping("/{id}")
    public void updateJob(@PathVariable String id, @RequestBody Job job) {
        job.setId(id);
        jobRepository.update(job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable String id) {
        jobRepository.delete(id);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    
}
