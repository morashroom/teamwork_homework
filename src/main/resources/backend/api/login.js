function loginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data
  })
}

//封装好的js函数
function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
