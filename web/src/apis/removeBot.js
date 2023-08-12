import $ from "jquery";
import { URL } from "./env";

export const removeBot = (token, botId) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/bot/remove/",
      type: "post",
      headers: {
        Authorization: "Bearer " + token,
      },
      data: {
        bot_id: botId,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "删除Bot失败" });
      },
    });
  });
};
