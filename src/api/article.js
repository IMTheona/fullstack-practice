import request from '@/utils/request'
import { useTokenStore } from '@/stores/token.js'

//获取文章分类列表
export const ArticleCategoryListService = () => {
    // const tokenStore = useTokenStore();
    // return request.get('/category',{headers: {'Authorization':tokenStore.token}})
    return request.get('/category')
}

//添加文章分类
export const ArticleCategoryAddService = (categoryData) => {
    return request.post('/category',categoryData)
}

//更新文章分类
export const ArticleCategoryUpdateService = (categoryData) => {
    return request.put('/category',categoryData)
}

//删除文章分类
export const ArticleCategoryDeleteService = (id) => {
    return request.delete('/category?id='+id)
}

//文章列表查询
export const ArticleListService = (params) => {
    return request.get('/article', { params: params })
}

//添加文章
export const ArticleAddService = (articleData) => {
    return request.post('/article', articleData)
}

//更新文章
export const ArticleUpdateService = (articleData) => {
    return request.put('/article', articleData)
}

//删除文章
export const ArticleDeleteService = (id) => {
    return request.delete('/article?id=' + id)
}