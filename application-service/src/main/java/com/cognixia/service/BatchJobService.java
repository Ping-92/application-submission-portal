package com.cognixia.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.JobExecution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cognixia.SftpConfig.UploadGateway;

@Service
public class BatchJobService {
	@Autowired
	private UploadGateway gateway;

	@Autowired
	private JobLauncher jobLauncher;

	@Qualifier("writeApplicationDataIntoJson")
	@Autowired
	Job writeApplicationDataIntoJson;


	
	@Scheduled(cron = "0 23 20 1/1 * ?")
	public void firstJobStarter() {
		Map<String, JobParameter> params = new HashMap<>();

		// put current time in millseconds so current job will be unique everytime
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));

		JobParameters jobParameters = new JobParameters(params);
		try {

			JobExecution jobExecution = 
					jobLauncher.run(writeApplicationDataIntoJson, jobParameters);
			System.out.println("Job Execution Id = " +jobExecution.getJobId());
			TimeUnit.SECONDS.sleep(5);
			fileUpload();
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	// upload file to sftp
	public boolean fileUpload() {
		Path currentRelativePath = Paths.get("");
		String abs = currentRelativePath.toAbsolutePath().toString();
		gateway.upload(new File(abs+"/src/main/resources/submissions.json"));
		return true;
	}
}
