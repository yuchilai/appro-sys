import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IApprover } from 'app/shared/model/approver.model';
import { getEntities as getApprovers } from 'app/entities/approver/approver.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { THEME } from 'app/shared/model/enumerations/theme.model';
import { getEntity, updateEntity, createEntity, reset } from './application-user.reducer';

export const ApplicationUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const approvers = useAppSelector(state => state.approver.entities);
  const applicationUserEntity = useAppSelector(state => state.applicationUser.entity);
  const loading = useAppSelector(state => state.applicationUser.loading);
  const updating = useAppSelector(state => state.applicationUser.updating);
  const updateSuccess = useAppSelector(state => state.applicationUser.updateSuccess);
  const tHEMEValues = Object.keys(THEME);

  const handleClose = () => {
    navigate('/application-user' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getApprovers({}));
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
    // if (values.invoiceGap !== undefined && typeof values.invoiceGap !== 'number') {
    //   values.invoiceGap = Number(values.invoiceGap);
    // }
    // Set invoiceGap to undefined if it's an empty string or undefined
    if (values.invoiceGap === '' || values.invoiceGap === undefined) {
      values.invoiceGap = undefined;
    } else if (typeof values.invoiceGap !== 'number') {
      // Convert invoiceGap to number if it's a non-empty string
      values.invoiceGap = Number(values.invoiceGap);
    }

    const entity = {
      ...applicationUserEntity,
      ...values,
      internalUser: users.find(it => it.id.toString() === values.internalUser.toString()),
      approver: approvers.find(it => it.id.toString() === values.approver.toString()),
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
          theme: 'AUTO',
          ...applicationUserEntity,
          internalUser: applicationUserEntity?.internalUser?.id,
          approver: applicationUserEntity?.approver?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.applicationUser.home.createOrEditLabel" data-cy="ApplicationUserCreateUpdateHeading">
            <Translate contentKey="approSysApp.applicationUser.home.createOrEditLabel">Create or edit a ApplicationUser</Translate>
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
                  id="application-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.applicationUser.invoiceGap')}
                id="application-user-invoiceGap"
                name="invoiceGap"
                data-cy="invoiceGap"
                type="text"
                validate={{
                  min: { value: -1, message: translate('entity.validation.min', { min: -1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('approSysApp.applicationUser.theme')}
                id="application-user-theme"
                name="theme"
                data-cy="theme"
                type="select"
              >
                {tHEMEValues.map(tHEME => (
                  <option value={tHEME} key={tHEME}>
                    {translate('approSysApp.THEME.' + tHEME)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('approSysApp.applicationUser.isOnline')}
                id="application-user-isOnline"
                name="isOnline"
                data-cy="isOnline"
                check
                type="checkbox"
              />
              <ValidatedField
                id="application-user-internalUser"
                name="internalUser"
                data-cy="internalUser"
                label={translate('approSysApp.applicationUser.internalUser')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="application-user-approver"
                name="approver"
                data-cy="approver"
                label={translate('approSysApp.applicationUser.approver')}
                type="select"
              >
                <option value="" key="0" />
                {approvers
                  ? approvers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/application-user" replace color="info">
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

export default ApplicationUserUpdate;
