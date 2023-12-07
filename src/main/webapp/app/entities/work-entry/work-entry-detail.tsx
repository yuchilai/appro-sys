import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './work-entry.reducer';

export const WorkEntryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workEntryEntity = useAppSelector(state => state.workEntry.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workEntryDetailsHeading">
          <Translate contentKey="approSysApp.workEntry.detail.title">WorkEntry</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="approSysApp.workEntry.title">Title</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.title}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="approSysApp.workEntry.date">Date</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.date ? <TextFormat value={workEntryEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="approSysApp.workEntry.description">Description</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.description}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="approSysApp.workEntry.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {workEntryEntity.startTime ? <TextFormat value={workEntryEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="approSysApp.workEntry.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.endTime ? <TextFormat value={workEntryEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="hours">
              <Translate contentKey="approSysApp.workEntry.hours">Hours</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.hours}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="approSysApp.workEntry.status">Status</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.status}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="approSysApp.workEntry.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.totalAmount}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="approSysApp.workEntry.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.comment}</dd>
          <dt>
            <span id="attachments">
              <Translate contentKey="approSysApp.workEntry.attachments">Attachments</Translate>
            </span>
          </dt>
          <dd>
            {workEntryEntity.attachments ? (
              <div>
                {workEntryEntity.attachmentsContentType ? (
                  <a onClick={openFile(workEntryEntity.attachmentsContentType, workEntryEntity.attachments)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {workEntryEntity.attachmentsContentType}, {byteSize(workEntryEntity.attachments)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="approSysApp.workEntry.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.fileName}</dd>
          <dt>
            <span id="fileType">
              <Translate contentKey="approSysApp.workEntry.fileType">File Type</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.fileType}</dd>
          <dt>
            <span id="fileSize">
              <Translate contentKey="approSysApp.workEntry.fileSize">File Size</Translate>
            </span>
          </dt>
          <dd>{workEntryEntity.fileSize}</dd>
          <dt>
            <Translate contentKey="approSysApp.workEntry.hourlyRate">Hourly Rate</Translate>
          </dt>
          <dd>{workEntryEntity.hourlyRate ? workEntryEntity.hourlyRate.name : ''}</dd>
          <dt>
            <Translate contentKey="approSysApp.workEntry.projectService">Project Service</Translate>
          </dt>
          <dd>{workEntryEntity.projectService ? workEntryEntity.projectService.name : ''}</dd>
          <dt>
            <Translate contentKey="approSysApp.workEntry.owner">Owner</Translate>
          </dt>
          <dd>{workEntryEntity.owner ? workEntryEntity.owner.id : ''}</dd>
          <dt>
            <Translate contentKey="approSysApp.workEntry.invoice">Invoice</Translate>
          </dt>
          <dd>{workEntryEntity.invoice ? workEntryEntity.invoice.id : ''}</dd>
          <dt>
            <Translate contentKey="approSysApp.workEntry.clientCompany">Client Company</Translate>
          </dt>
          <dd>{workEntryEntity.clientCompany ? workEntryEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/work-entry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/work-entry/${workEntryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkEntryDetail;
