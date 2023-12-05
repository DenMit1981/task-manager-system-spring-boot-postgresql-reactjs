import axios from 'axios';

const TASK_API_BASE_URL = "http://localhost:8081/tasks"

class TaskService {

    addTask(task) {
        return axios.post(TASK_API_BASE_URL, task);
    }

    getTaskById(taskId) {
        return axios.get(TASK_API_BASE_URL + '/' + taskId);
    }

    updateTask(task, taskId) {
        return axios.put(TASK_API_BASE_URL + '/' + taskId, task);
    }

    deleteTask(taskId) {
        return axios.delete(TASK_API_BASE_URL + '/' + taskId);
    }

    getAllTasksByPages = (pageSize, pageNumber) => {
        return axios.get(TASK_API_BASE_URL + '?pageSize=' + pageSize + '&pageNumber=' + pageNumber);
    }

    getAllSortedTasksByPages = (sortField, pageSize, pageNumber) => {
        return axios.get(TASK_API_BASE_URL + '?sortField=' + sortField + '&pageSize=' + pageSize + '&pageNumber=' + pageNumber);
    }

    getAllDescSortedTasksByPages = (sortField, pageSize, pageNumber) => {
        return axios.get(TASK_API_BASE_URL + '?sortField=' + sortField + '&sortDirection=desc' + '&pageSize=' + pageSize + '&pageNumber=' + pageNumber);
    }

    getTotalAmount() {
        return axios.get(TASK_API_BASE_URL + '/total');
    }
}

export default new TaskService()