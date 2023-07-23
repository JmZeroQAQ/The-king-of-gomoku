import { defineStore } from "pinia";
import { ref } from 'vue';

export const useUserStore = defineStore("userStore", () => {
    const username = ref('');
    const avatar = ref('');
    const rating = ref(1500);
    const id = ref(-1);
    const isAuth = ref(false);

    function updateUser(user) {
        username.value = user.username;
        avatar.value = user.avatar;
        rating.value = user.rating;
        id.value = user.id;
    }

    function setIsAuth(flag) {
        isAuth.value = flag;
    }

    return {
        username,
        avatar,
        rating,
        id,
        isAuth,
        updateUser,
        setIsAuth,
    }
});
