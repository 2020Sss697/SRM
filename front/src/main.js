import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from '@/router'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import locale from 'element-plus/dist/locale/zh-cn.js'

import App from './App.vue'

const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus,{locale});
app.mount('#app')


import { ElMessageBox } from 'element-plus'
import { getSupplierStatus } from '@/api/supplier.js'

router.beforeEach(async(to, from, next) => {
    if (to.meta.requiresSupplierStatus) {
            let result = await getSupplierStatus()
            if (result.data.status === 0) {
                ElMessageBox.alert('您无权访问该功能！请先填写公司相关信息！',{
                    type: 'warning'
                })
            }else{
                next()
            }
    }else{
        next()
    }
})