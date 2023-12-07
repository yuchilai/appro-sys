import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cc-email.reducer';

export const CCEmailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cCEmailEntity = useAppSelector(state => state.cCEmail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cCEmailDetailsHeading">
          <Translate contentKey="approSysApp.cCEmail.detail.title">CCEmail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cCEmailEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="approSysApp.cCEmail.email">Email</Translate>
            </span>
          </dt>
          <dd>{cCEmailEntity.email}</dd>
          <dt>
            <Translate contentKey="approSysApp.cCEmail.clientCompany">Client Company</Translate>
          </dt>
          <dd>{cCEmailEntity.clientCompany ? cCEmailEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cc-email" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cc-email/${cCEmailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CCEmailDetail;
