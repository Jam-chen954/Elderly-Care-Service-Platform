"use strict";const e=require("../common/vendor.js");e.defineStore("session",()=>{const n=e.ref(null),l=e.ref(null);return{account:n,accessToken:l,clear:function(){n.value=null,l.value=null}}});
