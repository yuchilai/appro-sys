package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.InvoiceBillingInfoTestSamples.*;
import static com.mycompany.myapp.domain.InvoiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceBillingInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceBillingInfo.class);
        InvoiceBillingInfo invoiceBillingInfo1 = getInvoiceBillingInfoSample1();
        InvoiceBillingInfo invoiceBillingInfo2 = new InvoiceBillingInfo();
        assertThat(invoiceBillingInfo1).isNotEqualTo(invoiceBillingInfo2);

        invoiceBillingInfo2.setId(invoiceBillingInfo1.getId());
        assertThat(invoiceBillingInfo1).isEqualTo(invoiceBillingInfo2);

        invoiceBillingInfo2 = getInvoiceBillingInfoSample2();
        assertThat(invoiceBillingInfo1).isNotEqualTo(invoiceBillingInfo2);
    }

    @Test
    void invoiceTest() throws Exception {
        InvoiceBillingInfo invoiceBillingInfo = getInvoiceBillingInfoRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        invoiceBillingInfo.setInvoice(invoiceBack);
        assertThat(invoiceBillingInfo.getInvoice()).isEqualTo(invoiceBack);
        assertThat(invoiceBack.getInvoiceBillingInfo()).isEqualTo(invoiceBillingInfo);

        invoiceBillingInfo.invoice(null);
        assertThat(invoiceBillingInfo.getInvoice()).isNull();
        assertThat(invoiceBack.getInvoiceBillingInfo()).isNull();
    }
}
