import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHourlyRate } from 'app/shared/model/hourly-rate.model';
import { getEntities as getHourlyRates } from 'app/entities/hourly-rate/hourly-rate.reducer';
import { IProjectService } from 'app/shared/model/project-service.model';
import { getEntities as getProjectServices } from 'app/entities/project-service/project-service.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { getEntities as getInvoices } from 'app/entities/invoice/invoice.reducer';
import { IClientCompany } from 'app/shared/model/client-company.model';
import { getEntities as getClientCompanies } from 'app/entities/client-company/client-company.reducer';
import { IApprover } from 'app/shared/model/approver.model';
import { getEntities as getApprovers } from 'app/entities/approver/approver.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { WorkStatus } from 'app/shared/model/enumerations/work-status.model';
import { getEntity, updateEntity, createEntity, reset } from './work-entry.reducer';

export const WorkEntryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hourlyRates = useAppSelector(state => state.hourlyRate.entities);
  const projectServices = useAppSelector(state => state.projectService.entities);
  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const invoices = useAppSelector(state => state.invoice.entities);
  const clientCompanies = useAppSelector(state => state.clientCompany.entities);
  const approvers = useAppSelector(state => state.approver.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const workEntryEntity = useAppSelector(state => state.workEntry.entity);
  const loading = useAppSelector(state => state.workEntry.loading);
  const updating = useAppSelector(state => state.workEntry.updating);
  const updateSuccess = useAppSelector(state => state.workEntry.updateSuccess);
  const workStatusValues = Object.keys(WorkStatus);

  const handleClose = () => {
    navigate('/work-entry' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHourlyRates({}));
    dispatch(getProjectServices({}));
    dispatch(getApplicationUsers({}));
    dispatch(getInvoices({}));
    dispatch(getClientCompanies({}));
    dispatch(getApprovers({}));
    dispatch(getTags({}));
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
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);
    if (values.hours !== undefined && typeof values.hours !== 'number') {
      values.hours = Number(values.hours);
    }
    if (values.totalAmount !== undefined && typeof values.totalAmount !== 'number') {
      values.totalAmount = Number(values.totalAmount);
    }
    if (values.fileSize !== undefined && typeof values.fileSize !== 'number') {
      values.fileSize = Number(values.fileSize);
    }
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);
    if (values.approvalKeyRegeneratedDays !== undefined && typeof values.approvalKeyRegeneratedDays !== 'number') {
      values.approvalKeyRegeneratedDays = Number(values.approvalKeyRegeneratedDays);
    }
    values.approvalKeyCreatedDate = convertDateTimeToServer(values.approvalKeyCreatedDate);

    const entity = {
      ...workEntryEntity,
      ...values,
      hourlyRate: hourlyRates.find(it => it.id.toString() === values.hourlyRate.toString()),
      projectService: projectServices.find(it => it.id.toString() === values.projectService.toString()),
      owner: applicationUsers.find(it => it.id.toString() === values.owner.toString()),
      invoice: invoices.find(it => it.id.toString() === values.invoice.toString()),
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
      ? {
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
          approvalKeyCreatedDate: displayDefaultDateTime(),
        }
      : {
          status: 'SUBMITTED',
          ...workEntryEntity,
          startTime: convertDateTimeFromServer(workEntryEntity.startTime),
          endTime: convertDateTimeFromServer(workEntryEntity.endTime),
          createdDate: convertDateTimeFromServer(workEntryEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(workEntryEntity.lastModifiedDate),
          approvalKeyCreatedDate: convertDateTimeFromServer(workEntryEntity.approvalKeyCreatedDate),
          hourlyRate: workEntryEntity?.hourlyRate?.id,
          projectService: workEntryEntity?.projectService?.id,
          owner: workEntryEntity?.owner?.id,
          invoice: workEntryEntity?.invoice?.id,
          clientCompany: workEntryEntity?.clientCompany?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="approSysApp.workEntry.home.createOrEditLabel" data-cy="WorkEntryCreateUpdateHeading">
            <Translate contentKey="approSysApp.workEntry.home.createOrEditLabel">Create or edit a WorkEntry</Translate>
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
                  id="work-entry-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('approSysApp.workEntry.title')}
                id="work-entry-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.date')}
                id="work-entry-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.description')}
                id="work-entry-description"
                name="description"
                data-cy="description"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.startTime')}
                id="work-entry-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.endTime')}
                id="work-entry-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.hours')}
                id="work-entry-hours"
                name="hours"
                data-cy="hours"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.status')}
                id="work-entry-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {workStatusValues.map(workStatus => (
                  <option value={workStatus} key={workStatus}>
                    {translate('approSysApp.WorkStatus.' + workStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('approSysApp.workEntry.totalAmount')}
                id="work-entry-totalAmount"
                name="totalAmount"
                data-cy="totalAmount"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.comment')}
                id="work-entry-comment"
                name="comment"
                data-cy="comment"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('approSysApp.workEntry.attachments')}
                id="work-entry-attachments"
                name="attachments"
                data-cy="attachments"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.fileName')}
                id="work-entry-fileName"
                name="fileName"
                data-cy="fileName"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.fileType')}
                id="work-entry-fileType"
                name="fileType"
                data-cy="fileType"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.fileSize')}
                id="work-entry-fileSize"
                name="fileSize"
                data-cy="fileSize"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.createdDate')}
                id="work-entry-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.lastModifiedDate')}
                id="work-entry-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.approvalKeyRegeneratedDays')}
                id="work-entry-approvalKeyRegeneratedDays"
                name="approvalKeyRegeneratedDays"
                data-cy="approvalKeyRegeneratedDays"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.approvalKeyCreatedDate')}
                id="work-entry-approvalKeyCreatedDate"
                name="approvalKeyCreatedDate"
                data-cy="approvalKeyCreatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.approvalKey')}
                id="work-entry-approvalKey"
                name="approvalKey"
                data-cy="approvalKey"
                type="text"
              />
              <ValidatedField
                label={translate('approSysApp.workEntry.batchApprovalKey')}
                id="work-entry-batchApprovalKey"
                name="batchApprovalKey"
                data-cy="batchApprovalKey"
                type="text"
              />
              <ValidatedField
                id="work-entry-hourlyRate"
                name="hourlyRate"
                data-cy="hourlyRate"
                label={translate('approSysApp.workEntry.hourlyRate')}
                type="select"
              >
                <option value="" key="0" />
                {hourlyRates
                  ? hourlyRates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="work-entry-projectService"
                name="projectService"
                data-cy="projectService"
                label={translate('approSysApp.workEntry.projectService')}
                type="select"
              >
                <option value="" key="0" />
                {projectServices
                  ? projectServices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="work-entry-owner"
                name="owner"
                data-cy="owner"
                label={translate('approSysApp.workEntry.owner')}
                type="select"
              >
                <option value="" key="0" />
                {applicationUsers
                  ? applicationUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="work-entry-invoice"
                name="invoice"
                data-cy="invoice"
                label={translate('approSysApp.workEntry.invoice')}
                type="select"
              >
                <option value="" key="0" />
                {invoices
                  ? invoices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="work-entry-clientCompany"
                name="clientCompany"
                data-cy="clientCompany"
                label={translate('approSysApp.workEntry.clientCompany')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/work-entry" replace color="info">
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

export default WorkEntryUpdate;
