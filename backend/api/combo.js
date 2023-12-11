// 查询列表数据
const getPackagesPage = (params) => {
  return $axios({
    url: '/packages/page',
    method: 'get',
    params
  })
}

// 删除数据接口
const deletePackages = (ids) => {
  return $axios({
    url: '/packages',
    method: 'delete',
    params: { ids }
  })
}

// 修改数据接口
const editPackages = (params) => {
  return $axios({
    url: '/packages',
    method: 'put',
    data: { ...params }
  })
}

// 新增数据接口
const addPackages = (params) => {
  return $axios({
    url: '/packages',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情接口
const queryPackagesById = (id) => {
  return $axios({
    url: `/packages/${id}`,
    method: 'get'
  })
}

// 批量起售禁售
const packagesStatusByStatus = (params) => {
  return $axios({
    url: `/packages/status/${params.status}`,
    method: 'post',
    params: { ids: params.ids }
  })
}
