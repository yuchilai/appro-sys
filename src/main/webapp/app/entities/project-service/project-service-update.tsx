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
import { IProjectService } from 'app/shared/model/project-service.model';
import { getEntity, updateEntity, createEntity, reset } from './project-service.reducer';

export const ProjectServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientCompanies = useAppSelector(state => state.clientCompany.entities);
  const projectServiceEntity = useAppSelector(state => state.projectService.entity);
  const loading = useAppSelector(state => state.projectService.loading);
  const updating = useAppSelector(state => state.projectService.updating);
  const updateSuccess = useAppSelector(state => state.projectService.updateSuccess);

  const handleClose = () => {
    navigate('/project-service' + location.search);
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
    if (values.fee !== undefined && typeof values.fee !== 'number') {
      values.fee = Number(values.fee);
    }
    if (values.dayLength !== undefined && typeof values.dayLength !== 'number') {
      values.dayLength = Number(values.dayLength);
    }

    const entity = {
      ...projectServiceEntity,
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
          ...projectServiceEntity,
          clientCompany: projectServiceEntity?.clientCompany?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.projectService.home.createOrEditLabel" data-cy="ProjectServiceCreateUpdateHeading">
            <Translate contentKey="approSysApp.projectService.home.createOrEditLabel">Create or edit a ProjectService</Translate>
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
                  id="project-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.projectService.name')}
                id="project-service-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.projectService.fee')}
                id="project-service-fee"
                name="fee"
                data-cy="fee"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('approSysApp.projectService.description')}
                id="project-service-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('approSysApp.projectService.dayLength')}
                id="project-service-dayLength"
                name="dayLength"
                data-cy="dayLength"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.projectService.extra')}
                id="project-service-extra"
                name="extra"
                data-cy="extra"
                type="textarea"
              />
              <ValidatedField
                id="project-service-clientCompany"
                name="clientCompany"
                data-cy="clientCompany"
                label={translate('approSysApp.projectService.clientCompany')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project-service" replace color="info">
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

export default ProjectServiceUpdate;
