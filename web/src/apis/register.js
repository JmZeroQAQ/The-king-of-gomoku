import $ from 'jquery';
import { URL } from './env'

export const register = (username, password, confirmed_password) => {
    return new Promise(resolve => {
        $.ajax({
            url: URL + "/user/register/",
            type: "post",
            data: {
                username,
                password,
                confirmedPassword: confirmed_password,
            },

            success: resp => {
                resolve(resp);
            },

            error: () => {
                resolve({message: "哎呀，似乎遇到了一些问题"})
            },
        });
    })
}