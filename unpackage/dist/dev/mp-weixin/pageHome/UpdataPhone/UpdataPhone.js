"use strict";const e=require("../../common/vendor.js");Array||e.resolveComponent("uni-popup-message")();const d=()=>"../../uni_modules/uni-popup/components/uni-popup-message/uni-popup-message.js";Math||(d+v)();const v=()=>"../../uni_modules/uni-popup/components/uni-popup/uni-popup.js",_={__name:"UpdataPhone",setup(i){const{$request:l}=e.getCurrentInstance().appContext.config.globalProperties,s=e.reactive({phone:null,code:null}),n=e.ref("success"),t=e.ref(""),u=e.ref(null),a=e.ref("验证码"),p=e.ref(!1),m=()=>{l("/user/getCode?phone="+s.phone,"POST").then(o=>{if(o.code==="200"){a.value=60,p.value=!0;let r=setInterval(()=>{a.value-=1,a.value==0&&(clearInterval(r),p.value=!1,a.value="验证码")},1e3)}else n.value="error",t.value=o.message,u.value.open()})},g=()=>{l("/user/updatePhone","POST",s).then(o=>{o.code==="200"?(e.index.setStorageSync("user",o.data),e.index.setStorageSync("token",o.data.token),n.value="success",t.value="更改成功！",u.value.open(),setTimeout(()=>{e.index.navigateBack()},2e3)):o.code==="400"&&(n.value="error",t.value=o.message,u.value.open())})};return(o,r)=>({a:s.phone,b:e.o(c=>s.phone=c.detail.value),c:s.code,d:e.o(c=>s.code=c.detail.value),e:e.t(a.value),f:p.value,g:e.o(m),h:e.o(g),i:e.p({type:n.value,message:t.value,duration:2e3}),j:e.sr(u,"22e595f3-0",{k:"message"}),k:e.p({type:"message"})})}},f=e._export_sfc(_,[["__file","/Users/xiaodu/前端/LoseFat/pageHome/UpdataPhone/UpdataPhone.vue"]]);wx.createPage(f);
