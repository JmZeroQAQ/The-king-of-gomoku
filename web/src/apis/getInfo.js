import $ from "jquery";
import { URL } from './env';

export const getInfo = (token) => {
    return new Promise(resolve => {
        $.ajax({
            url: URL + "/user/getInfo/",
            type: "get",
            headers: {
                'Authorization': "Bearer " + token,
            },
            success(resp) {
                resolve(resp);
            }
        });
    })
}