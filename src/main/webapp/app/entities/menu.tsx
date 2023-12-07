import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/application-user">
        <Translate contentKey="global.menu.entities.applicationUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/email">
        <Translate contentKey="global.menu.entities.email" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/phone-num">
        <Translate contentKey="global.menu.entities.phoneNum" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client-company">
        <Translate contentKey="global.menu.entities.clientCompany" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cc-email">
        <Translate contentKey="global.menu.entities.ccEmail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cc-phone-num">
        <Translate contentKey="global.menu.entities.ccPhoneNum" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cc-address">
        <Translate contentKey="global.menu.entities.ccAddress" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/approver">
        <Translate contentKey="global.menu.entities.approver" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/work-entry">
        <Translate contentKey="global.menu.entities.workEntry" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tag">
        <Translate contentKey="global.menu.entities.tag" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/approval">
        <Translate contentKey="global.menu.entities.approval" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contact-info">
        <Translate contentKey="global.menu.entities.contactInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ci-email">
        <Translate contentKey="global.menu.entities.ciEmail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ci-phone-num">
        <Translate contentKey="global.menu.entities.ciPhoneNum" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ci-address">
        <Translate contentKey="global.menu.entities.ciAddress" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/invoice">
        <Translate contentKey="global.menu.entities.invoice" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/invoice-billing-info">
        <Translate contentKey="global.menu.entities.invoiceBillingInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hourly-rate">
        <Translate contentKey="global.menu.entities.hourlyRate" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/project-service">
        <Translate contentKey="global.menu.entities.projectService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/accounts-payable">
        <Translate contentKey="global.menu.entities.accountsPayable" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
