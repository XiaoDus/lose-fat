"use strict";const s=require("../../../../common/vendor.js"),n=()=>"./table-checkbox.js",c={name:"uniTr",components:{tableCheckbox:n},props:{disabled:{type:Boolean,default:!1},keyValue:{type:[String,Number],default:""}},options:{virtualHost:!0},data(){return{value:!1,border:!1,selection:!1,widthThArr:[],ishead:!0,checked:!1,indeterminate:!1}},created(){this.root=this.getTable(),this.head=this.getTable("uniThead"),this.head&&(this.ishead=!1,this.head.init(this)),this.border=this.root.border,this.selection=this.root.type,this.root.trChildren.push(this);const t=this.root.data.find(e=>e[this.root.rowKey]===this.keyValue);t&&(this.rowData=t),this.root.isNodata()},mounted(){if(this.widthThArr.length>0){const t=this.selection==="selection"?50:0;this.root.minWidth=Number(this.widthThArr.reduce((e,i)=>Number(e)+Number(i)))+t}},unmounted(){const t=this.root.trChildren.findIndex(e=>e===this);this.root.trChildren.splice(t,1),this.root.isNodata()},methods:{minWidthUpdate(t){if(this.widthThArr.push(t),this.widthThArr.length>0){const e=this.selection==="selection"?50:0;this.root.minWidth=Number(this.widthThArr.reduce((i,r)=>Number(i)+Number(r)))+e}},checkboxSelected(t){let e=this.root.data.find(i=>i[this.root.rowKey]===this.keyValue);this.checked=t.checked,this.root.check(e||this,t.checked,e?this.keyValue:null)},change(t){this.root.trChildren.forEach(e=>{e===this&&this.root.check(this,t.detail.value.length>0)})},getTable(t="uniTable"){let e=this.$parent,i=e.$options.name;for(;i!==t;){if(e=e.$parent,!e)return!1;i=e.$options.name}return e}}};Array||s.resolveComponent("table-checkbox")();function a(t,e,i,r,o,h){return s.e({a:o.selection==="selection"},o.selection==="selection"?{b:s.o(h.checkboxSelected),c:s.p({checked:o.checked,indeterminate:o.indeterminate,disabled:i.disabled}),d:o.border?1:""}:{})}const d=s._export_sfc(c,[["render",a],["__file","/Users/xiaodu/前端/LoseFat/uni_modules/uni-table/components/uni-tr/uni-tr.vue"]]);wx.createComponent(d);
