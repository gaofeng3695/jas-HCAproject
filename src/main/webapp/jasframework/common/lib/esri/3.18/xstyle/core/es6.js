//>>built
define("xstyle/core/es6",[],function(){function r(a,c,b){e?-1===e.indexOf(a)&&e.push(a):(e=[a],setTimeout(function(){e.forEach(function(b){var a=[];b.properties.forEach(function(c){a.push({target:b.object,name:c})});b(a);b.object=null;b.properties=null});e=null},0));a.object=c;a=a.properties||(a.properties=[]);-1===a.indexOf(b)&&a.push(b)}function s(){return s}var p=Object.observe,l;if(l=Object.defineProperty)a:{try{Object.defineProperty({},"t",{});l=!0;break a}catch(x){}l=void 0}var h={observe:p,
defineProperty:l,promise:"undefined"!==typeof Promise,WeakMap:"function"===typeof WeakMap},p=h.observe?Object.observe:h.defineProperty?function(a,c){c.addKey=function(b){var d="key"+b;if(!this[d]){this[d]=!0;var g=a[b];if((d=Object.getOwnPropertyDescriptor(a,b))&&d.set){var e=d.set,v=d.get;Object.defineProperty(a,b,{get:function(){return g=v.call(this)},set:function(a){e.call(this,a);g!==a&&(g=a,c&&r(c,this,b))}})}else Object.defineProperty(a,b,{get:function(){return g},set:function(a){g!==a&&(g=
a,c&&r(c,this,b))}})}};c.remove=function(){c=null}}:function(a,c){t||(t=!0,setInterval(function(){for(var b=0,a=k.length;b<a;b++){var d=q[b],c=k[b],e=n[b],m=void 0,f=void 0;for(f in d)d.hasOwnProperty(f)&&d[f]!==c[f]&&(d[f]=c[f],(m||(m=[])).push({name:f}));for(f in c)c.hasOwnProperty(f)&&!d.hasOwnProperty(f)&&(d[f]=c[f],(m||(m=[])).push({name:f}));m&&e(m)}},20));var b={},d;for(d in a)a.hasOwnProperty(d)&&(b[d]=a[d]);k.push(a);q.push(b);n.push(c)},e;l=h.observe?Object.unobserve:function(a,c){c.remove&&
c.remove();for(var b=0,d=k.length;b<d;b++)if(k[b]===a&&n[b]===c){k.splice(b,1);q.splice(b,1);n.splice(b,1);break}};var k=[],q=[],n=[],t=!1,w=1,u=function(){};u.prototype.toJSON=s;return{Promise:h.promise?Promise:function(){function a(c){function b(a){a&&a.then?a.then(b,d):(l=a,g())}function d(a){h=a;g()}function g(){e=!0;for(var a=0,b=k.length;a<b;a++)k[a]();k=0}var e,l,h,k=0;c(b,d);return{then:function(b,d){return new a(function(a,c){function g(){try{h&&!d?c(h):a(h?d(h):b?b(l):l)}catch(e){c(e)}}
e?g():(k||(k=[])).push(g)})}}}return a}(),WeakMap:h.WeakMap?WeakMap:function(a,c){var b="__"+(c||"")+w++;return h.defineProperty?{get:function(a){return a[b]},set:function(a,c){Object.defineProperty(a,b,{value:c,enumerable:!1})}}:{get:function(a){return(a=a[b])&&a.value},set:function(a,c){(a[b]||(a[b]=new u)).value=c}}},observe:p,unobserve:l,copy:function(a,c){for(var b in c)a[b]=c[b];return a}}});