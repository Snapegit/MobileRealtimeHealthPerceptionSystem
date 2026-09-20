const config = {
    get() {
        return {
            url : process.env.VUE_APP_BASE_API_URL + process.env.VUE_APP_BASE_API + '/',
            name: process.env.VUE_APP_BASE_API,
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/cl72615271/client/h5/index.html'
        }
    },
    getProjectName(){
        return {
            projectName: "基于Android的实时健康感知系统"
        } 
    }
}
export default config
