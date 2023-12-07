package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountsPayableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountsPayableDTO.class);
        AccountsPayableDTO accountsPayableDTO1 = new AccountsPayableDTO();
        accountsPayableDTO1.setId(1L);
        AccountsPayableDTO accountsPayableDTO2 = new AccountsPayableDTO();
        assertThat(accountsPayableDTO1).isNotEqualTo(accountsPayableDTO2);
        accountsPayableDTO2.setId(accountsPayableDTO1.getId());
        assertThat(accountsPayableDTO1).isEqualTo(accountsPayableDTO2);
        accountsPayableDTO2.setId(2L);
        assertThat(accountsPayableDTO1).isNotEqualTo(accountsPayableDTO2);
        accountsPayableDTO1.setId(null);
        assertThat(accountsPayableDTO1).isNotEqualTo(accountsPayableDTO2);
    }
}
