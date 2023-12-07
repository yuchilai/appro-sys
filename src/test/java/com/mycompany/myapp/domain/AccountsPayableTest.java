package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AccountsPayableTestSamples.*;
import static com.mycompany.myapp.domain.ClientCompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountsPayableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountsPayable.class);
        AccountsPayable accountsPayable1 = getAccountsPayableSample1();
        AccountsPayable accountsPayable2 = new AccountsPayable();
        assertThat(accountsPayable1).isNotEqualTo(accountsPayable2);

        accountsPayable2.setId(accountsPayable1.getId());
        assertThat(accountsPayable1).isEqualTo(accountsPayable2);

        accountsPayable2 = getAccountsPayableSample2();
        assertThat(accountsPayable1).isNotEqualTo(accountsPayable2);
    }

    @Test
    void clientCompanyTest() throws Exception {
        AccountsPayable accountsPayable = getAccountsPayableRandomSampleGenerator();
        ClientCompany clientCompanyBack = getClientCompanyRandomSampleGenerator();

        accountsPayable.setClientCompany(clientCompanyBack);
        assertThat(accountsPayable.getClientCompany()).isEqualTo(clientCompanyBack);

        accountsPayable.clientCompany(null);
        assertThat(accountsPayable.getClientCompany()).isNull();
    }
}
