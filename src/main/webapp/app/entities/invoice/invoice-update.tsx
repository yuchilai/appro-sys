import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInvoiceBillingInfo } from 'app/shared/model/invoice-billing-info.model';
import { getEntities as getInvoiceBillingInfos } from 'app/entities/invoice-billing-info/invoice-billing-info.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';

export const InvoiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const invoiceBillingInfos = useAppSelector(state => state.invoiceBillingInfo.entities);
  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  const loading = useAppSelector(state => state.invoice.loading);
  const updating = useAppSelector(state => state.invoice.updating);
  const updateSuccess = useAppSelector(state => state.invoice.updateSuccess);
  const invoiceStatusValues = Object.keys(InvoiceStatus);

  const handleClose = () => {
    navigate('/invoice' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInvoiceBillingInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }

    const entity = {
      ...invoiceEntity,
      ...values,
      invoiceBillingInfo: invoiceBillingInfos.find(it => it.id.toString() === values.invoiceBillingInfo.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'CREATED',
          ...invoiceEntity,
          invoiceBillingInfo: invoiceEntity?.invoiceBillingInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.invoice.home.createOrEditLabel" data-cy="InvoiceCreateUpdateHeading">
            <Translate contentKey="approSysApp.invoice.home.createOrEditLabel">Create or edit a Invoice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="invoice-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.invoice.issueDate')}
                id="invoice-issueDate"
                name="issueDate"
                data-cy="issueDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.invoice.amount')}
                id="invoice-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('approSysApp.invoice.status')}
                id="invoice-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {invoiceStatusValues.map(invoiceStatus => (
                  <option value={invoiceStatus} key={invoiceStatus}>
                    {translate('approSysApp.InvoiceStatus.' + invoiceStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('approSysApp.invoice.paymentDueDate')}
                id="invoice-paymentDueDate"
                name="paymentDueDate"
                data-cy="paymentDueDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.invoice.projectDescription')}
                id="invoice-projectDescription"
                name="projectDescription"
                data-cy="projectDescription"
                type="textarea"
              />
              <ValidatedField
                id="invoice-invoiceBillingInfo"
                name="invoiceBillingInfo"
                data-cy="invoiceBillingInfo"
                label={translate('approSysApp.invoice.invoiceBillingInfo')}
                type="select"
              >
                <option value="" key="0" />
                {invoiceBillingInfos
                  ? invoiceBillingInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/invoice" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default InvoiceUpdate;
