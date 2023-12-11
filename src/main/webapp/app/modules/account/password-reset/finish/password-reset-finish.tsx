import React, { useState, useEffect } from 'react';
import { Col, Row, Button, Alert } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handlePasswordResetFinish, reset } from '../password-reset.reducer';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PasswordResetFinishPage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();
  const key = searchParams.get('key');

  const [password, setPassword] = useState('');

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({ newPassword }) => dispatch(handlePasswordResetFinish({ key, newPassword }));

  const updatePassword = event => setPassword(event.target.value);

  const getResetForm = () => {
    return (
      <ValidatedForm onSubmit={handleValidSubmit}>
        <ValidatedField
          name="newPassword"
          label={translate('global.form.newpassword.label')}
          placeholder={translate('global.form.newpassword.placeholder')}
          type="password"
          validate={{
            required: { value: true, message: translate('global.messages.validate.newpassword.required') },
            minLength: { value: 4, message: translate('global.messages.validate.newpassword.minlength') },
            maxLength: { value: 50, message: translate('global.messages.validate.newpassword.maxlength') },
          }}
          onChange={updatePassword}
          data-cy="resetPassword"
        />
        <PasswordStrengthBar password={password} />
        <ValidatedField
          name="confirmPassword"
          label={translate('global.form.confirmpassword.label')}
          placeholder={translate('global.form.confirmpassword.placeholder')}
          type="password"
          validate={{
            required: { value: true, message: translate('global.messages.validate.confirmpassword.required') },
            minLength: { value: 4, message: translate('global.messages.validate.confirmpassword.minlength') },
            maxLength: { value: 50, message: translate('global.messages.validate.confirmpassword.maxlength') },
            validate: v => v === password || translate('global.messages.error.dontmatch'),
          }}
          data-cy="confirmResetPassword"
        />
        <Button color="success" type="submit" data-cy="submit">
          <Translate contentKey="reset.finish.form.button">Validate new password</Translate>
        </Button>
      </ValidatedForm>
    );
  };

  const successMessage = useAppSelector(state => state.passwordReset.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(translate(successMessage));
    }
  }, [successMessage]);

  const renderSuccessMessage = () => (
    <div>
      <Alert color="success">
        {translate(successMessage)}
        <span>
          <Translate contentKey="global.messages.info.authenticated.prefixwithoutplease">sign in</Translate>{' '}
        </span>
        <Link to="/login" className="alert-link">
          <Translate contentKey="global.messages.info.authenticated.link">here</Translate>
        </Link>
      </Alert>
    </div>
  );

  const renderContent = () => {
    if (!key) {
      return null;
    } else if (successMessage) {
      return <div>{renderSuccessMessage()}</div>;
    } else {
      return getResetForm();
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="4">
          <h1>
            <Translate contentKey="reset.finish.title">Reset password</Translate>
          </h1>
          <div>{renderContent()}</div>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordResetFinishPage;
