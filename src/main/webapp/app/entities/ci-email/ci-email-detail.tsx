import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ci-email.reducer';

export const CIEmailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cIEmailEntity = useAppSelector(state => state.cIEmail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cIEmailDetailsHeading">
          <Translate contentKey="approSysApp.cIEmail.detail.title">CIEmail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cIEmailEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="approSysApp.cIEmail.email">Email</Translate>
            </span>
          </dt>
          <dd>{cIEmailEntity.email}</dd>
          <dt>
            <Translate contentKey="approSysApp.cIEmail.contactInfo">Contact Info</Translate>
          </dt>
          <dd>{cIEmailEntity.contactInfo ? cIEmailEntity.contactInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ci-email" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ci-email/${cIEmailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CIEmailDetail;
