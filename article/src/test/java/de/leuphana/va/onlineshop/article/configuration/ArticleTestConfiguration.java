package de.leuphana.va.onlineshop.article.configuration;

import de.leuphana.va.onlineshop.article.connector.ArticleSpringDataConnectorRequester;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@TestConfiguration
@EnableTransactionManagement
public class ArticleTestConfiguration {

    @Bean
    public DataSource testRestDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/article");
        dataSource.setUsername("Shop");
        dataSource.setPassword("Shop");
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean testSessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(testRestDataSource());
        sessionFactory.setPackagesToScan("de.leuphana.va.onlineshop.article.component.structure");
        sessionFactory.setHibernateProperties(testHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public ArticleSpringDataConnectorRequester testArticleSpringDataConnectorRequester() {
        return new ArticleSpringDataConnectorRequester();
    }

    @Bean
    public PlatformTransactionManager testHibernateTransactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(testSessionFactory().getObject());
        return transactionManager;
    }

    private Properties testHibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.default_batch_fetch_size", "50");
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "50");
        return hibernateProperties;
    }
}
