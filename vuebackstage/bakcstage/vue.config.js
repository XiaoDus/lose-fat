// vue.config.js
module.exports = {
    chainWebpack: (config) => {
        config.plugin('define').tap((definitions) => {
            Object.assign(definitions[0], {
                __VUE_OPTIONS_API__: 'true',
                __VUE_PROD_DEVTOOLS__: 'false',
                __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
            })
            return definitions
        })
    },
    //相当于webpack-dev-server,  对本地服务器进行配置
    // devServer : {
    //     proxy:  {
    //         //以“/a”作为开头的axios请求都会进行代理
    //         "/user" : {
    //             target: "http://localhost:8080",  //请求目标服务器的url
    //             changeOrigin: true,        //是否跨域（选为true）
    //             pathRewrite: {
    //                 "^/user" : ""   //将axios请求url中的/a进行重写
    //             }
    //         }
    //     }
    // }
}