import router from "../router";
import axios from "axios";

const MS_PER_DAY = 24 * 60 * 60 * 1000;
const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

export const formatPosts = async (response) => {
  return {
    posts: response.data["content"],
    totalPages: response.data["totalPages"],
    totalPosts: response.data["totalElements"],
    currentPage: response.data["number"],
    lastPage: response.data["last"],
  };
};

export const formatComments = async (response) => {
  return {
    comments: response.data["content"],
    totalPages: response.data["totalPages"],
    totalComments: response.data["totalElements"],
    currentPage: response.data["number"],
    lastPage: response.data["last"],
  };
};

export const dateTimeDiff = (dateTime) => {
  const currentDateTime = new Date();
  const pastDateTime = new Date(dateTime);
  const dateTimeDiff = Math.abs(currentDateTime - pastDateTime);
  const dayDiff = Math.round(dateTimeDiff / MS_PER_DAY);

  if (dayDiff > 7) {
    return `${MONTHS[pastDateTime.getMonth()]} ${pastDateTime.getDay()}, ${pastDateTime.getFullYear()}`;
  } else if (dayDiff > 1) {
    return `${dayDiff} days ago`;
  } else if (dayDiff > 0) {
    return "1 day ago";
  } else {
    const timeDiff = dateTime % MS_PER_DAY;
    console.log(timeDiff);
    return "";
  }
};

export const delay = (t, v) => {
  return new Promise(function (resolve) {
    setTimeout(resolve.bind(null, v), t);
  });
};

export const refreshToken = async () => {
  let auth = JSON.parse(localStorage.getItem("auth"));
  return await axios
    .get("/api/token/refresh", {
      headers: {
        Authorization: `Bearer ${auth.refreshToken}`,
      },
    })
    .then((res) => {
      auth.accessToken = res.data.accessToken;
      localStorage.setItem("auth", JSON.stringify(auth));
      return {
        accessToken: res.data.accessToken,
      };
    })
    .catch((err) => {
      if (err.response.status === 403) {
        auth.authenticated = false;
        auth.accessToken = null;
        auth.refreshToken = null;
        auth.user = null;
        localStorage.setItem("auth", JSON.stringify(auth));
        router.push({ name: "home" });
        return err;
      } else {
        return err;
      }
    });
};
