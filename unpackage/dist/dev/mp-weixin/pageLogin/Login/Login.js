"use strict";const e=require("../../common/vendor.js");if(!Array){const d=e.resolveComponent("uni-popup-message"),t=e.resolveComponent("uni-popup");(d+t)()}const f=()=>"../../uni_modules/uni-popup/components/uni-popup-message/uni-popup-message.js",h=()=>"../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";Math||(f+h)();const x={__name:"Login",setup(d){const{$request:t}=e.getCurrentInstance().appContext.config.globalProperties,{proxy:g}=e.getCurrentInstance(),l=e.ref("success"),i=e.ref(""),r=e.ref(null),o=e.ref("password"),u=e.reactive({phone:null,pwd:null}),a=e.reactive({phone:null,code:null}),p=e.ref("验证码"),c=e.ref(!1),m=()=>{t("/user/getCode?phone="+a.phone,"POST").then(s=>{if(s.code==="200"){p.value=60,c.value=!0;let v=setInterval(()=>{p.value-=1,p.value==0&&(clearInterval(v),c.value=!1,p.value="验证码")},1e3)}else l.value="error",i.value=s.message,r.value.open()})},_=()=>{o.value=="password"?t("/user/password_login","POST",u).then(s=>{s.code==="200"?(e.index.setStorageSync("user",s.data),e.index.setStorageSync("token",s.data.token),e.index.switchTab({url:"../../pages/index/index"})):s.code==="400"&&(l.value="error",i.value=s.message,r.value.open())}):t("/user/sms_login","POST",a).then(s=>{s.code==="200"?(e.index.setStorageSync("user",s.data),e.index.setStorageSync("token",s.data.token),e.index.switchTab({url:"../../pages/index/index"})):s.code==="400"&&(l.value="error",i.value=s.message,r.value.open())})};return(s,v)=>e.e({a:o.value=="password"},o.value=="password"?{b:u.phone,c:e.o(n=>u.phone=n.detail.value),d:u.pwd,e:e.o(n=>u.pwd=n.detail.value)}:{},{f:o.value=="ssm"},o.value=="ssm"?{g:a.phone,h:e.o(n=>a.phone=n.detail.value),i:a.code,j:e.o(n=>a.code=n.detail.value),k:e.t(p.value),l:c.value,m:e.o(m)}:{},{n:e.o(_),o:o.value=="ssm"},o.value=="ssm"?{p:e.o(n=>o.value="password")}:{},{q:`${e.unref(g).$host}/file/download/ssm.png`,r:e.o(n=>o.value="ssm"),s:`${e.unref(g).$host}/file/download/wx.png`,t:e.p({type:l.value,message:i.value,duration:2e3}),v:e.sr(r,"8e5c5ab9-0",{k:"message"}),w:e.p({type:"message"})})}},w=e._export_sfc(x,[["__scopeId","data-v-8e5c5ab9"],["__file","/Users/xiaodu/前端/LoseFat/pageLogin/Login/Login.vue"]]);wx.createPage(w);
