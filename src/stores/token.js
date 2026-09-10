import { defineStore } from "pinia";
import { ref } from "vue";

const useTokenStore = defineStore("token", () => {
    const token = ref('');

    const setToken = (newToken) => {
        token.value = newToken;
    }

    const removeToken = () => {
        token.value = '';
    }

    return {
        token,
        setToken,
        removeToken
    }
})

export { useTokenStore };