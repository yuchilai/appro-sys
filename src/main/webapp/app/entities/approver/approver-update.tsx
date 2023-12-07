import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { getEntities as getWorkEntries } from 'app/entities/work-entry/work-entry.reducer';
import { IClientCompany } from 'app/shared/model/client-company.model';
import { getEntities as getClientCompanies } from 'app/entities/client-company/client-company.reducer';
import { IApprover } from 'app/shared/model/approver.model';
import { getEntity, updateEntity, createEntity, reset } from './approver.reducer';

export const ApproverUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workEntries = useAppSelector(state => state.workEntry.entities);
  const clientCompanies = useAppSelector(state => state.clientCompany.entities);
  const approverEntity = useAppSelector(state => state.approver.entity);
  const loading = useAppSelector(state => state.approver.loading);
  const updating = useAppSelector(state => state.approver.updating);
  const updateSuccess = useAppSelector(state => state.approver.updateSuccess);

  const handleClose = () => {
    navigate('/approver' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWorkEntries({}));
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
    values.assignedDate = convertDateTimeToServer(values.assignedDate);

    const entity = {
      ...approverEntity,
      ...values,
      approvedWorkEntries: mapIdList(values.approvedWorkEntries),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          assignedDate: displayDefaultDateTime(),
        }
      : {
          ...approverEntity,
          assignedDate: convertDateTimeFromServer(approverEntity.assignedDate),
          approvedWorkEntries: approverEntity?.approvedWorkEntries?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.approver.home.createOrEditLabel" data-cy="ApproverCreateUpdateHeading">
            <Translate contentKey="approSysApp.approver.home.createOrEditLabel">Create or edit a Approver</Translate>
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
                  id="approver-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedBlobField
                label={translate('approSysApp.approver.signature')}
                id="approver-signature"
                name="signature"
                data-cy="signature"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('approSysApp.approver.assignedDate')}
                id="approver-assignedDate"
                name="assignedDate"
                data-cy="assignedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.approver.approvedWorkEntries')}
                id="approver-approvedWorkEntries"
                data-cy="approvedWorkEntries"
                type="select"
                multiple
                name="approvedWorkEntries"
              >
                <option value="" key="0" />
                {workEntries
                  ? workEntries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approver" replace color="info">
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

export default ApproverUpdate;
