import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './accounts-payable.reducer';

export const AccountsPayable = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const accountsPayableList = useAppSelector(state => state.accountsPayable.entities);
  const loading = useAppSelector(state => state.accountsPayable.loading);
  const totalItems = useAppSelector(state => state.accountsPayable.totalItems);

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
      <h2 id="accounts-payable-heading" data-cy="AccountsPayableHeading">
        <Translate contentKey="approSysApp.accountsPayable.home.title">Accounts Payables</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="approSysApp.accountsPayable.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/accounts-payable/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="approSysApp.accountsPayable.home.createLabel">Create new Accounts Payable</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {accountsPayableList && accountsPayableList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="approSysApp.accountsPayable.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('deptName')}>
                  <Translate contentKey="approSysApp.accountsPayable.deptName">Dept Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deptName')} />
                </th>
                <th className="hand" onClick={sort('repLastName')}>
                  <Translate contentKey="approSysApp.accountsPayable.repLastName">Rep Last Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('repLastName')} />
                </th>
                <th className="hand" onClick={sort('repFirstName')}>
                  <Translate contentKey="approSysApp.accountsPayable.repFirstName">Rep First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('repFirstName')} />
                </th>
                <th className="hand" onClick={sort('repEmail')}>
                  <Translate contentKey="approSysApp.accountsPayable.repEmail">Rep Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('repEmail')} />
                </th>
                <th className="hand" onClick={sort('repPhoneNum')}>
                  <Translate contentKey="approSysApp.accountsPayable.repPhoneNum">Rep Phone Num</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('repPhoneNum')} />
                </th>
                <th className="hand" onClick={sort('isUsedForInvoice')}>
                  <Translate contentKey="approSysApp.accountsPayable.isUsedForInvoice">Is Used For Invoice</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isUsedForInvoice')} />
                </th>
                <th>
                  <Translate contentKey="approSysApp.accountsPayable.clientCompany">Client Company</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accountsPayableList.map((accountsPayable, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/accounts-payable/${accountsPayable.id}`} color="link" size="sm">
                      {accountsPayable.id}
                    </Button>
                  </td>
                  <td>{accountsPayable.deptName}</td>
                  <td>{accountsPayable.repLastName}</td>
                  <td>{accountsPayable.repFirstName}</td>
                  <td>{accountsPayable.repEmail}</td>
                  <td>{accountsPayable.repPhoneNum}</td>
                  <td>{accountsPayable.isUsedForInvoice ? 'true' : 'false'}</td>
                  <td>
                    {accountsPayable.clientCompany ? (
                      <Link to={`/client-company/${accountsPayable.clientCompany.id}`}>{accountsPayable.clientCompany.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/accounts-payable/${accountsPayable.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/accounts-payable/${accountsPayable.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (location.href = `/accounts-payable/${accountsPayable.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="approSysApp.accountsPayable.home.notFound">No Accounts Payables found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={accountsPayableList && accountsPayableList.length > 0 ? '' : 'd-none'}>
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

export default AccountsPayable;
