import React from "react";
import TasksTable from "./TasksTable";
import { AppBar, Button, Typography } from "@material-ui/core";
import TaskService from '../services/TaskService';

class MainPage extends React.Component {
   constructor(props) {
      super(props);

      this.state = {
         prop: 42,
         allTasks: [],
         total: 0,
         pageSize: 10,
         pageNumber: 1,
         currentPage: 1,
         accessDeniedError: ''
      };

      this.handleClickTitle = this.handleClickTitle.bind(this);
      this.handleLogout = this.handleLogout.bind(this);
      this.addTask = this.addTask.bind(this);
      this.editTask = this.editTask.bind(this);
      this.removeTask = this.removeTask.bind(this);
   }

   componentDidMount() {
      const { pageNumber, pageSize } = this.state;

      TaskService.getAllTasksByPages(pageSize, pageNumber).then((res) => {
         this.setState({ allTasks: res.data });
      });

      TaskService.getTotalAmount().then((res) => {
         this.setState({ total: res.data });
      });
   }

   handlePreviousPageNumberChange = () => {
       const { pageSize, pageNumber, total } = this.state;
       const maxAmountOfRowsOn3Pages = 3 * pageSize;
       const numberOfPageChanges = parseInt(total / maxAmountOfRowsOn3Pages);

       if(pageNumber !== 0) {
          TaskService.getAllTasksByPages(pageSize, pageNumber - 1).then((res) => {
             this.setState({ allTasks: res.data });
             this.setState({ pageNumber: pageNumber - 1 });

             if(this.state.pageNumber - 1 > 0) {
                this.setState({ currentPage: this.state.pageNumber - numberOfPageChanges - 1});
             } else {
                this.setState({ currentPage: 1 });
             }
          });
       }
   };

   handlePageNumberChange = (pageNumber) => {
      const { pageSize } = this.state;

      TaskService.getAllTasksByPages(pageSize, pageNumber).then((res) => {
         this.setState({ allTasks: res.data });
         this.setState({ pageNumber: pageNumber });
      });
   };

   handleNextPageNumberChange = () => {
      const { total, pageSize, pageNumber } = this.state;
      const maxAmountOfRowsOn3Pages = 3 * pageSize;
      const numberOfPageChanges = parseInt(total / maxAmountOfRowsOn3Pages);
      const pagesCount = parseInt(total / pageSize) ;

      if(pageNumber <= pagesCount) {
         TaskService.getAllTasksByPages(pageSize, +pageNumber + 1).then((res) => {
            this.setState({ allTasks: res.data });
            this.setState({ pageNumber: +pageNumber + 1 });
            this.setState({ currentPage: pageNumber + numberOfPageChanges });
         });
      };
   };

   handleLogout = () => {
      window.location.href = "/";
   };

   handleClickTitle(taskId) {
      sessionStorage.setItem("taskId", taskId);

      this.props.history.push('/tasks/' + taskId);
   }

   addTask() {
      const userRole = sessionStorage.getItem("userRole");

      if (userRole !== "ROLE_USER") {
         this.props.history.push('/add-task');
      } else {
         this.setState({ accessDeniedError: "Access is allowed only for administrator" });
      }
   }

   editTask(taskId) {
      const userRole = sessionStorage.getItem("userRole");

      if (userRole !== "ROLE_USER") {
         this.props.history.push('/edit-task/' + taskId);
      } else {
         this.setState({ accessDeniedError: "Access is allowed only for administrator" });
      }
   }

   removeTask(taskId) {
      const tasks = this.state.allTasks;
      const userRole = sessionStorage.getItem("userRole");

      if (userRole !== "ROLE_USER") {
         TaskService.deleteTask(taskId).then( res => {
            const data = tasks.filter(i => i.id !== taskId);

            this.setState({ allTasks : data });

            taskId--;
         });
      } else {
          this.setState({ accessDeniedError: "Access is allowed only for administrator" });
      }
   }

   handleSortTasksAsc = (event, field) => {
      const { pageSize, pageNumber } = this.state;

      TaskService.getAllSortedTasksByPages(field, pageSize, pageNumber).then((res) => {
         this.setState({ allTasks: res.data })
      });
   }

   handleSortTasksDesc = (event, field) => {
      const { pageSize, pageNumber } = this.state;

      TaskService.getAllDescSortedTasksByPages(field, pageSize, pageNumber).then((res) => {
         this.setState({ allTasks: res.data })
      });
   }

   render() {
     const { allTasks, total, pageNumber, currentPage, accessDeniedError } = this.state;

     const { handleSortTasksAsc, handleSortTasksDesc, addTask, editTask, removeTask, handleClickTitle,
            handlePageNumberChange, handlePreviousPageNumberChange, handleNextPageNumberChange, handleLogout} = this;

     return (
        <div>
             <div className="buttons-container">
                <Typography component="h2" variant="h3">
                    Tasks table
                </Typography>
                {accessDeniedError.length > 0 &&
                    <Typography className="has-error" component="h6" variant="h5">
                        {accessDeniedError}
                    </Typography>
                }
                <Button
                    onClick={handleLogout}
                    variant="contained"
                    color="secondary"
                >
                    Logout
                </Button>
             </div>
             <div className="table-container">
                <AppBar position="static">
                    <TasksTable
                        handleClickTitleCallback={handleClickTitle}
                        sortAscCallback={handleSortTasksAsc}
                        sortDescCallback={handleSortTasksDesc}
                        addCallback={addTask}
                        editCallback={editTask}
                        deleteCallback={removeTask}
                        tasks = {allTasks}
                        total = {total}
                        pageNumber = {pageNumber}
                    />
                </AppBar><br/>
                <div>
                    <table>
                        <tr className="table">
                            <td>
                               <Button
                                   size="large"
                                   variant="contained"
                                   color="primary"
                                   type="reset"
                                   onClick={addTask}
                               >
                                   Add Task
                               </Button>
                            </td>
                            <td>
                                <div  className="container__button-wrapper">
                                    <button
                                        size="large"
                                        variant="contained"
                                        color="primary"
                                        type="reset"
                                        onClick={handlePreviousPageNumberChange}
                                    >
                                        Previous
                                    </button>
                                </div>
                            </td>
                            <td>
                                <div className="container__button-wrapper">
                                    <button
                                        size="large"
                                        variant="contained"
                                        color="primary"
                                        type="reset"
                                        onClick={() => handlePageNumberChange(currentPage)}
                                    >
                                        {currentPage}
                                    </button>
                                </div>
                            </td>
                            <td>
                                <div className="container__button-wrapper">
                                    <button
                                        size="large"
                                        variant="contained"
                                        color="secondary"
                                        type="reset"
                                        onClick={() => handlePageNumberChange(currentPage + 1)}
                                    >
                                        {currentPage + 1}
                                    </button>
                                </div>
                            </td>
                            <td>
                                <div className="container__button-wrapper">
                                    <button
                                        size="large"
                                        variant="contained"
                                        color="primary"
                                        type="reset"
                                        onClick={() => handlePageNumberChange(currentPage + 2)}
                                    >
                                        {currentPage + 2}
                                    </button>
                                </div>
                            </td>
                            <td>
                                <div className="container__button-wrapper">
                                    <button
                                        size="large"
                                        variant="contained"
                                        color="primary"
                                        type="reset"
                                        onClick={handleNextPageNumberChange}
                                    >
                                        Next
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
             </div>
        </div>
     );
   }
 }

export default MainPage;