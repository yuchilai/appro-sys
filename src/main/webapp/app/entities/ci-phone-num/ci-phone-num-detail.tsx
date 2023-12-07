import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ci-phone-num.reducer';

export const CIPhoneNumDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cIPhoneNumEntity = useAppSelector(state => state.cIPhoneNum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cIPhoneNumDetailsHeading">
          <Translate contentKey="approSysApp.cIPhoneNum.detail.title">CIPhoneNum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cIPhoneNumEntity.id}</dd>
          <dt>
            <span id="phoneNum">
              <Translate contentKey="approSysApp.cIPhoneNum.phoneNum">Phone Num</Translate>
            </span>
          </dt>
          <dd>{cIPhoneNumEntity.phoneNum}</dd>
          <dt>
            <Translate contentKey="approSysApp.cIPhoneNum.contactInfo">Contact Info</Translate>
          </dt>
          <dd>{cIPhoneNumEntity.contactInfo ? cIPhoneNumEntity.contactInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ci-phone-num" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ci-phone-num/${cIPhoneNumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CIPhoneNumDetail;
