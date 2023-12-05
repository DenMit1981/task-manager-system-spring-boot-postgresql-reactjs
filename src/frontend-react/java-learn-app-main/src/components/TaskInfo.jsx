import React from "react";
import { Link } from "react-router-dom";
import TaskService from '../services/TaskService';

import {
  Button,
  TableRow,
  Typography,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
} from "@material-ui/core";


class TaskInfo extends React.Component {
  constructor(props) {
     super(props)

     this.state = {
        task: {},
     }

     this.handleLogout = this.handleLogout.bind(this);
  }

  componentDidMount() {
     const taskId = this.props.match.params.id;

     TaskService.getTaskById(taskId).then(res => {
        this.setState({ task: res.data });
     });
  }

  handleLogout = () => {
     window.location.href = "/";
  };

  render() {
      const { task } = this.state;
      const { handleLogout } = this;

      return (
             <div className="container">
                <div align='center'>
                   <div className="buttons-container">
                      <Button component={Link} to="/tasks" variant="contained" color="primary">
                          Back to tasks
                      </Button>
                      <Button
                         onClick={handleLogout}
                         variant="contained"
                         color="secondary"
                      >
                         Logout
                      </Button>
                    </div>
                    <div className="container__title-wrapper">
                       <Typography component="h2" variant="h4">
                          {`Task № ${task.id} - ${task.title}`}
                       </Typography>
                    </div><br/>
                    <div className="task-data-container__info">
                       <TableContainer className="task-table" component={Paper}>
                         <Table>
                           <TableBody>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Created on:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.creationDate}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Modified on:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.lastModifiedDate}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Status:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.status}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Executor:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.executor}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Admin:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.admin}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                             <TableRow>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   Description:
                                 </Typography>
                               </TableCell>
                               <TableCell>
                                 <Typography align="left" variant="subtitle1">
                                   {task.description}
                                 </Typography>
                               </TableCell>
                             </TableRow>
                           </TableBody>
                         </Table>
                       </TableContainer>
                     </div>
               </div>
             </div>
         );
     }
}

export default TaskInfo;