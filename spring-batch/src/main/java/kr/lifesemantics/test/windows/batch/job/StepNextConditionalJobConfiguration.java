package kr.lifesemantics.test.windows.batch.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job stepNextConditionalJob() {
		return jobBuilderFactory.get("stepNextConditionalJob")
								.start(conditionalJobStep1())		//	   job 시작하는데, if(JobStep1() ==
									.on("FAILED")					//on - 				    			 FAILED) {
									.to(conditionalJobStep3())		//to - 	~~~Step3() 을 실행하는데? 
									.on("*")						//on - 3의 결과 상관 없이
									.end()							//end- 끝내
								.from(conditionalJobStep1())		//	   else(conditionalJobStep(1)
									.on("*")						//								  != FAILED) {
									.to(conditionalJobStep2())		//	   무조건 JobStep2() 로 가, 그리고 정상적으로 step2()가 종료되면
									.next(conditionalJobStep3())	//next- step3() 의 결과
									.on("*")						//					 관계없이
									.end()							//							flow가 끝나
								.end()
							.build();
		
		/**on flow, start = flow open (start shooting
		//		    from = else (shooting
		//		    end = flow close (finish shooting
		//		    on = set ExitStatus for catch, [* = all situation] (condition that pulling trigger
		//			to = point next working,  (after shooting, secondary action
		 *			 
		**/
		
	}
	
//////////////////////////////////////////////////////////////////////////
	//ExitStatus를 커스텀 하고 싶을 때	
	public class SkipCheckingListener extends StepExecutionListenerSupport {

	    public ExitStatus afterStep(StepExecution stepExecution) {
	        String exitCode = stepExecution.getExitStatus().getExitCode();
	        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) && 
	              stepExecution.getSkipCount() > 0) {
	            return new ExitStatus("COMPLETED WITH SKIPS");
	        }
	        else {
	            return null;
	        }
	    }
	}
///////////////////////////////////////////////////////////////////////////
	
	
	@Bean
	public Step conditionalJobStep1() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step1");
					
					/** 
					 * 	ExitStatus를 Failed로 지정한다.
					 *  해당 status를 보고 flow가 진행된다.
					 *  
					 * **/
					//contribution.setExitStatus(ExitStatus.FAILED);
					
					return RepeatStatus.FINISHED;
				}).build();
	}
	
	@Bean
	public Step conditionalJobStep2() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("conditionalJobStep2")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step2");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step conditionalJobStep3() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("conditionalJobStep3")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step3");
					return RepeatStatus.FINISHED;
				}).build();
	}

}
