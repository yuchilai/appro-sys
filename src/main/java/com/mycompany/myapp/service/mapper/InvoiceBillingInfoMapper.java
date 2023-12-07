package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.InvoiceBillingInfo;
import com.mycompany.myapp.service.dto.InvoiceBillingInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvoiceBillingInfo} and its DTO {@link InvoiceBillingInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceBillingInfoMapper extends EntityMapper<InvoiceBillingInfoDTO, InvoiceBillingInfo> {}
