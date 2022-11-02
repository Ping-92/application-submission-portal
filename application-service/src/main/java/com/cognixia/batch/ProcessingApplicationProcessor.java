package com.cognixia.batch;

import org.springframework.batch.item.ItemProcessor;

import com.cognixia.model.Application;

public class ProcessingApplicationProcessor implements ItemProcessor<Application, Application> {

	@Override
	public Application process(Application application) throws Exception {
		
		application.setApplicationStatus("Processed-EmailNotSent");
		return application;
	}
}