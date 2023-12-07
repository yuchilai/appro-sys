import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClientCompany } from 'app/shared/model/client-company.model';
import { getEntities as getClientCompanies } from 'app/entities/client-company/client-company.reducer';
import { IAccountsPayable } from 'app/shared/model/accounts-payable.model';
import { getEntity, updateEntity, createEntity, reset } from './accounts-payable.reducer';

export const AccountsPayableUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientCompanies = useAppSelector(state => state.clientCompany.entities);
  const accountsPayableEntity = useAppSelector(state => state.accountsPayable.entity);
  const loading = useAppSelector(state => state.accountsPayable.loading);
  const updating = useAppSelector(state => state.accountsPayable.updating);
  const updateSuccess = useAppSelector(state => state.accountsPayable.updateSuccess);

  const handleClose = () => {
    navigate('/accounts-payable' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClientCompanies({}));
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

    const entity = {
      ...accountsPayableEntity,
      ...values,
      clientCompany: clientCompanies.find(it => it.id.toString() === values.clientCompany.toString()),
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
          ...accountsPayableEntity,
          clientCompany: accountsPayableEntity?.clientCompany?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.accountsPayable.home.createOrEditLabel" data-cy="AccountsPayableCreateUpdateHeading">
            <Translate contentKey="approSysApp.accountsPayable.home.createOrEditLabel">Create or edit a AccountsPayable</Translate>
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
                  id="accounts-payable-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.accountsPayable.deptName')}
                id="accounts-payable-deptName"
                name="deptName"
                data-cy="deptName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.accountsPayable.repLastName')}
                id="accounts-payable-repLastName"
                name="repLastName"
                data-cy="repLastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.accountsPayable.repFirstName')}
                id="accounts-payable-repFirstName"
                name="repFirstName"
                data-cy="repFirstName"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.accountsPayable.repEmail')}
                id="accounts-payable-repEmail"
                name="repEmail"
                data-cy="repEmail"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.accountsPayable.repPhoneNum')}
                id="accounts-payable-repPhoneNum"
                name="repPhoneNum"
                data-cy="repPhoneNum"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.accountsPayable.isUsedForInvoice')}
                id="accounts-payable-isUsedForInvoice"
                name="isUsedForInvoice"
                data-cy="isUsedForInvoice"
                check
                type="checkbox"
              />
              <ValidatedField
                id="accounts-payable-clientCompany"
                name="clientCompany"
                data-cy="clientCompany"
                label={translate('approSysApp.accountsPayable.clientCompany')}
                type="select"
              >
                <option value="" key="0" />
                {clientCompanies
                  ? clientCompanies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accounts-payable" replace color="info">
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

export default AccountsPayableUpdate;
