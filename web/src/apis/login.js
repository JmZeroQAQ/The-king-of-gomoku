import $ from 'jquery';
import { URL } from './env'

export const login = (username, password) => {
    return new Promise(resolve => {
        $.ajax({
            url: URL + "/user/login/",
            type: "post",
            data: {
                username,
                password,
            },

            success: resp => {
                resolve(resp);
            },

            error: resp => {
                resolve(resp);
            }
        });
    });
}