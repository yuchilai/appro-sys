package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Invoice;
import com.mycompany.myapp.domain.InvoiceBillingInfo;
import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
import com.mycompany.myapp.service.dto.InvoiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
    @Mapping(target = "invoiceBillingInfo", source = "invoiceBillingInfo", qualifiedByName = "invoiceBillingInfoId")
    InvoiceDTO toDto(Invoice s);

    @Named("invoiceBillingInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InvoiceBillingInfoDTO toDtoInvoiceBillingInfoId(InvoiceBillingInfo invoiceBillingInfo);
}
