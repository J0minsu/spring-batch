package kr.lifesemantics.test.windows.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration	//Spring batch 의 모든 job 은 Configuration 으로 등록
@Slf4j
public class SimpleJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job simpleJob() {
		
				//simpleJob 이라는 Batch Job을 생성합니다.
		return jobBuilderFactory.get("simpleJob")
								.start(simpleStep1(null))
								.next(simpleStep2(null))
								.build();
	}

	
//	@Bean
//	@JobScope
//	public Step simpleStep1(@Value("#{jobParameters[requestDate]}")String reqeustDate) {
//
//				//simpleStep1 이라는 Batch Step을 생성합니다.
//		return stepBuilderFactory.get("simpleStep1")
//				//Step 안에서 실행될 기능들을 명시합니다.
//				//Step 안에서 단일로 실행될 커스텀한 기능들을 선언
//				//해당 Batch가 실행되면 아래의 log 출력
//				.tasklet((contribution, chunkContext) -> {
//					log.info(">>>> This is Step1");
//					log.info(">>>> RequestDate = {}", reqeustDate);
//					return RepeatStatus.FINISHED;
//				}).build();
//	}
	
	@Bean
	@JobScope
	public Step simpleStep1(@Value("#{jobParameters[requestDate]}")String reqeustDate) {

				//simpleStep1 이라는 Batch Step을 생성합니다.
				//일부러 에러를 발생해 경과를 봅니다.
		return stepBuilderFactory.get("simpleStep1")
								 .tasklet((contribution, chunkContext) -> {
									 log.info(">>>> This is Step1");
									 log.info(">>>> RequestDate = {}", reqeustDate);
									 return RepeatStatus.FINISHED;
							      }).build();
	}
	
	@Bean
	@JobScope
	public Step simpleStep2(@Value("#{jobParameters[requestDate]}")String reqeustDate) {

				//simpleStep1 이라는 Batch Step을 생성합니다.
		return stepBuilderFactory.get("simpleStep2")
				//Step 안에서 실행될 기능들을 명시합니다.
				//Step 안에서 단일로 실행될 커스텀한 기능들을 선언
				//해당 Batch가 실행되면 아래의 log 출력
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>> This is Step2");
					log.info(">>>> RequestDate = {}", reqeustDate);
					return RepeatStatus.FINISHED;
				}).build();
	}

}
