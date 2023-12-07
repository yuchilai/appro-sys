import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './phone-num.reducer';

export const PhoneNumDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const phoneNumEntity = useAppSelector(state => state.phoneNum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="phoneNumDetailsHeading">
          <Translate contentKey="approSysApp.phoneNum.detail.title">PhoneNum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{phoneNumEntity.id}</dd>
          <dt>
            <span id="phoneNum">
              <Translate contentKey="approSysApp.phoneNum.phoneNum">Phone Num</Translate>
            </span>
          </dt>
          <dd>{phoneNumEntity.phoneNum}</dd>
          <dt>
            <Translate contentKey="approSysApp.phoneNum.applicationUser">Application User</Translate>
          </dt>
          <dd>{phoneNumEntity.applicationUser ? phoneNumEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/phone-num" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/phone-num/${phoneNumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PhoneNumDetail;
