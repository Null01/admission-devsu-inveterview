package co.com.devsu.bank.account.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author andresduran0205@gmail.com
 */
@Component
@Data
@ConfigurationProperties(prefix = "databases")
public class DatabaseApplicationProperties {

    private Security security;
    private DataSource main;

    @Data
    @ToString
    public static class Security {
        private boolean encrypt;
    }

    @Data
    public static class DataSource {

        @Data
        @ToString
        public static class Properties {
            private String url;
            private String username;
            private String password;
            private String driverClassName;
        }

        private Properties datasource;
    }

}
