import { createRouter,createWebHistory } from 'vue-router'

//导入组件
import SupplierLogin from '@/views/supplier/SupplierLogin.vue'
import SupplierLayout from '@/views/supplier/SupplierLayout.vue'
import BuyerLogin from '@/views/buyer/BuyerLogin.vue'
import BuyerLayout from '@/views/buyer/BuyerLayout.vue'
import ManagerLogin from '@/views/manager/ManagerLogin.vue'
import ManagerLayout from '@/views/manager/ManagerLayout.vue'

import QuotationManage from '@/views/quotation/QuotationManage.vue'
import QuotationList from '@/views/quotation/QuotationList.vue'
import MyPerformance from '@/views/performance/MyPerformance.vue'
import PerformanceList from '@/views/performance/PerformanceList.vue'
import SupplierList from '@/views/supplier/SupplierList.vue'
import QuotationCategory from '@/views/quotation/QuotationCategory.vue'
import InquiryManage from '@/views/inquiry/InquiryManage.vue'
import InquiryList from '@/views/inquiry/InquiryList.vue'
import MyPo from '@/views/po/MyPo.vue'
import SupplierAccess from '@/views/supplier/SupplierAccess.vue'
import PoManage from '@/views/po/PoManage.vue'
import PoApproval from '@/views/po/PoApproval.vue'
import PerformanceAll from '@/views/performance/PerformanceAll.vue'
import RequisitionManage from '@/views/requisition/RequisitionManage.vue'
import RequisitionApproval from '@/views/requisition/RequisitionApproval.vue'
import SupplierInfo from '@/views/supplier/SupplierInfo.vue'
import BuyerInfo from '@/views/buyer/BuyerInfo.vue'
import UserResetPassword from '@/views/supplier/SupplierResetPassword.vue'
import Welcome from '@/views/supplier/Welcome.vue'
//定义路由关系
const routes = [
    { path: '/login', component: SupplierLogin },
    {
        path: '/', 
        component: SupplierLayout, 
        redirect: '/welcome',
        children: [
            { path: '/welcome', component: Welcome },
            { 
                path: '/inquiry/list', 
                component: InquiryList,
                meta: {
                    requiresSupplierStatus: true
                },
             },
            { 
                path: '/quotation/manage',
                component: QuotationManage, 
                meta: { 
                    requiresSupplierStatus: true
                },
            },
            { 
                path: '/po/myPo', 
                component: MyPo, 
                meta: {
                    requiresSupplierStatus: true
                },
            },
            {
                path: '/performance/myPf',
                component: MyPerformance,
                meta: {
                    requiresSupplierStatus: true
                },
            },
            { path: '/supplier/info', component: SupplierInfo },
            { path: '/supplier/resetPassword', component: UserResetPassword }
        ]
    },
    { path: '/manager/login', component: ManagerLogin },
    {
        path: '/manager',
        component: ManagerLayout,
        redirect: '/requisition/approval',
        children: [
            { path: '/requisition/approval', component: RequisitionApproval },
            { path: '/performance/all', component: PerformanceAll },
            { path: '/po/approval', component: PoApproval },
            { path: '/quotation/list', component: QuotationList }
        ]
    },
    { path: '/buyer/login', component: BuyerLogin },
    {
        path: '/buyer', 
        component: BuyerLayout, 
        redirect: '/supplier/access', 
        children: [
            { path: '/supplier/access', component: SupplierAccess },
            { path: '/requisition/manage', component: RequisitionManage },
            { path: '/supplier/list', component: SupplierList },
            { path: '/performance/list', component: PerformanceList },
            { path: '/po/manage', component: PoManage },
            { path: '/quotation/category', component: QuotationCategory },
            { path: '/inquiry/manage', component: InquiryManage },
            { path: '/buyer/info', component: BuyerInfo },
            { path: '/buyer/resetPassword', component: UserResetPassword }
        ]
    }
]

//创建路由器
const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

//导出路由
export default router


