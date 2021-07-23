package kr.lifesemantics.test.windows.batch.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.lifesemantics.test.windows.batch.job.model.Pay;
import kr.lifesemantics.test.windows.batch.job.model.Pay2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaItemWriterJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;
	
	private static final int CHUNK_SIZE = 10;
	
	@Bean
	public Job jpaItemWriterJob() {
		return jobBuilderFactory.get("jpaItemWriterJob")
								.start(jpaItemWriterStep())
								.build();
	}

	@Bean
	public Step jpaItemWriterStep() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("jpaItemWriterStep")
								 .<Pay, Pay2>chunk(CHUNK_SIZE)
								 .reader(jpaItemWriterReader())
								 .processor(jpaItemProcessor())
								 .writer(jpaItemWriter())
								 .build();
	}

	@Bean
	public ItemReader<Pay> jpaItemWriterReader() {
		// TODO Auto-generated method stub
		return new JpaPagingItemReaderBuilder<Pay>()
					.name("jpaItemWriterReader")
					.entityManagerFactory(entityManagerFactory)
					.pageSize(CHUNK_SIZE)
					.queryString("Select p From Pay p")
					.build();
	}

	@Bean
	public ItemProcessor<Pay, Pay2> jpaItemProcessor() {
		// TODO Auto-generated method stub
		return pay -> new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
	}

//	@Bean
//	public JpaItemWriter<Pay2> jpaItemWriter() {
//		// TODO Auto-generated method stub
//		JpaItemWriter<Pay2> jpaItemWriter = new JpaItemWriter<Pay2>();
//		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
//		return jpaItemWriter;
//	}
	
	@Bean
	public ItemWriter<Pay2> jpaItemWriter() {
		// TODO Auto-generated method stub
		
		return items -> {
			for( Pay2 item : items) {
				System.out.println(item.toString());
			}
		};
	}
}
