import axios from "axios";

const api = axios.create({
  baseURL: "/api",
});

class AuthService {
  async login(user) {
    return api
      .post("/login", {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        return response.data;
      });
  }
  async register(user) {
    return api.post("/register", {
      username: user.username,
      password: user.password,
    });
  }
}

export default new AuthService();
