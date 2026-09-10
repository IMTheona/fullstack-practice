import request from '@/utils/request'
import { useTokenStore } from '@/stores/token.js'

export const ArticleCategoryListService = () => {
    const tokenStore = useTokenStore();
    return request.get('/category',{headers: {'Authorization':tokenStore.token}})
}