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
import { ICCAddress } from 'app/shared/model/cc-address.model';
import { getEntity, updateEntity, createEntity, reset } from './cc-address.reducer';

export const CCAddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientCompanies = useAppSelector(state => state.clientCompany.entities);
  const cCAddressEntity = useAppSelector(state => state.cCAddress.entity);
  const loading = useAppSelector(state => state.cCAddress.loading);
  const updating = useAppSelector(state => state.cCAddress.updating);
  const updateSuccess = useAppSelector(state => state.cCAddress.updateSuccess);

  const handleClose = () => {
    navigate('/cc-address' + location.search);
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
      ...cCAddressEntity,
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
          ...cCAddressEntity,
          clientCompany: cCAddressEntity?.clientCompany?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.cCAddress.home.createOrEditLabel" data-cy="CCAddressCreateUpdateHeading">
            <Translate contentKey="approSysApp.cCAddress.home.createOrEditLabel">Create or edit a CCAddress</Translate>
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
                  id="cc-address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.cCAddress.address1')}
                id="cc-address-address1"
                name="address1"
                data-cy="address1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.address2')}
                id="cc-address-address2"
                name="address2"
                data-cy="address2"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.city')}
                id="cc-address-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.state')}
                id="cc-address-state"
                name="state"
                data-cy="state"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.county')}
                id="cc-address-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.zipCode')}
                id="cc-address-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.country')}
                id="cc-address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.cCAddress.isUsedForInvoice')}
                id="cc-address-isUsedForInvoice"
                name="isUsedForInvoice"
                data-cy="isUsedForInvoice"
                check
                type="checkbox"
              />
              <ValidatedField
                id="cc-address-clientCompany"
                name="clientCompany"
                data-cy="clientCompany"
                label={translate('approSysApp.cCAddress.clientCompany')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cc-address" replace color="info">
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

export default CCAddressUpdate;
