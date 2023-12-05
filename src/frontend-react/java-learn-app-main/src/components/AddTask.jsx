import React from "react";
import {
  Button,
  TextField,
  Typography,
  InputLabel,
  Select,
} from "@material-ui/core";

import TaskService from '../services/TaskService';
import UserService from '../services/UserService';

class AddTask extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      allExecutors:[],
      title: 'New task',
      executor: '',
      description: 'This is a new task',
      invalidTaskError: [],
      isInvalidTask: false,
      accessDeniedError: '',
      isAccessDenied: false
    };

    this.handleTitleChange = this.handleTitleChange.bind(this);
    this.handleExecutorChange = this.handleExecutorChange.bind(this);
    this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
    this.handleSaveTask = this.handleSaveTask.bind(this);
    this.handleCancel = this.handleCancel.bind(this);
  }

  componentDidMount() {
     UserService.getAllExecutors().then((res) => {
        this.setState({ allExecutors: res.data });
     });
  }

  handleTitleChange = (event) => {
    this.setState({
      title: event.target.value,
    });
  };

  handleExecutorChange = (event) => {
    this.setState({
      executor: event.target.value,
    });
  };

  handleDescriptionChange = (event) => {
    this.setState({
      description: event.target.value,
    });
  };

  handleSaveTask = () => {
    const task = {
       title: this.state.title,
       executor: this.state.executor,
       description: this.state.description
    };

    TaskService.addTask(task).then(res => {
       this.props.history.push(`/tasks`);
    }).catch(err => {
        if (err.response.status === 400) {
           this.setState({ isInvalidTask: true });
           this.setState({ invalidTaskError: err.response.data });
           this.setState({ accessDeniedError: '' });
        }
        if (err.response.status === 403) {
           this.setState({ isAccessDenied: true });
           this.setState({ accessDeniedError: err.response.data.info });
           this.setState({ invalidTaskError: [] });
        }
    })
  }

  handleCancel() {
      this.props.history.push(`/tasks`);
  }

  render() {
    const {
      allExecutors,
      title,
      executor,
      description,
      invalidTaskError,
      isInvalidTask,
      accessDeniedError,
      isAccessDenied
    } = this.state;

    const { handleTitleChange, handleExecutorChange, handleDescriptionChange,
            handleSaveTask, handleCancel } = this;

    return (
      <div className="create-container">
        <div>
          <Typography display="block" variant="h3">
            Add task
          </Typography>
        </div><br/>
        {isInvalidTask &&
            <Typography className="has-error" component="h6" variant="h5">
                {invalidTaskError.map((error, index) => (
                    <div>
                        {index + 1}
                      . {error.title}
                        {error.executor}
                        {error.description}
                    </div>
                ))}
            </Typography>
        }
        {isAccessDenied &&
            <Typography className="has-error" component="h6" variant="h5">
                {accessDeniedError}
            </Typography>
        }
        <div className="container__from-wrapper">
             <form>
                <table>
                   <tr className="table">
                      <td>
                         <Typography component="h6" variant="h5">
                            Title
                         </Typography>
                      </td>
                      <td>
                         <TextField
                            required
                            onChange={handleTitleChange}
                            variant="outlined"
                            placeholder="Title"
                            style = {{width: 300}}
                            value={title}
                         />
                      </td>
                   </tr>
                   <tr className="table">
                      <td>
                         <Typography component="h6" variant="h5">
                              Description
                         </Typography>
                      </td>
                      <td>
                         <TextField
                            onChange={handleDescriptionChange}
                            variant="outlined"
                            multiline
                            rows={4}
                            placeholder="Here is a some text input"
                            style = {{width: 300}}
                            value={description}
                         />
                      </td>
                   </tr>
                   <tr className="table">
                      <td>
                         <Typography component="h6" variant="h5">
                             Executor
                         </Typography>
                      </td>
                      <td>
                         <InputLabel shrink htmlFor="executor-label">
                             Choose from the menu
                         </InputLabel>
                         <Select
                             value={executor}
                             label="executor"
                             onChange={handleExecutorChange}
                             style = {{width: 150}}
                         >
                             { allExecutors.map((obj) => {
                                  return <option value={obj}>{obj}</option>
                             })}
                         </Select>
                      </td>
                   </tr><br/>
                   <tr className="table">
                      <td>
                         <div className="container__button-wrapper">
                             <Button
                                 size="large"
                                 variant="contained"
                                 color="primary"
                                 type="reset"
                                 onClick={handleSaveTask}
                             >
                                 Save
                             </Button>
                         </div>
                      </td>
                      <td>
                         <div className="container__button-wrapper">
                             <Button
                                 size="large"
                                 variant="contained"
                                 color="secondary"
                                 type="reset"
                                 onClick={handleCancel}
                             >
                                 Cancel
                             </Button>
                         </div>
                      </td>
                   </tr>
                </table>
             </form>
           </div>
      </div>
    );
  }
}

export default AddTask;