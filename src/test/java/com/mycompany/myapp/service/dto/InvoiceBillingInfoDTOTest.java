package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceBillingInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceBillingInfoDTO.class);
        InvoiceBillingInfoDTO invoiceBillingInfoDTO1 = new InvoiceBillingInfoDTO();
        invoiceBillingInfoDTO1.setId(1L);
        InvoiceBillingInfoDTO invoiceBillingInfoDTO2 = new InvoiceBillingInfoDTO();
        assertThat(invoiceBillingInfoDTO1).isNotEqualTo(invoiceBillingInfoDTO2);
        invoiceBillingInfoDTO2.setId(invoiceBillingInfoDTO1.getId());
        assertThat(invoiceBillingInfoDTO1).isEqualTo(invoiceBillingInfoDTO2);
        invoiceBillingInfoDTO2.setId(2L);
        assertThat(invoiceBillingInfoDTO1).isNotEqualTo(invoiceBillingInfoDTO2);
        invoiceBillingInfoDTO1.setId(null);
        assertThat(invoiceBillingInfoDTO1).isNotEqualTo(invoiceBillingInfoDTO2);
    }
}
