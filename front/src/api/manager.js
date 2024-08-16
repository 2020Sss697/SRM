import request from '@/utils/request.js'

export const managerLoginService = (managerLoginData) => {
    const params = new URLSearchParams();
    for (let key in managerLoginData) {
        params.append(key, managerLoginData[key])
    }
    return request.post('/manager/login', params)
}

export const managerInfoService = () => {
    return request.get('/manager/managerInfo')
}