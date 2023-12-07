import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './accounts-payable.reducer';

export const AccountsPayableDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accountsPayableEntity = useAppSelector(state => state.accountsPayable.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountsPayableDetailsHeading">
          <Translate contentKey="approSysApp.accountsPayable.detail.title">AccountsPayable</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.id}</dd>
          <dt>
            <span id="deptName">
              <Translate contentKey="approSysApp.accountsPayable.deptName">Dept Name</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.deptName}</dd>
          <dt>
            <span id="repLastName">
              <Translate contentKey="approSysApp.accountsPayable.repLastName">Rep Last Name</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.repLastName}</dd>
          <dt>
            <span id="repFirstName">
              <Translate contentKey="approSysApp.accountsPayable.repFirstName">Rep First Name</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.repFirstName}</dd>
          <dt>
            <span id="repEmail">
              <Translate contentKey="approSysApp.accountsPayable.repEmail">Rep Email</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.repEmail}</dd>
          <dt>
            <span id="repPhoneNum">
              <Translate contentKey="approSysApp.accountsPayable.repPhoneNum">Rep Phone Num</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.repPhoneNum}</dd>
          <dt>
            <span id="isUsedForInvoice">
              <Translate contentKey="approSysApp.accountsPayable.isUsedForInvoice">Is Used For Invoice</Translate>
            </span>
          </dt>
          <dd>{accountsPayableEntity.isUsedForInvoice ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="approSysApp.accountsPayable.clientCompany">Client Company</Translate>
          </dt>
          <dd>{accountsPayableEntity.clientCompany ? accountsPayableEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/accounts-payable" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accounts-payable/${accountsPayableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountsPayableDetail;
