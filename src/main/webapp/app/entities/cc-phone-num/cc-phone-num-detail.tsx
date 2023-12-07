import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cc-phone-num.reducer';

export const CCPhoneNumDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cCPhoneNumEntity = useAppSelector(state => state.cCPhoneNum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cCPhoneNumDetailsHeading">
          <Translate contentKey="approSysApp.cCPhoneNum.detail.title">CCPhoneNum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cCPhoneNumEntity.id}</dd>
          <dt>
            <span id="phoneNum">
              <Translate contentKey="approSysApp.cCPhoneNum.phoneNum">Phone Num</Translate>
            </span>
          </dt>
          <dd>{cCPhoneNumEntity.phoneNum}</dd>
          <dt>
            <Translate contentKey="approSysApp.cCPhoneNum.clientCompany">Client Company</Translate>
          </dt>
          <dd>{cCPhoneNumEntity.clientCompany ? cCPhoneNumEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cc-phone-num" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cc-phone-num/${cCPhoneNumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CCPhoneNumDetail;
