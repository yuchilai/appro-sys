import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hourly-rate.reducer';

export const HourlyRateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hourlyRateEntity = useAppSelector(state => state.hourlyRate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hourlyRateDetailsHeading">
          <Translate contentKey="approSysApp.hourlyRate.detail.title">HourlyRate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hourlyRateEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="approSysApp.hourlyRate.name">Name</Translate>
            </span>
          </dt>
          <dd>{hourlyRateEntity.name}</dd>
          <dt>
            <span id="rate">
              <Translate contentKey="approSysApp.hourlyRate.rate">Rate</Translate>
            </span>
          </dt>
          <dd>{hourlyRateEntity.rate}</dd>
          <dt>
            <span id="isDefault">
              <Translate contentKey="approSysApp.hourlyRate.isDefault">Is Default</Translate>
            </span>
          </dt>
          <dd>{hourlyRateEntity.isDefault ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="approSysApp.hourlyRate.clientCompany">Client Company</Translate>
          </dt>
          <dd>{hourlyRateEntity.clientCompany ? hourlyRateEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hourly-rate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hourly-rate/${hourlyRateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HourlyRateDetail;
