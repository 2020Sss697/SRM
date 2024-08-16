import request from '@/utils/request.js'

export const performanceListService = () => {
    return request.get('/performance')
}

export const performanceAddService = (performanceData) => {
    return request.post('/performance', performanceData)
}

export const performanceUpdateService = (quotationData) => {
    return request.put('/performance', quotationData)
}

export const performanceListAllService = () => {
    return request.get('/performance/all')
}

export const performanceListMyService = () => {
    return request.get('/performance/myPf')
}