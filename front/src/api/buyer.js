import request from '@/utils/request.js'

export const buyerLoginService = (buyerLoginData) => {
    const params = new URLSearchParams();
    for (let key in buyerLoginData) {
        params.append(key, buyerLoginData[key])
    }
    return request.post('/buyer/login', params)
}

export const buyerInfoService = () => {
    return request.get('/buyer/buyerInfo')
}

export const buyerInfoUpdateService = (buyerInfoData) => {
    return request.put('/buyer/update', buyerInfoData)
}
