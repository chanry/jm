webpackJsonp([1],{121:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={name:"app",components:{}}},122:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n(53);e.default={data:function(){return{form:{ids:"",region:""},url:"",datas:[]}},methods:{getSummaries:function(t){var e=t.columns,n=t.data,a=[];e.forEach(function(t,e){if("money"==t.property()){if(0==e)return void(a[e]="总计");var r=n.map(function(e){return Number(e[t.property])});r.every(function(t){return isNaN(t)})?a[e]="N/A":(a[e]=r.reduce(function(t,e){var n=Number(e);return isNaN(n)?t:t+e},0),a[e]+=" 元")}})},onSubmit:function(){var t=this,e={ids:this.form.ids};"LL"==this.form.region?(0,a.getLLPrice)(e).then(function(e){var n=e.data.data;t.datas=n}):"JE"==this.form.region?(0,a.getJEPrice)(e).then(function(e){var n=e.data.data;t.datas=n}):"OM"==this.form.region?(0,a.getOMPrice)(e).then(function(e){var n=e.data.data;t.datas=n}):"ZM"==this.form.region&&(0,a.getZMPrice)(e).then(function(e){var n=e.data.data;t.datas=n}),console.log("submit!")}}}},123:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{title:this.$route.query.title,intro:this.$route.query.intro}},methods:{handleClick:function(){this.$router.push({path:"/xlcs",query:{id:this.$route.query.id}})}}}},124:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n(53),r=n(125);!function(t){t&&t.__esModule}(r);e.default={data:function(){return{answerTip:"查看答案",show:!1,data:{},value:"",options:[],answer:""}},methods:{handleClick:function(){if(this.show)this.show=!1,this.answerTip="查看答案";else{this.show=!0,this.answerTip="重新选择";var t=this.value;"A"==t?this.answer=this.data.answera:"B"==t?this.answer=this.data.answerb:"C"==t?this.answer=this.data.answerc:"D"==t?this.answer=this.data.answerd:"E"==t?this.answer=this.data.answere:"F"==t&&(this.answer=this.data.answerf)}},getInfo:function(){var t=this,e={id:this.$route.query.id};(0,a.getXlcs)(e).then(function(e){var n=e.data.data;t.data=n;var a=[];if(""!==n.choosea){var r={label:n.choosea,value:"A"};a.push(r)}if(""!==n.chooseb){var s={label:n.chooseb,value:"B"};a.push(s)}if(""!==n.choosec){var i={label:n.choosec,value:"C"};a.push(i)}if(""!==n.choosed){var o={label:n.choosed,value:"D"};a.push(o)}if(""!==n.choosee){var u={label:n.choosee,value:"E"};a.push(u)}if(""!==n.choosef){var l={label:n.choosef,value:"F"};a.push(l)}t.options=a})}},mounted:function(){this.getInfo()}}},125:function(t,e,n){"use strict";function a(t,e){for(var e=e-(t+"").length,n=0;n<e;n++)t="0"+t;return t}Object.defineProperty(e,"__esModule",{value:!0});var r=/([yMdhsm])(\1*)/g;e.default={getRandomInt:function(t,e){return Math.floor(Math.random()*(e-t+1))+t},getQueryStringByName:function(t){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(e),a="";return null!=n&&(a=n[2]),e=null,n=null,null==a||""==a||"undefined"==a?"":a},formatDate:{format:function(t,e){return e=e||"yyyy-MM-dd",e.replace(r,function(e){switch(e.charAt(0)){case"y":return a(t.getFullYear(),e.length);case"M":return a(t.getMonth()+1,e.length);case"d":return a(t.getDate(),e.length);case"w":return t.getDay()+1;case"h":return a(t.getHours(),e.length);case"m":return a(t.getMinutes(),e.length);case"s":return a(t.getSeconds(),e.length)}})},parse:function(t,e){var n=e.match(r),a=t.match(/(\d)+/g);if(n.length==a.length){for(var s=new Date(1970,0,1),i=0;i<n.length;i++){var o=parseInt(a[i]);switch(n[i].charAt(0)){case"y":s.setFullYear(o);break;case"M":s.setMonth(o-1);break;case"d":s.setDate(o);break;case"h":s.setHours(o);break;case"m":s.setMinutes(o);break;case"s":s.setSeconds(o)}}return s}return null}}}},126:function(t,e,n){"use strict";function a(t){return t&&t.__esModule?t:{default:t}}var r=n(2),s=a(r),i=n(78),o=a(i),u=n(76),l=a(u);n(77);var c=n(79),d=a(c),f=n(75),h=a(f),v=n(46),p=a(v),m=n(74),_=a(m);s.default.use(d.default),s.default.use(p.default),s.default.use(l.default);var g=new d.default({routes:_.default,scrollBehavior:function(t,e,n){return n||{x:0,y:0}}});new s.default({router:g,store:h.default,render:function(t){return t(o.default)}}).$mount("#app")},127:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.increment=function(t){(0,t.commit)("INCREMENT")},e.decrement=function(t){(0,t.commit)("DECREMENT")}},128:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});e.getCount=function(t){return t.count}},179:function(t,e){},180:function(t,e){},181:function(t,e){},182:function(t,e){},183:function(t,e){},186:function(t,e,n){t.exports=n.p+"static/img/test.ea03aad.jpg"},187:function(t,e,n){n(179);var a=n(16)(null,n(191),"data-v-3270a76e",null);t.exports=a.exports},188:function(t,e,n){n(181);var a=n(16)(n(122),n(193),null,null);t.exports=a.exports},189:function(t,e,n){n(183);var a=n(16)(n(123),n(195),null,null);t.exports=a.exports},190:function(t,e,n){n(180);var a=n(16)(n(124),n(192),null,null);t.exports=a.exports},191:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement;return(t._self._c||e)("p",{staticClass:"page-container"},[t._v("404 page not found")])},staticRenderFns:[]}},192:function(t,e,n){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("header",{staticStyle:{"text-align":"center","margin-top":"10px","font-size":"1.2rem"}},[t._v(t._s(t.data.title))]),t._v(" "),a("mt-cell",{staticStyle:{margin:"1rem 0"},attrs:{title:t.data.question}}),t._v(" "),a("div",{staticClass:"page-swipe"},[a("mt-swipe",{attrs:{showIndicators:!1}},[a("mt-swipe-item",{staticClass:"slide1"},[a("img",{attrs:{height:"200px",width:"100%",src:n(186)}})])],1)],1),t._v(" "),a("div",{staticClass:"choose"},[a("mt-radio",{attrs:{title:"",options:t.options},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}})],1),t._v(" "),a("div",{staticClass:"answer"},[a("mt-button",{attrs:{size:"large",type:"primary"},nativeOn:{click:function(e){t.handleClick(e)}}},[t._v(t._s(t.answerTip))])],1),t._v(" "),t.show?a("div",[a("div",{staticClass:"content"},[t._v(t._s(t.answer))])]):t._e()],1)},staticRenderFns:[]}},193:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-form",{ref:"form",staticClass:"cl-form",attrs:{model:t.form,"label-width":"80px",size:"medium"}},[n("h1",[t._v("数据抓取表")]),t._v(" "),n("el-form-item",{attrs:{label:"数据ID"}},[n("el-input",{model:{value:t.form.ids,callback:function(e){t.$set(t.form,"ids",e)},expression:"form.ids"}})],1),t._v(" "),n("el-form-item",{attrs:{label:"网站来源"}},[n("el-select",{attrs:{placeholder:"请选择网站"},model:{value:t.form.region,callback:function(e){t.$set(t.form,"region",e)},expression:"form.region"}},[n("el-option",{attrs:{label:"溜溜网",value:"LL"}}),t._v(" "),n("el-option",{attrs:{label:"建E网",value:"JE"}}),t._v(" "),n("el-option",{attrs:{label:"欧模网",value:"OM"}}),t._v(" "),n("el-option",{attrs:{label:"知末网",value:"ZM"}})],1)],1),t._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary"},on:{click:t.onSubmit}},[t._v("立即搜索")]),t._v(" "),n("el-button",[t._v("取消")])],1),t._v(" "),n("el-table",{staticStyle:{width:"100%"},attrs:{data:t.datas,border:"","show-summary":"","summary-method":t.getSummaries}},[n("el-table-column",{attrs:{prop:"id",label:"ID"}}),t._v(" "),n("el-table-column",{attrs:{prop:"money",label:"金币"}}),t._v(" "),n("el-table-column",{attrs:{prop:"name",label:"名字"}})],1)],1)},staticRenderFns:[]}},194:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"app"}},[n("transition",{attrs:{name:"fade",mode:"out-in"}},[n("router-view")],1)],1)},staticRenderFns:[]}},195:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"container"},[n("div",{staticClass:"intro"},[n("header",{staticStyle:{"text-align":"center","font-size":"1.2rem"}},[t._v(t._s(t.title))]),t._v(" "),n("p",[t._v("\n            "+t._s(t.intro)+"\n        ")]),t._v(" "),n("div",{staticClass:"test"},[n("mt-button",{attrs:{size:"large",type:"primary"},nativeOn:{click:function(e){t.handleClick(e)}}},[t._v("开始测试")])],1)]),t._v(" "),n("div",{staticClass:"gg"},[t._v("\n        广告\n    ")])])},staticRenderFns:[]}},53:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.getZMPrice=e.getOMPrice=e.getJEPrice=e.getLLPrice=e.context=void 0;var a=n(102),r=function(t){return t&&t.__esModule?t:{default:t}}(a),s=(e.context="",r.default.create({baseURL:"",timeout:3e4}));e.getLLPrice=function(t){return s.get("/getLLPrice",{params:t})},e.getJEPrice=function(t){return s.get("/getJEPrice",{params:t})},e.getOMPrice=function(t){return s.get("/getOMPrice",{params:t})},e.getZMPrice=function(t){return s.get("/getZMPrice",{params:t})}},74:function(t,e,n){"use strict";function a(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var r=n(187),s=a(r),i=n(188),o=a(i),u=n(190),l=a(u),c=n(189),d=a(c),f=[{path:"/404",component:s.default,name:"",hidden:!0},{path:"/xlcs",component:l.default},{path:"/",component:o.default},{path:"/test",component:d.default},{path:"*",hidden:!0,redirect:{path:"/404"}}];e.default=f},75:function(t,e,n){"use strict";function a(t){if(t&&t.__esModule)return t;var e={};if(null!=t)for(var n in t)Object.prototype.hasOwnProperty.call(t,n)&&(e[n]=t[n]);return e.default=t,e}function r(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var s=n(2),i=r(s),o=n(46),u=r(o),l=n(127),c=a(l),d=n(128),f=a(d);i.default.use(u.default);var h={count:10},v={INCREMENT:function(t){t.count++},DECREMENT:function(t){t.count--}};e.default=new u.default.Store({actions:c,getters:f,state:h,mutations:v})},77:function(t,e){},78:function(t,e,n){n(182);var a=n(16)(n(121),n(194),null,null);t.exports=a.exports}},[126]);
//# sourceMappingURL=app.853f36982e327ab4e46d.js.map