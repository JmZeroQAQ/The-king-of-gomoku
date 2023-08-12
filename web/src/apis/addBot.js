import $ from "jquery";
import { URL } from "./env";

export const addBot = (token, bot) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/bot/add/",
      type: "post",
      headers: {
        Authorization: "Bearer " + token,
      },
      data: {
        title: bot.title,
        description: bot.description,
        content: bot.content,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "添加Bot失败" });
      },
    });
  });
};
