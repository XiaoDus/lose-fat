"use strict";const e=require("../../common/vendor.js");if(!Array){const v=e.resolveComponent("uni-popup-message"),c=e.resolveComponent("uni-popup");(v+c)()}const x=()=>"../../uni_modules/uni-popup/components/uni-popup-message/uni-popup-message.js",w=()=>"../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";Math||(x+w)();const y={__name:"Login",setup(v){const{$request:c}=e.getCurrentInstance().appContext.config.globalProperties,{proxy:g}=e.getCurrentInstance(),u=e.ref("success"),p=e.ref(""),l=e.ref(null),a=e.ref("ssm"),r=e.reactive({phone:null,pwd:null}),o=e.reactive({phone:null,code:null}),m=t=>{var s=/[\t\r\f\n\s]*/g;return t.replace(s,"")},i=e.ref("获取验证码"),d=e.ref(!1),_=()=>{if(o.phone==null){u.value="error",p.value="请输入手机号！",l.value.open();return}let t=/^1[3-9]\d{9}$/;if(o.phone=m(o.phone),console.log(o.phone),t.test(o.phone)){let s=61;d.value=!0;let n=setInterval(()=>{s-=1,i.value=s+" 秒",s==0&&(clearInterval(n),d.value=!1,i.value="重新获取验证码")},1e3)}else u.value="error",p.value="手机号格式错误！",l.value.open()},f=()=>{let t=/^1[3-9]\d{9}$/;if(a.value=="password"){if(!t.test(r.phone)){u.value="error",p.value="手机号格式错误！",l.value.open();return}c("/user/password_login","POST",r).then(s=>{s.code==="200"?(e.index.setStorageSync("user",s.data),e.index.setStorageSync("token",s.data.token),e.index.switchTab({url:"../../pages/index/index"})):s.code==="400"&&(u.value="error",p.value=s.message,l.value.open())})}else{if(!t.test(o.phone)){u.value="error",p.value="手机号格式错误！",l.value.open();return}c("/user/sms_login","POST",o).then(s=>{s.code==="200"?(e.index.setStorageSync("user",s.data),e.index.setStorageSync("token",s.data.token),e.index.switchTab({url:"../../pages/index/index"})):s.code==="400"&&(u.value="error",p.value=s.message,l.value.open())})}},h=()=>{e.index.requestSubscribeMessage({tmplIds:["g5XYbMp7cEad6oDtV65gseJ9PdCFncCL8pH2sWaW3Ps"],success(t){console.log(t)}})};return(t,s)=>e.e({a:a.value=="password"},a.value=="password"?{b:r.phone,c:e.o(n=>r.phone=n.detail.value),d:r.pwd,e:e.o(n=>r.pwd=n.detail.value)}:{},{f:a.value=="ssm"},a.value=="ssm"?{g:o.phone,h:e.o(n=>o.phone=n.detail.value),i:o.code,j:e.o(n=>o.code=n.detail.value),k:e.t(i.value),l:d.value,m:e.o(_)}:{},{n:e.o(f),o:e.t(a.value=="ssm"?"密码":"验证码"),p:e.o(n=>a.value=="ssm"?a.value="password":a.value="ssm"),q:`${e.unref(g).$host}/file/download/ssm.png`,r:e.o(n=>a.value="ssm"),s:`${e.unref(g).$host}/file/download/wx.png`,t:e.o(h),v:e.p({type:u.value,message:p.value,duration:2e3}),w:e.sr(l,"8e5c5ab9-0",{k:"message"}),x:e.p({type:"message"})})}},S=e._export_sfc(y,[["__scopeId","data-v-8e5c5ab9"],["__file","/Users/xiaodu/前端/LoseFat/pageLogin/Login/Login.vue"]]);wx.createPage(S);
