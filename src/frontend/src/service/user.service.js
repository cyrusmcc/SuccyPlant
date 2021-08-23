import api from './api';

const API_URL = 'http://localhost:8080/api/test/';

class UserService {
    getPublicContent() {
        return api.get(API_URL + 'all');
    }

    getUserBoard() {
        return api.get(API_URL + 'user', { headers: authHeader()});
    }

}

export default new UserService();