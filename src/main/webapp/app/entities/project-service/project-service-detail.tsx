import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './project-service.reducer';

export const ProjectServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectServiceEntity = useAppSelector(state => state.projectService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectServiceDetailsHeading">
          <Translate contentKey="approSysApp.projectService.detail.title">ProjectService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="approSysApp.projectService.name">Name</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.name}</dd>
          <dt>
            <span id="fee">
              <Translate contentKey="approSysApp.projectService.fee">Fee</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.fee}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="approSysApp.projectService.description">Description</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.description}</dd>
          <dt>
            <span id="dayLength">
              <Translate contentKey="approSysApp.projectService.dayLength">Day Length</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.dayLength}</dd>
          <dt>
            <span id="extra">
              <Translate contentKey="approSysApp.projectService.extra">Extra</Translate>
            </span>
          </dt>
          <dd>{projectServiceEntity.extra}</dd>
          <dt>
            <Translate contentKey="approSysApp.projectService.clientCompany">Client Company</Translate>
          </dt>
          <dd>{projectServiceEntity.clientCompany ? projectServiceEntity.clientCompany.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-service/${projectServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectServiceDetail;
