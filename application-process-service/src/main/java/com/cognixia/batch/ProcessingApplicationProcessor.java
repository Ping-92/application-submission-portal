package com.cognixia.batch;

import org.springframework.batch.item.ItemProcessor;

import com.cognixia.model.PermApplication;

public class ProcessingApplicationProcessor implements ItemProcessor<PermApplication, PermApplication> {

	@Override
	public PermApplication process(PermApplication application) throws Exception {
		
		application.setApplicationStatus("Processed-EmailNotSent");
		return application;
	}
}