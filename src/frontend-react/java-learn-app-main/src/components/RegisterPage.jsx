import React from "react";
import { Button, TextField, Typography } from "@material-ui/core";
import UserService from '../services/UserService';

class RegisterPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      loginValue: "",
      passwordValue: "",
      registerValidErrors: [],
      isUserPresent: false,
      userIsPresentErrors: '',
    };
    this.signIn = this.signIn.bind(this);
  }

  handleLoginChange = (event) => {
    this.setState({ loginValue: event.target.value });
  };

  handlePasswordChange = (event) => {
    this.setState({ passwordValue: event.target.value });
  };

  signIn() {
    UserService
      .createUser(this.state.loginValue, this.state.passwordValue)
      .then((response) => {
        this.props.history.push('/')
      })
      .catch(err => {
         if (err.response.status === 401) {
            this.setState({ registerValidErrors: err.response.data });
            this.setState({ userIsPresentErrors: '' });
         }
         if (err.response.status === 400) {
            this.setState({ isUserPresent: true })
            this.setState({ userIsPresentErrors: err.response.data.info });
            this.setState({ registerValidErrors: [] });
         }
      })
  }

  render() {

    const { isUserPresent, userIsPresentErrors, registerValidErrors } = this.state;

    const { handleLoginChange, handlePasswordChange, signIn } = this;

    return (
      <div className="container">
        <div className="container__title-wrapper">
          <Typography component="h2" variant="h3">
            Register page
          </Typography>
        </div>
        <div className="has-error">
        {
          isUserPresent &&
             <div>
                 {userIsPresentErrors}
             </div>
        }
        </div>
        <div className="container__from-wrapper">
          <form>
              <div className="form__input-wrapper">
                <div className="container__title-wrapper">
                   <Typography component="h6" variant="h5">
                      Login
                   </Typography>
                </div>
                <TextField
                  onChange={handleLoginChange}
                  label="Login"
                  variant="outlined"
                  placeholder="Enter your login"
                />
              </div>
              <div className="form__input-wrapper">
                 <div className="container__title-wrapper">
                   <Typography component="h6" variant="h5">
                      Password
                   </Typography>
                </div>
                <TextField
                  onChange={handlePasswordChange}
                  label="Password"
                  variant="outlined"
                  type="password"
                  placeholder="Enter your password"
                />
              </div>
          </form>
        </div>
        <div className="container__button-wrapper">
          <Button
            size="large"
            variant="contained"
            color="primary"
            onClick={signIn}
          >
            Sign In
          </Button>
        </div>
        {
          registerValidErrors.length > 0 &&
            <div className="has-error">
              <ol>
                {registerValidErrors.map((key) => {
                  return <li>{key} </li>
                })}
              </ol>
            </div>
        }
      </div>
    );
  }
};

export default RegisterPage;