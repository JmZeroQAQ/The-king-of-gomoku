import $ from "jquery";
import { URL } from "./env";

export const getRankList = (page) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/rankList/getList/",
      type: "get",
      data: {
        page,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "获取rankList失败" });
      },
    });
  });
};
