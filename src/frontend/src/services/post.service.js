import axios from "axios";
import { formatPosts, refreshToken } from "./util";

const api = axios.create({
  baseURL: "/api/posts",
});

class PostsService {
  async getHomePosts(page) {
    return await api
      .get("/home", {
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (response) => {
        return await formatPosts(response);
      });
  }

  async getCategoryPosts(page, category) {
    return await api
      .get(`/explore/${category}`, {
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (response) => {
        return await formatPosts(response);
      });
  }

  async getUserPost(username, postId) {
    return await api.get(`/post/${username}/${postId}`).then((response) => {
      return response.data;
    });
  }

  async createNewPost(accessToken, newPost) {
    return await api
      .post("/create", newPost, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        return response;
      })
      .catch(async (err) => {
        if (err.response.status === 403) {
          let data = await refreshToken();

          if (data["accessToken"]) {
            await this.createNewPost(data["accessToken"], newPost).then(
              (res) => {
                return res;
              }
            );
          } else {
            return data;
          }
        } else {
          return err;
        }
      });
  }
}

export default new PostsService();
