"use strict";const o=require("../../common/vendor.js");if(!Array){const a=o.resolveComponent("uni-icons"),n=o.resolveComponent("uni-list-item");(a+n)()}const _=()=>"../../uni_modules/uni-icons/components/uni-icons/uni-icons.js",f=()=>"../../uni_modules/uni-list/components/uni-list-item/uni-list-item.js";Math||(_+f)();const m=o.defineComponent({__name:"FoodList",setup(a){const{$request:n}=o.getCurrentInstance().appContext.config.globalProperties,d=o.reactive({1:"#70c995",2:"#f7cc4f",3:"red"}),l=o.ref([]),r=async e=>{o.index.showToast({icon:"loading",title:"加载中",duration:2e3});const s=await n("/foods/get_foodbyclass/?id="+e,"GET");s.code==="200"&&(l.value=s.data,console.log(s.data))},u=async e=>{const s=await n("/foods/food_message?foodId="+e,"GET");if(s.code=="200"){const t=s.data,c=await n("/collect/get_collect?foodId="+e,"GET");t.collect=c.data,o.index.navigateTo({url:"../FoodMessage/FoodMessage",success:i=>{o.index.$emit("foodMessage",t)}})}};return o.onLoad(e=>{o.index.setNavigationBarTitle({title:e.name}),r(e.id)}),(e,s)=>({a:o.f(l.value,(t,c,i)=>({a:"4c479ab0-1-"+i+","+("4c479ab0-0-"+i),b:o.p({type:"smallcircle-filled",size:"15",color:d[t.healthLevel]}),c:o.o(p=>u(t.foodId),c),d:"4c479ab0-0-"+i,e:o.p({clickable:!0,title:t.name,note:t.calory+"千卡/100克",link:!0}),f:c}))})}}),g=o._export_sfc(m,[["__file","/Users/xiaodu/前端/LoseFat/pageSearch/FoodList/FoodList.vue"]]);wx.createPage(g);
