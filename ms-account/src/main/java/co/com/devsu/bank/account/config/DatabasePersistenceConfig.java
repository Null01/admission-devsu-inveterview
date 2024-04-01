package co.com.devsu.bank.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import co.com.devsu.bank.account.properties.DatabaseApplicationProperties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author andresfelipegarciaduran
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager",
        basePackages = {"co.com.devsu.bank.account.model.repository"})
public class DatabasePersistenceConfig {

    private final DatabaseApplicationProperties property;

    @Autowired
    public DatabasePersistenceConfig(DatabaseApplicationProperties databaseApplicationProperties) {
        this.property = databaseApplicationProperties;
    }

    @Primary
    @Bean(name = "mainDataSourceExtension")
    public DataSource mainDataSourceExtension() {
        final DatabaseApplicationProperties.DataSource.Properties properties = property.getMain().getDatasource();
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .driverClassName(properties.getDriverClassName())
                .username(properties.getUsername())
                .password(properties.getPassword()).build();
    }

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("mainDataSourceExtension") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("co.com.devsu.bank.account.model.entities").persistenceUnit("pu-ms-account").build();
    }

    @Primary
    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(@Qualifier("mainEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
