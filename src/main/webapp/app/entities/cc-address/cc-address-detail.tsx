import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cc-address.reducer';

export const CCAddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cCAddressEntity = useAppSelector(state => state.cCAddress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cCAddressDetailsHeading">
          <Translate contentKey="approSysApp.cCAddress.detail.title">CCAddress</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.id}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="approSysApp.cCAddress.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="approSysApp.cCAddress.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.address2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="approSysApp.cCAddress.city">City</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="approSysApp.cCAddress.state">State</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.state}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="approSysApp.cCAddress.county">County</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.county}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="approSysApp.cCAddress.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.zipCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="approSysApp.cCAddress.country">Country</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.country}</dd>
          <dt>
            <span id="isUsedForInvoice">
              <Translate contentKey="approSysApp.cCAddress.isUsedForInvoice">Is Used For Invoice</Translate>
            </span>
          </dt>
          <dd>{cCAddressEntity.isUsedForInvoice ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="approSysApp.cCAddress.clientCompany">Client Company</Translate>
          </dt>
          <dd>{cCAddressEntity.clientCompany ? cCAddressEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cc-address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cc-address/${cCAddressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CCAddressDetail;
