package com.webgenerals.billingjob.batchconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FilePreparationTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
		String inputFile = jobParameters.getString("input.file");
		Path source = Paths.get(inputFile);
		Path stagingDir = Paths.get("staging");

		if (!Files.exists(stagingDir)) {
			log.debug("Creating staging directory at {}", stagingDir.toAbsolutePath());
			Files.createDirectories(stagingDir);
		}

		Path target = stagingDir.resolve(source.getFileName());

		log.info("Copying file from {} to {}", source.toAbsolutePath(), target.toAbsolutePath());
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

		return RepeatStatus.FINISHED;
	}
}
