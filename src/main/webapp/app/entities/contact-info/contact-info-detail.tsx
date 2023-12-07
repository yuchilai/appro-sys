import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact-info.reducer';

export const ContactInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactInfoEntity = useAppSelector(state => state.contactInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactInfoDetailsHeading">
          <Translate contentKey="approSysApp.contactInfo.detail.title">ContactInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.id}</dd>
          <dt>
            <span id="positsion">
              <Translate contentKey="approSysApp.contactInfo.positsion">Positsion</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.positsion}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="approSysApp.contactInfo.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="approSysApp.contactInfo.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{contactInfoEntity.lastName}</dd>
          <dt>
            <Translate contentKey="approSysApp.contactInfo.clientCompany">Client Company</Translate>
          </dt>
          <dd>{contactInfoEntity.clientCompany ? contactInfoEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact-info/${contactInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactInfoDetail;
