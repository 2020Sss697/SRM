import request from '@/utils/request.js'


export const supplierRegisterService = (supplierRegisterData) => {

    const params = new URLSearchParams()
    for (let key in supplierRegisterData) {
        params.append(key, supplierRegisterData[key]);
    }
    return request.post('/supplier/register', params);
}

export const supplierLoginService = (supplierLoginData) => {
    const params = new URLSearchParams();
    for (let key in supplierLoginData) {
        params.append(key, supplierLoginData[key])
    }
    return request.post('/supplier/login', params)
}

export const supplierInfoService = ()=>{
    return request.get('/supplier/supplierInfo')
}

export const supplierInfoUpdateService = (supplierInfoData) => {
    return request.put('/supplier/update', supplierInfoData)
}

export const supplierUpdatePwdService = (supplierUpdatePwdData) =>{
    return request.put('/supplier/updatePwd', supplierUpdatePwdData)
}

export const supplierAccessService = (id) => {
    return request.put('/supplier/access?id=' + id);
}

export const supplierListAllService = () => {
    return request.get('/supplier')
}

export const supplierListWaitService = () => {
    return request.get('/supplier/wait')
}


export const getSupplierStatus = () => {
    return request.get('/supplier/status')
}