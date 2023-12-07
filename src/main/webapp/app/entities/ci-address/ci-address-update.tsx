import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactInfo } from 'app/shared/model/contact-info.model';
import { getEntities as getContactInfos } from 'app/entities/contact-info/contact-info.reducer';
import { ICIAddress } from 'app/shared/model/ci-address.model';
import { getEntity, updateEntity, createEntity, reset } from './ci-address.reducer';

export const CIAddressUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactInfos = useAppSelector(state => state.contactInfo.entities);
  const cIAddressEntity = useAppSelector(state => state.cIAddress.entity);
  const loading = useAppSelector(state => state.cIAddress.loading);
  const updating = useAppSelector(state => state.cIAddress.updating);
  const updateSuccess = useAppSelector(state => state.cIAddress.updateSuccess);

  const handleClose = () => {
    navigate('/ci-address' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContactInfos({}));
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
      ...cIAddressEntity,
      ...values,
      contactInfo: contactInfos.find(it => it.id.toString() === values.contactInfo.toString()),
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
          ...cIAddressEntity,
          contactInfo: cIAddressEntity?.contactInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.cIAddress.home.createOrEditLabel" data-cy="CIAddressCreateUpdateHeading">
            <Translate contentKey="approSysApp.cIAddress.home.createOrEditLabel">Create or edit a CIAddress</Translate>
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
                  id="ci-address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.cIAddress.address1')}
                id="ci-address-address1"
                name="address1"
                data-cy="address1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.address2')}
                id="ci-address-address2"
                name="address2"
                data-cy="address2"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.city')}
                id="ci-address-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.state')}
                id="ci-address-state"
                name="state"
                data-cy="state"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.county')}
                id="ci-address-county"
                name="county"
                data-cy="county"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.zipCode')}
                id="ci-address-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.cIAddress.country')}
                id="ci-address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                id="ci-address-contactInfo"
                name="contactInfo"
                data-cy="contactInfo"
                label={translate('approSysApp.cIAddress.contactInfo')}
                type="select"
              >
                <option value="" key="0" />
                {contactInfos
                  ? contactInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ci-address" replace color="info">
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

export default CIAddressUpdate;
