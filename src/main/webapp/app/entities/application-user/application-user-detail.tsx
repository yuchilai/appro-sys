import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './application-user.reducer';

export const ApplicationUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicationUserEntity = useAppSelector(state => state.applicationUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationUserDetailsHeading">
          <Translate contentKey="approSysApp.applicationUser.detail.title">ApplicationUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.id}</dd>
          <dt>
            <span id="theme">
              <Translate contentKey="approSysApp.applicationUser.theme">Theme</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.theme}</dd>
          <dt>
            <span id="isOnline">
              <Translate contentKey="approSysApp.applicationUser.isOnline">Is Online</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.isOnline ? 'true' : 'false'}</dd>
          <dt>
            <span id="invoiceGap">
              <Translate contentKey="approSysApp.applicationUser.invoiceGap">Invoice Gap</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.invoiceGap}</dd>
          <dt>
            <Translate contentKey="approSysApp.applicationUser.internalUser">Internal User</Translate>
          </dt>
          <dd>{applicationUserEntity.internalUser ? applicationUserEntity.internalUser.id : ''}</dd>
          <dt>
            <Translate contentKey="approSysApp.applicationUser.approver">Approver</Translate>
          </dt>
          <dd>{applicationUserEntity.approver ? applicationUserEntity.approver.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/application-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/application-user/${applicationUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationUserDetail;
