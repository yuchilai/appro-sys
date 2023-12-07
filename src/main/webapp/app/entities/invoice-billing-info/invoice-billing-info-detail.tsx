import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './invoice-billing-info.reducer';

export const InvoiceBillingInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const invoiceBillingInfoEntity = useAppSelector(state => state.invoiceBillingInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceBillingInfoDetailsHeading">
          <Translate contentKey="approSysApp.invoiceBillingInfo.detail.title">InvoiceBillingInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="approSysApp.invoiceBillingInfo.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.firstName}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="approSysApp.invoiceBillingInfo.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.lastname}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="approSysApp.invoiceBillingInfo.email">Email</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.email}</dd>
          <dt>
            <span id="phoneNum">
              <Translate contentKey="approSysApp.invoiceBillingInfo.phoneNum">Phone Num</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.phoneNum}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="approSysApp.invoiceBillingInfo.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="approSysApp.invoiceBillingInfo.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.address2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="approSysApp.invoiceBillingInfo.city">City</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="approSysApp.invoiceBillingInfo.state">State</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.state}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="approSysApp.invoiceBillingInfo.county">County</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.county}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="approSysApp.invoiceBillingInfo.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.zipCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="approSysApp.invoiceBillingInfo.country">Country</Translate>
            </span>
          </dt>
          <dd>{invoiceBillingInfoEntity.country}</dd>
        </dl>
        <Button tag={Link} to="/invoice-billing-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice-billing-info/${invoiceBillingInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceBillingInfoDetail;
