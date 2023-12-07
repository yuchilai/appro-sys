import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approver.reducer';

export const ApproverDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approverEntity = useAppSelector(state => state.approver.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approverDetailsHeading">
          <Translate contentKey="approSysApp.approver.detail.title">Approver</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{approverEntity.id}</dd>
          <dt>
            <span id="signature">
              <Translate contentKey="approSysApp.approver.signature">Signature</Translate>
            </span>
          </dt>
          <dd>
            {approverEntity.signature ? (
              <div>
                {approverEntity.signatureContentType ? (
                  <a onClick={openFile(approverEntity.signatureContentType, approverEntity.signature)}>
                    <img
                      src={`data:${approverEntity.signatureContentType};base64,${approverEntity.signature}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {approverEntity.signatureContentType}, {byteSize(approverEntity.signature)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="assignedDate">
              <Translate contentKey="approSysApp.approver.assignedDate">Assigned Date</Translate>
            </span>
          </dt>
          <dd>
            {approverEntity.assignedDate ? <TextFormat value={approverEntity.assignedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="approSysApp.approver.approvedWorkEntries">Approved Work Entries</Translate>
          </dt>
          <dd>
            {approverEntity.approvedWorkEntries
              ? approverEntity.approvedWorkEntries.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.title}</a>
                    {approverEntity.approvedWorkEntries && i === approverEntity.approvedWorkEntries.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/approver" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approver/${approverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApproverDetail;
