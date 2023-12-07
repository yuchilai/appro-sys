package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.InvoiceBillingInfoTestSamples.*;
import static com.mycompany.myapp.domain.InvoiceTestSamples.*;
import static com.mycompany.myapp.domain.WorkEntryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InvoiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoice.class);
        Invoice invoice1 = getInvoiceSample1();
        Invoice invoice2 = new Invoice();
        assertThat(invoice1).isNotEqualTo(invoice2);

        invoice2.setId(invoice1.getId());
        assertThat(invoice1).isEqualTo(invoice2);

        invoice2 = getInvoiceSample2();
        assertThat(invoice1).isNotEqualTo(invoice2);
    }

    @Test
    void invoiceBillingInfoTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        InvoiceBillingInfo invoiceBillingInfoBack = getInvoiceBillingInfoRandomSampleGenerator();

        invoice.setInvoiceBillingInfo(invoiceBillingInfoBack);
        assertThat(invoice.getInvoiceBillingInfo()).isEqualTo(invoiceBillingInfoBack);

        invoice.invoiceBillingInfo(null);
        assertThat(invoice.getInvoiceBillingInfo()).isNull();
    }

    @Test
    void workEntryTest() throws Exception {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        WorkEntry workEntryBack = getWorkEntryRandomSampleGenerator();

        invoice.addWorkEntry(workEntryBack);
        assertThat(invoice.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getInvoice()).isEqualTo(invoice);

        invoice.removeWorkEntry(workEntryBack);
        assertThat(invoice.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getInvoice()).isNull();

        invoice.workEntries(new HashSet<>(Set.of(workEntryBack)));
        assertThat(invoice.getWorkEntries()).containsOnly(workEntryBack);
        assertThat(workEntryBack.getInvoice()).isEqualTo(invoice);

        invoice.setWorkEntries(new HashSet<>());
        assertThat(invoice.getWorkEntries()).doesNotContain(workEntryBack);
        assertThat(workEntryBack.getInvoice()).isNull();
    }
}
