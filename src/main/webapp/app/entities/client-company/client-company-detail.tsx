import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client-company.reducer';

export const ClientCompanyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientCompanyEntity = useAppSelector(state => state.clientCompany.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientCompanyDetailsHeading">
          <Translate contentKey="approSysApp.clientCompany.detail.title">ClientCompany</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clientCompanyEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="approSysApp.clientCompany.name">Name</Translate>
            </span>
          </dt>
          <dd>{clientCompanyEntity.name}</dd>
          <dt>
            <span id="invoicePrefix">
              <Translate contentKey="approSysApp.clientCompany.invoicePrefix">Invoice Prefix</Translate>
            </span>
          </dt>
          <dd>{clientCompanyEntity.invoicePrefix}</dd>
          <dt>
            <Translate contentKey="approSysApp.clientCompany.approver">Approver</Translate>
          </dt>
          <dd>
            {clientCompanyEntity.approvers
              ? clientCompanyEntity.approvers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientCompanyEntity.approvers && i === clientCompanyEntity.approvers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/client-company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-company/${clientCompanyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientCompanyDetail;
