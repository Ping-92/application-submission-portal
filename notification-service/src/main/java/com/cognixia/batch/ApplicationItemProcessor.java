package com.cognixia.batch;

import org.springframework.batch.item.ItemProcessor;

import com.cognixia.model.Application;

public class ApplicationItemProcessor implements ItemProcessor<Application, Application> {

	@Override
	public Application process(Application application) throws Exception {
		return application;
	}

}
