const url_all = {
	'DEV': 'http://127.0.0.1:3000', // 开发
	'PRO': 'http://192.168.213.153:3000', // 生产
	'Reflection': 'http://1el9898253.oicp.vip/'
}

let BASEURL = url_all[ 'DEV' ] // 调整当前环境
// let BASEURL = url_all[ 'PRO' ] // 调整当前环境
// let BASEURL = url_all[ 'Reflection' ] // 调整当前环境

/*
 * 全局请求封装
 * @param path 请求路径
 * @param method 请求类型(GET/POST/DELETE等)
 * @oaram data 请求体数据
 * @param loading 请求未完成是是否显示加载中，默认为true
 */
export const Request = ( path, method, data ) => {
	const token = uni.getStorageSync( "token" ) != null ? uni.getStorageSync( "token" ) : null;
	return new Promise( ( resolve, reject ) => {
		uni.request( {
			url: BASEURL + path,
			method: method,
			data,
			header: {
				"Authorization": token
			},
			success( response ) {


				resolve( response.data );


			},
			fail( err ) {
				uni.showToast( {
					icon: "error",
					title: '服务响应失败'
				} );
				console.error( err );
				reject( err );
			},
			complete() {
				uni.hideLoading();
			}
		} );
	} );
}