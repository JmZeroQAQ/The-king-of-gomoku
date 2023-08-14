import $ from "jquery";
import { URL } from "./env";

export const getAllList = (page) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/record/getAllList/",
      type: "get",
      data: {
        page,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "获取记录失败" });
      },
    });
  });
};

export const getRecordInfo = (recordId) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/record/getInfo/",
      type: "get",
      data: {
        record_id: recordId,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "获取回放信息失败" });
      },
    });
  });
};

export const getMyRecords = (token, page) => {
  return new Promise((resolve) => {
    $.ajax({
      url: URL + "/record/getMyRecords/",
      type: "get",
      headers: {
        Authorization: "Bearer " + token,
      },
      data: {
        page,
      },
      success(resp) {
        resolve(resp);
      },
      error() {
        resolve({ message: "获取记录失败" });
      },
    });
  });
};
