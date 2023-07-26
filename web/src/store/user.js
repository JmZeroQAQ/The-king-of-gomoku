import { defineStore } from "pinia";
import { ref, reactive } from 'vue';
import { getInfo } from '@/apis/getInfo';
import { removeToken } from "@/utils/storage";

export const useUserStore = defineStore("userStore", () => {
    const user = reactive({
        id: -1,
        name: "",
        avatar: "",
        rating: "",
    });

    // 是否登录
    const isAuth = ref(false);
    // jwt令牌
    let token = "";

    async function asyncGetInfo() {
        const data = await getInfo(token);
        if(data.message === "success") {
            user.id = data.id;
            user.name = data.username;
            user.avatar = data.avatar;
            user.rating = data.rating;
        } else {
            // 登录失效，localStorage清除失效的令牌
            if(data.code === 401) {
                removeToken();
            }
            setIsAuth(false);
            setToken("");
        }
    }

    function setIsAuth(flag) {
        isAuth.value = flag;
    }

    function setToken(new_token) {
        token = new_token;
    }

    function logout() {
        user.id = -1;
        user.name = "";
        user.avatar = "";
        user.rating = "";

        isAuth.value = false;
        token = "";
        removeToken();
    }

    return {
        user,
        isAuth,
        asyncGetInfo,
        setIsAuth,
        setToken,
        logout,
    }
});
