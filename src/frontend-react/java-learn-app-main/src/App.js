import "./App.css";
import AuthenticatedRoute from "./components/AuthenticatedRoute";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import MainPage from "./components/MainPage";
import TaskInfo from "./components/TaskInfo";
import AddTask from "./components/AddTask";
import EditTask from "./components/EditTask";


import {
   BrowserRouter as Router,
   Route,
   Switch,
} from "react-router-dom";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact component={LoginPage} />
        <Route path="/register" exact component={RegisterPage} />
        <AuthenticatedRoute path="/tasks" exact component={MainPage} />
        <Route path="/add-task" component={AddTask} />
        <Route path="/edit-task/:id" component={EditTask} />
        <Route path="/tasks/:id" component={TaskInfo} />
      </Switch>
    </Router>
  );
}

export default App;
