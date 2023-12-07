import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApprover } from 'app/shared/model/approver.model';
import { getEntities as getApprovers } from 'app/entities/approver/approver.reducer';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { getEntities as getWorkEntries } from 'app/entities/work-entry/work-entry.reducer';
import { IApproval } from 'app/shared/model/approval.model';
import { getEntity, updateEntity, createEntity, reset } from './approval.reducer';

export const ApprovalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const approvers = useAppSelector(state => state.approver.entities);
  const workEntries = useAppSelector(state => state.workEntry.entities);
  const approvalEntity = useAppSelector(state => state.approval.entity);
  const loading = useAppSelector(state => state.approval.loading);
  const updating = useAppSelector(state => state.approval.updating);
  const updateSuccess = useAppSelector(state => state.approval.updateSuccess);

  const handleClose = () => {
    navigate('/approval' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getApprovers({}));
    dispatch(getWorkEntries({}));
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
    values.approvalDateTime = convertDateTimeToServer(values.approvalDateTime);

    const entity = {
      ...approvalEntity,
      ...values,
      approver: approvers.find(it => it.id.toString() === values.approver.toString()),
      workEntry: workEntries.find(it => it.id.toString() === values.workEntry.toString()),
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
          approvalDateTime: displayDefaultDateTime(),
        }
      : {
          ...approvalEntity,
          approvalDateTime: convertDateTimeFromServer(approvalEntity.approvalDateTime),
          approver: approvalEntity?.approver?.id,
          workEntry: approvalEntity?.workEntry?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.approval.home.createOrEditLabel" data-cy="ApprovalCreateUpdateHeading">
            <Translate contentKey="approSysApp.approval.home.createOrEditLabel">Create or edit a Approval</Translate>
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
                  id="approval-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.approval.approved')}
                id="approval-approved"
                name="approved"
                data-cy="approved"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('approSysApp.approval.approvalDateTime')}
                id="approval-approvalDateTime"
                name="approvalDateTime"
                data-cy="approvalDateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.approval.comments')}
                id="approval-comments"
                name="comments"
                data-cy="comments"
                type="textarea"
              />
              <ValidatedField
                id="approval-approver"
                name="approver"
                data-cy="approver"
                label={translate('approSysApp.approval.approver')}
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
              <ValidatedField
                id="approval-workEntry"
                name="workEntry"
                data-cy="workEntry"
                label={translate('approSysApp.approval.workEntry')}
                type="select"
              >
                <option value="" key="0" />
                {workEntries
                  ? workEntries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approval" replace color="info">
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

export default ApprovalUpdate;
