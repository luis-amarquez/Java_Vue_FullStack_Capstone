import axios from "axios";
import { formatPosts, formatComments } from "./util";

const api = axios.create({
  baseURL: "/api/user",
});

class UserService {
  // TODO: simplify this to just take in auth
  async getUserAccountInfo(accessToken) {
    return await api
      .get("/account", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        return response.data;
      });
  }

  async getUserPosts(accessToken, page) {
    return await api
      .get("/account/posts", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (response) => {
        return await formatPosts(response);
      });
  }

  async getUserComments(accessToken, page) {
    return await api
      .get("/account/posts", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (response) => {
        return await formatComments(response);
      });
  }

  async getUserFromUsername(username) {
    return await api.get(`/${username}`).then((response) => {
      return response.data;
    });
  }

  async getUsersPosts(username, page) {
    return await api
      .get(`/${username}/posts`, {
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (response) => {
        return await formatPosts(response);
      });
  }
}

export default new UserService();
