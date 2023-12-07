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
import { ICIPhoneNum } from 'app/shared/model/ci-phone-num.model';
import { getEntity, updateEntity, createEntity, reset } from './ci-phone-num.reducer';

export const CIPhoneNumUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactInfos = useAppSelector(state => state.contactInfo.entities);
  const cIPhoneNumEntity = useAppSelector(state => state.cIPhoneNum.entity);
  const loading = useAppSelector(state => state.cIPhoneNum.loading);
  const updating = useAppSelector(state => state.cIPhoneNum.updating);
  const updateSuccess = useAppSelector(state => state.cIPhoneNum.updateSuccess);

  const handleClose = () => {
    navigate('/ci-phone-num' + location.search);
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
      ...cIPhoneNumEntity,
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
          ...cIPhoneNumEntity,
          contactInfo: cIPhoneNumEntity?.contactInfo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.cIPhoneNum.home.createOrEditLabel" data-cy="CIPhoneNumCreateUpdateHeading">
            <Translate contentKey="approSysApp.cIPhoneNum.home.createOrEditLabel">Create or edit a CIPhoneNum</Translate>
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
                  id="ci-phone-num-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.cIPhoneNum.phoneNum')}
                id="ci-phone-num-phoneNum"
                name="phoneNum"
                data-cy="phoneNum"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="ci-phone-num-contactInfo"
                name="contactInfo"
                data-cy="contactInfo"
                label={translate('approSysApp.cIPhoneNum.contactInfo')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ci-phone-num" replace color="info">
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

export default CIPhoneNumUpdate;
