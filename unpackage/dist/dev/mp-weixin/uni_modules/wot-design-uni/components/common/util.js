"use strict";require("../../../../common/vendor.js");function l(t){return Number.isNaN(Number(t))?t:`${t}px`}function c(t){return Object.prototype.toString.call(t)==="[object Object]"||typeof t=="object"}function p(t){const r=Object.prototype.toString.call(t).match(/\[object (\w+)\]/);return r&&r.length?r[1].toLowerCase():""}function j(t,n,r){const e=(t<<16|n<<8|r).toString(16);return"#"+"0".repeat(Math.max(0,6-e.length))+e}function a(t){const n=[];for(let r=1;r<7;r+=2)n.push(parseInt("0x"+t.slice(r,r+2),16));return n}const S=(t,n,r=2)=>{const e=a(t),o=a(n),g=(o[0]-e[0])/r,b=(o[1]-e[1])/r,d=(o[2]-e[2])/r,u=[];for(let i=0;i<r;i++)u.push(j(parseInt(String(g*i+e[0])),parseInt(String(b*i+e[1])),parseInt(String(d*i+e[2]))));return u},h={id:1e3};function y(t){return t.replace(/[A-Z]/g,function(r){return"-"+r}).toLowerCase()}function x(t){return t.replace(/-(\w)/g,(n,r)=>r.toUpperCase())}function A(t){return typeof Array.isArray=="function"?Array.isArray(t):Object.prototype.toString.call(t)==="[object Array]"}function C(t){return p(t)==="function"}function O(t){return p(t)==="string"}function s(t){return A(t)?t.filter(function(n){return n!=null&&n!==""}).map(function(n){return s(n)}).join(";"):O(t)?t:c(t)?Object.keys(t).filter(function(n){return t[n]!=null&&t[n]!==""}).map(function(n){return[y(n),t[n]].join(":")}).join(";"):""}function f(t,n){return Object.keys(n).forEach(r=>{const e=t[r],o=n[r];c(e)&&c(o)?f(e,o):t[r]=o}),t}const w=(t,n)=>{const r=n.split(".");try{return r.reduce((e,o)=>e!=null?e[o]:void 0,t)}catch{return}};exports.addUnit=l;exports.camelCase=x;exports.context=h;exports.deepAssign=f;exports.getPropByPath=w;exports.gradient=S;exports.isFunction=C;exports.objToStyle=s;
