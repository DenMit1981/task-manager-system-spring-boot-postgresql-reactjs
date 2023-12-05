import React from "react";
import PropTypes from "prop-types";
import icons from 'glyphicons'

import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from "@material-ui/core";

import { TASKS_TABLE_COLUMNS } from "../constants/tablesColumns";

class TasksTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      page: 0,
      rowsPerPage: 15,
    };
  }

  render() {

    const { tasks, sortAscCallback, sortDescCallback, editCallback, deleteCallback,
            handleClickTitleCallback, total, pageNumber } = this.props;

    const { page, rowsPerPage } = this.state;

    return (
       <Paper>
         <TableContainer>
           <Table style={{borderTop: '2px solid black'}}>
             <TableHead style={{borderTop: '2px solid black', borderBottom: '2px solid black'}} align="center">
              <TableRow>
                <TableCell style={{visibility: 'none'}}></TableCell>
                {TASKS_TABLE_COLUMNS.map((column) => (
                  <TableCell style={{borderRight: '2px solid black', borderLeft: '2px solid black'}}>
                    <div key={column.id}>
                       <div class="field"><b>{column.label}</b></div>
                       {column.label !== 'Creation Date' && column.label !== 'Modified Date'?
                          <div>
                             <div class="up-arrow" onClick={(e) => sortAscCallback(e, column.id)}></div>
                             <div class="down-arrow" onClick={(e) => sortDescCallback(e, column.id)}></div>
                          </div>
                              :
                          <div>
                             <div class="up-arrow-date" onClick={(e) => sortAscCallback(e, column.id)}></div>
                             <div class="down-arrow-date" onClick={(e) => sortDescCallback(e, column.id)}></div>
                          </div>
                       }
                    </div>
                  </TableCell>
                ))}
                  <TableCell style={{visibility: 'none'}}></TableCell>
              </TableRow>
             </TableHead>
             <TableBody>
              {tasks
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row, index) => {
                  return (
                    <TableRow style={{borderTop: '2px solid black', borderBottom: '2px solid black'}}
                        hover role="checkbox"
                        align="center"
                        key={index}>
                        <TableCell>
                            <button
                                onClick={() => editCallback(row.id)}
                                variant="contained"
                                color="primary"
                            >
                                <span>{icons.pencil}</span>
                            </button>
                        </TableCell>
                        {TASKS_TABLE_COLUMNS.map((column) => {
                            const value = row[column.id];
                            if (column.id === "title") {
                                return (
                                    <TableCell style={{borderRight: '2px solid black', borderLeft: '2px solid black'}}
                                        key={row.id}>
                                            <a className="box" style={{ textDecoration: 'underline' }}
                                               onClick={() => handleClickTitleCallback(row.id)}
                                            >
                                                {value}
                                            </a>
                                    </TableCell>
                                   );
                            } else {
                                return <TableCell style={{borderRight: '2px solid black', borderLeft: '2px solid black'}}
                                            key={column.id}>{value}
                                       </TableCell>;
                            }
                        })}
                        <TableCell>
                             <button key={row.id}
                                 onClick={() => deleteCallback(row.id)}
                                 variant="contained"
                                 color="primary"
                             >
                                 <span>{icons.cross}</span>
                             </button>
                        </TableCell>
                    </TableRow>
                  );
                })}
            </TableBody>
         </Table><br/>
         <Typography align="right" component="h5" variant="h5">
             Page: {pageNumber}
         </Typography>
         <Typography align="right" component="h5" variant="h5">
             Total: {total}
         </Typography>
       </TableContainer>
      </Paper>
    );
  }
}

TasksTable.propTypes = {
  addCallback: PropTypes.func,
  editCallback: PropTypes.func,
  deleteCallback: PropTypes.func,
  tasks: PropTypes.array,
  total: PropTypes.number,
  pageNumber: PropTypes.number
};

export default TasksTable;
