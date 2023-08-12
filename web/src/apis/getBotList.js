import $ from "jquery";
import { URL } from "./env";

export const getBotList = (token) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/bot/getList/",
      type: "get",
      headers: {
        Authorization: "Bearer " + token,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "获取Bot列表失败" });
      }
    });
  });
};
