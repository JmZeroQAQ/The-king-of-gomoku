import $ from "jquery";
import { URL } from "./env";

export const updateBot = (token, bot) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/bot/update/",
      type: "post",
      headers: {
        Authorization: "Bearer " + token,
      },
      data: {
        bot_id: bot.id,
        title: bot.title,
        description: bot.description,
        content: bot.content,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "修改Bot失败" });
      },
    });
  });
};
