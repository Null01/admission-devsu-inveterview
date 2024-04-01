package karate.account;

import com.intuit.karate.junit5.Karate;

public class AccountRunnerTest {

    @Karate.Test
    Karate testAccountWhenCreateNewAccountAndTransactions() {
        return Karate.run("classpath:karate/account/accountsV1.feature")
                .relativeTo(getClass());
    }
}
