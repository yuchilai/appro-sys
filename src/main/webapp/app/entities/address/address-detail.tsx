import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="approSysApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="approSysApp.address.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="approSysApp.address.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.address2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="approSysApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="approSysApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="approSysApp.address.county">County</Translate>
            </span>
          </dt>
          <dd>{addressEntity.county}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="approSysApp.address.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.zipCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="approSysApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <Translate contentKey="approSysApp.address.applicationUser">Application User</Translate>
          </dt>
          <dd>{addressEntity.applicationUser ? addressEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
