import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './invoice.reducer';

export const InvoiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceDetailsHeading">
          <Translate contentKey="approSysApp.invoice.detail.title">Invoice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.id}</dd>
          <dt>
            <span id="issueDate">
              <Translate contentKey="approSysApp.invoice.issueDate">Issue Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.issueDate ? <TextFormat value={invoiceEntity.issueDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="approSysApp.invoice.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.amount}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="approSysApp.invoice.status">Status</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.status}</dd>
          <dt>
            <span id="paymentDueDate">
              <Translate contentKey="approSysApp.invoice.paymentDueDate">Payment Due Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.paymentDueDate ? (
              <TextFormat value={invoiceEntity.paymentDueDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="projectDescription">
              <Translate contentKey="approSysApp.invoice.projectDescription">Project Description</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.projectDescription}</dd>
          <dt>
            <Translate contentKey="approSysApp.invoice.invoiceBillingInfo">Invoice Billing Info</Translate>
          </dt>
          <dd>{invoiceEntity.invoiceBillingInfo ? invoiceEntity.invoiceBillingInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceDetail;
