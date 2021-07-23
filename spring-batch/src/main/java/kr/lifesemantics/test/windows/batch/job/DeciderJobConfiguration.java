package kr.lifesemantics.test.windows.batch.job;

import java.util.Random;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeciderJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job deciderJob() {
		return jobBuilderFactory.get("deciderJob")
									.start(startStep())
										.next(decider())
											.from(decider())
												.on("ODD")
												.to(oddStep())
											.from(decider())
												.on("EVEN")
												.to(evenStep())
									.end()
								.build();
	}
	

	@Bean
	public Step startStep() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("startStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> Start!");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step oddStep() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("oddStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> 홀수당!");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step evenStep() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("evenStep")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> 짝수당!");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public JobExecutionDecider decider() {
		// TODO Auto-generated method stub
		return new OddDecider();
	}
	
	public static class OddDecider implements JobExecutionDecider {

		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			// TODO Auto-generated method stub
			Random random = new Random();
			
			int randomNumber = random.nextInt(50) + 1;
			log.info("난수 발생!!! : {}", randomNumber);
			
				if(randomNumber % 2 == 0) return new FlowExecutionStatus("EVEN");
									 else return new FlowExecutionStatus("ODD");

		}
		
	}
	
}
