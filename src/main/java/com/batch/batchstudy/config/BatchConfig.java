package com.batch.batchstudy.config;

import com.batch.batchstudy.listener.JobCompletionNotificationListener;
import com.batch.batchstudy.mapper.MemberMapper;
import com.batch.batchstudy.processor.MemberCopyProcessor;
import com.batch.batchstudy.vo.Member;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Bean
    public Job memberCopyJob(JobBuilderFactory jobBuilderFactory, Step step1) throws Exception {
        return jobBuilderFactory.get("memberCopyJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) throws Exception {
        return stepBuilderFactory.get("step1")
                .<Member, Member>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Member> reader() {
        return new JdbcCursorItemReaderBuilder<Member>()
                .fetchSize(10)
                .dataSource(dataSource())
                .rowMapper(new BeanPropertyRowMapper<>(Member.class))
                .sql("SELECT id, name, last_login FROM member")
                .name("reader")
                .build();
    }

    @Bean
    public MemberCopyProcessor processor() {
        return new MemberCopyProcessor();
    }

    @Bean
    public ItemWriter<Member> writer(){
        return new JdbcBatchItemWriterBuilder<Member>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE member set last_login = :last_login where id = :id")
                .dataSource(dataSource())
                .build();
    }

    @Bean
    public JobCompletionNotificationListener listener(){
        return new JobCompletionNotificationListener();
    }

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/postgres");
        hikariConfig.setUsername("yangseungbin");
        hikariConfig.setPassword("yang123");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

//    @Bean
//    public JobLauncher jobLauncher() throws Exception {
//        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//        jobLauncher.setJobRepository(jobRepository());
//        jobLauncher.afterPropertiesSet();
//        return jobLauncher;
//    }

//    @Bean
//    PlatformTransactionManager transactionManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }


//    @Bean
//    protected JobRepository jobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource());
//        factory.setTransactionManager(transactionManager());
//        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
//        factory.setTablePrefix("BATCH_");
//        factory.setMaxVarCharLength(1000);
//        return factory.getObject();
//    }

//    @Bean
//    public Job processJob() {
//        return jobBuilderFactory.get("processJob")
//                .incrementer(new RunIdIncrementer()).listener(listener())
//                .flow(orderStep1()).end().build();
//    }
//
//    @Bean
//    public Step orderStep1() {
//        return stepBuilderFactory.get("orderStep1").<String, String> chunk(1)
//                .reader(new Reader()).processor(new Processor())
//                .writer(new Writer()).build();
//    }
//    @Bean
//    @JobScope
//    public JdbcPagingItemReader<Member> reader() throws Exception {
//        Map<String, Object> parameterValues = new HashMap<>();
//        parameterValues.put("amount", 2000);
//
//    return new JdbcPagingItemReaderBuilder<Member>().name("reader")
//            .dataSource(dataSource())
//            .pageSize(10)
//            .fetchSize(10)
//            .queryProvider(createQueryProvider())
//            .parameterValues(parameterValues)
//            .rowMapper(new BeanPropertyRowMapper<>(Member.class))
//            .build();
//    }
//    @Bean
//    public PagingQueryProvider createQueryProvider() throws Exception{
//        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource());
//        queryProvider.setSelectClause("id, name");
//        queryProvider.setFromClause("from member");
//        Map<String, Order> sortKeys = new HashMap<>(100);
//        sortKeys.put("id", Order.ASCENDING);
//        queryProvider.setSortKeys(sortKeys);
//
//      return queryProvider.getObject();
//    }
//    @Bean
//    public ItemWriter<Member> writer(){
//    return new JdbcBatchItemWriterBuilder<Member>()
//            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//            .sql("INSERT INTO member (id, name) VALUES(:id, :name)")
//            .dataSource(dataSource())
//            .build();
//    }
}
