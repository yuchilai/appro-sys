import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './work-entry.reducer';

export const WorkEntry = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const workEntryList = useAppSelector(state => state.workEntry.entities);
  const loading = useAppSelector(state => state.workEntry.loading);
  const totalItems = useAppSelector(state => state.workEntry.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="work-entry-heading" data-cy="WorkEntryHeading">
        <Translate contentKey="approSysApp.workEntry.home.title">Work Entries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="approSysApp.workEntry.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/work-entry/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="approSysApp.workEntry.home.createLabel">Create new Work Entry</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {workEntryList && workEntryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="approSysApp.workEntry.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="approSysApp.workEntry.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="approSysApp.workEntry.date">Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="approSysApp.workEntry.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('startTime')}>
                  <Translate contentKey="approSysApp.workEntry.startTime">Start Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                </th>
                <th className="hand" onClick={sort('endTime')}>
                  <Translate contentKey="approSysApp.workEntry.endTime">End Time</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endTime')} />
                </th>
                <th className="hand" onClick={sort('hours')}>
                  <Translate contentKey="approSysApp.workEntry.hours">Hours</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('hours')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="approSysApp.workEntry.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('totalAmount')}>
                  <Translate contentKey="approSysApp.workEntry.totalAmount">Total Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalAmount')} />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  <Translate contentKey="approSysApp.workEntry.comment">Comment</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('comment')} />
                </th>
                <th className="hand" onClick={sort('attachments')}>
                  <Translate contentKey="approSysApp.workEntry.attachments">Attachments</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('attachments')} />
                </th>
                <th className="hand" onClick={sort('fileName')}>
                  <Translate contentKey="approSysApp.workEntry.fileName">File Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fileName')} />
                </th>
                <th className="hand" onClick={sort('fileType')}>
                  <Translate contentKey="approSysApp.workEntry.fileType">File Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fileType')} />
                </th>
                <th className="hand" onClick={sort('fileSize')}>
                  <Translate contentKey="approSysApp.workEntry.fileSize">File Size</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fileSize')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="approSysApp.workEntry.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th className="hand" onClick={sort('lastModifiedDate')}>
                  <Translate contentKey="approSysApp.workEntry.lastModifiedDate">Last Modified Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastModifiedDate')} />
                </th>
                <th className="hand" onClick={sort('approvalKeyRegeneratedDays')}>
                  <Translate contentKey="approSysApp.workEntry.approvalKeyRegeneratedDays">Approval Key Regenerated Days</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approvalKeyRegeneratedDays')} />
                </th>
                <th className="hand" onClick={sort('approvalKeyCreatedDate')}>
                  <Translate contentKey="approSysApp.workEntry.approvalKeyCreatedDate">Approval Key Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approvalKeyCreatedDate')} />
                </th>
                <th className="hand" onClick={sort('approvalKey')}>
                  <Translate contentKey="approSysApp.workEntry.approvalKey">Approval Key</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('approvalKey')} />
                </th>
                <th className="hand" onClick={sort('batchApprovalKey')}>
                  <Translate contentKey="approSysApp.workEntry.batchApprovalKey">Batch Approval Key</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('batchApprovalKey')} />
                </th>
                <th>
                  <Translate contentKey="approSysApp.workEntry.hourlyRate">Hourly Rate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="approSysApp.workEntry.projectService">Project Service</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="approSysApp.workEntry.owner">Owner</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="approSysApp.workEntry.invoice">Invoice</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="approSysApp.workEntry.clientCompany">Client Company</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workEntryList.map((workEntry, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/work-entry/${workEntry.id}`} color="link" size="sm">
                      {workEntry.id}
                    </Button>
                  </td>
                  <td>{workEntry.title}</td>
                  <td>{workEntry.date ? <TextFormat type="date" value={workEntry.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{workEntry.description}</td>
                  <td>{workEntry.startTime ? <TextFormat type="date" value={workEntry.startTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{workEntry.endTime ? <TextFormat type="date" value={workEntry.endTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{workEntry.hours}</td>
                  <td>
                    <Translate contentKey={`approSysApp.WorkStatus.${workEntry.status}`} />
                  </td>
                  <td>{workEntry.totalAmount}</td>
                  <td>{workEntry.comment}</td>
                  <td>
                    {workEntry.attachments ? (
                      <div>
                        {workEntry.attachmentsContentType ? (
                          <a onClick={openFile(workEntry.attachmentsContentType, workEntry.attachments)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {workEntry.attachmentsContentType}, {byteSize(workEntry.attachments)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{workEntry.fileName}</td>
                  <td>{workEntry.fileType}</td>
                  <td>{workEntry.fileSize}</td>
                  <td>
                    {workEntry.createdDate ? <TextFormat type="date" value={workEntry.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {workEntry.lastModifiedDate ? (
                      <TextFormat type="date" value={workEntry.lastModifiedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{workEntry.approvalKeyRegeneratedDays}</td>
                  <td>
                    {workEntry.approvalKeyCreatedDate ? (
                      <TextFormat type="date" value={workEntry.approvalKeyCreatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{workEntry.approvalKey}</td>
                  <td>{workEntry.batchApprovalKey}</td>
                  <td>
                    {workEntry.hourlyRate ? <Link to={`/hourly-rate/${workEntry.hourlyRate.id}`}>{workEntry.hourlyRate.name}</Link> : ''}
                  </td>
                  <td>
                    {workEntry.projectService ? (
                      <Link to={`/project-service/${workEntry.projectService.id}`}>{workEntry.projectService.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{workEntry.owner ? <Link to={`/application-user/${workEntry.owner.id}`}>{workEntry.owner.id}</Link> : ''}</td>
                  <td>{workEntry.invoice ? <Link to={`/invoice/${workEntry.invoice.id}`}>{workEntry.invoice.id}</Link> : ''}</td>
                  <td>
                    {workEntry.clientCompany ? (
                      <Link to={`/client-company/${workEntry.clientCompany.id}`}>{workEntry.clientCompany.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/work-entry/${workEntry.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/work-entry/${workEntry.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (location.href = `/work-entry/${workEntry.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="approSysApp.workEntry.home.notFound">No Work Entries found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={workEntryList && workEntryList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default WorkEntry;
