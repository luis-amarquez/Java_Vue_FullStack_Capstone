import axios from "axios";
import { formatComments, refreshToken } from "./util";

const api = axios.create({
  baseURL: "/api/comment",
});

class CommentService {
  async getPostComments(postId, page) {
    return api
      .get(`/post/${postId}`, {
        params: {
          page: page ? page : 0,
        },
      })
      .then(async (res) => {
        return await formatComments(res);
      });
  }

  async createNewComment(accessToken, comment) {
    return api
      .post("/create", comment, {
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
            await this.createNewComment(data["accessToken"], comment).then(
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

export default new CommentService();
