import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ci-address.reducer';

export const CIAddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cIAddressEntity = useAppSelector(state => state.cIAddress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cIAddressDetailsHeading">
          <Translate contentKey="approSysApp.cIAddress.detail.title">CIAddress</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.id}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="approSysApp.cIAddress.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="approSysApp.cIAddress.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.address2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="approSysApp.cIAddress.city">City</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="approSysApp.cIAddress.state">State</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.state}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="approSysApp.cIAddress.county">County</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.county}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="approSysApp.cIAddress.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.zipCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="approSysApp.cIAddress.country">Country</Translate>
            </span>
          </dt>
          <dd>{cIAddressEntity.country}</dd>
          <dt>
            <Translate contentKey="approSysApp.cIAddress.contactInfo">Contact Info</Translate>
          </dt>
          <dd>{cIAddressEntity.contactInfo ? cIAddressEntity.contactInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ci-address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ci-address/${cIAddressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CIAddressDetail;
