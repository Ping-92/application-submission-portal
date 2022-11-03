package com.cognixia.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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


@Service
public class BatchJobService {
	@Autowired
	private JobLauncher jobLauncher;

	@Qualifier("writeApplicationDataIntoSqlDb")
	@Autowired
	Job writeApplicationDataIntoSqlDb;


	
	@Scheduled(cron = "0 27 12 * * ?")
	public void firstJobStarter() {
		Map<String, JobParameter> params = new HashMap<>();

		// put current time in millseconds so current job will be unique everytime
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));

		JobParameters jobParameters = new JobParameters(params);
		try {
			JobExecution jobExecution = 
					jobLauncher.run(writeApplicationDataIntoSqlDb, jobParameters);
			System.out.println("Job Execution Id = " +jobExecution.getJobId());
			TimeUnit.SECONDS.sleep(5);
			
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

	//download json file from SFTP
	@Scheduled(cron = "0 26 12 * * ?")
	public static void downloadFile() throws IOException {
		System.out.println("Downloading...");
		Path currentRelativePath = Paths.get("");
		String abs = currentRelativePath.toAbsolutePath().toString();
		File source = new File("C:\\tmp\\ftp\\upload\\submissions.json");
		File dest = new File(abs+"/src/main/resources/submissions.json");
		
		InputStream is = null;
		OutputStream os = null;
		try {
			int length;
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			
			while((length = is.read(buffer)) > 0) {
				os.write(buffer,0,length);
			}
		} finally {
			is.close();
			os.close();
		}
		
	}
}
