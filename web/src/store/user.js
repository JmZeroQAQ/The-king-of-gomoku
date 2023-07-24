import { defineStore } from "pinia";
import { ref, reactive } from 'vue';

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

    function updateUser(user) {
        user.id = user.id;
        user.name = user.name;
        user.avatar = user.avatar;
        user.rating = user.rating;
    }

    function setIsAuth(flag) {
        isAuth.value = flag;
    }

    function setToken(token) {
        token = token;
    }

    return {
        user,
        isAuth,
        updateUser,
        setIsAuth,
        setToken,
    }
});
