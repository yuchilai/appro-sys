import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button, Card } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import { Clock } from 'app/shared/clock/clock';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isApprover = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.APPROVER]));

  return (
    <>
      {!isAuthenticated ? (
        <Row>
          <Col md="3" className="pad">
            <span className="hipster rounded" />
          </Col>
          <Col md="9">
            <h1 className="display-4">
              <Translate contentKey="home.title">Welcome to ApproSys!</Translate>
            </h1>
            <p className="lead">
              <Translate contentKey="home.subtitle">
                ApproSys is a tailored solution designed to streamline the project approval and invoicing process for freelance programming
                consultants.
              </Translate>
            </p>
            <div>
              <Alert color="warning">
                <Translate contentKey="global.messages.info.authenticated.prefix">Please sign in </Translate>

                <Link to="/login" className="alert-link">
                  <Translate contentKey="global.messages.info.authenticated.link">here</Translate>
                </Link>
                <Translate contentKey="global.messages.info.authenticated.suffix">
                  . If you are an approver, you should request your designated client liaison to assign you as their official approver.
                </Translate>
              </Alert>

              <Alert color="warning">
                <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
                <Link to="/account/register" className="alert-link">
                  <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
                </Link>
              </Alert>
            </div>
            <p>
              <Translate contentKey="home.like">If you like my project, do not forget to give us a star on</Translate>{' '}
              <a href="https://github.com/yuchilai" target="_blank" rel="noopener noreferrer">
                GitHub
              </a>
              !
            </p>
          </Col>
        </Row>
      ) : (
        <Row>
          <Col md="3" className="pad">
            <h1 className="display-5">
              <Translate contentKey="home.welcome">Welcome</Translate> {account.login}
            </h1>
            <h2 className="display-6">
              <Clock />
            </h2>
          </Col>
          <Col md="9">
            <h2 className="d-flex justify-content-center display-1" id="home-control-pannel" data-cy="">
              <Translate contentKey="home.controlPannel">Control Pannel</Translate>
            </h2>
            <Row>
              <Col md="6">
                <Card>
                  <Button tag={Link} to="/application-user" size="lg" replace color="primary" outline className="mb-3" data-cy="">
                    <FontAwesomeIcon icon="user-tie" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="approSysApp.applicationUser.home.title">Application User</Translate>
                    </span>
                  </Button>
                  <Button tag={Link} to="/client-company" size="lg" replace color="primary" outline className="mb-3" data-cy="">
                    <FontAwesomeIcon icon="building" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="approSysApp.clientCompany.home.title">Company</Translate>
                    </span>
                  </Button>
                </Card>
              </Col>
              <Col md="6">
                <Card>
                  <Button tag={Link} to="/work-entry" size="lg" replace color="secondary" outline className="mb-3" data-cy="">
                    <FontAwesomeIcon icon="calendar-days" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="approSysApp.workEntry.home.title">Work Schedules</Translate>
                    </span>
                  </Button>
                  <Button tag={Link} to="/invoice" size="lg" replace color="secondary" outline className="mb-3" data-cy="">
                    <FontAwesomeIcon icon="file-invoice-dollar" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="approSysApp.invoice.home.title">Invoices</Translate>
                    </span>
                  </Button>
                </Card>
              </Col>
            </Row>
          </Col>
        </Row>
      )}
    </>
  );
};

export default Home;
