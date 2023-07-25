import $ from 'jquery';
import { URL } from './env'

export const login = (username, password) => {
    return new Promise(resolve => {
        $.ajax({
            url: URL + "/user/login/",
            type: "POST",
            data: {
                username,
                password,
            },

            success: resp => {
                resolve(resp);
            },

            error: () => {
                resolve({message: "密码错误"});
            }
        });
    });
}