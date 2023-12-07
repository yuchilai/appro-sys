import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './email.reducer';

export const EmailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const emailEntity = useAppSelector(state => state.email.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="emailDetailsHeading">
          <Translate contentKey="approSysApp.email.detail.title">Email</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{emailEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="approSysApp.email.email">Email</Translate>
            </span>
          </dt>
          <dd>{emailEntity.email}</dd>
          <dt>
            <Translate contentKey="approSysApp.email.applicationUser">Application User</Translate>
          </dt>
          <dd>{emailEntity.applicationUser ? emailEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/email" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/email/${emailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmailDetail;
