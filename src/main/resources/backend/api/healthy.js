//员工健康信息的分页查询接口
const getHealthPage= (params) => {
    return $axios({
        url: '/healthy/page',
        method: 'get',
        params
    })
}

//添加员工的健康信息
const addHealthInfo=(params)=> {
    console.log("参数的值是"+params);
    return $axios({
        url: '/healthy/add',
        method: 'post',
        data: { ...params }
    })
}