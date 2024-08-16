import request from '@/utils/request.js'

export const inquiryListService = () => {
    return request.get('/inquiry')
}

export const inquiryListAllService = () => {
    return request.get('/inquiry/list')
}

export const inquiryAddService = (inquiryData) => {
    return request.post('/inquiry', inquiryData)
}


export const inquiryUpdateService = (inquiryData) => {
    return request.put('/inquiry', inquiryData)
}

export const inquiryDeleteService = (id) => {
    return request.delete('/inquiry?id=' + id)
}