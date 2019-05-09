// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.18/esri/copyright.txt for details.
//>>built
define("esri/workers/WorkerClient","dojo/Evented dojo/_base/declare dojo/Deferred dojo/_base/lang dojo/request ../sniff ../kernel ../urlUtils require".split(" "),function(f,n,h,e,p,k,q,g,r){var l=window.Blob||window.webkitBlob||window.mozBlob,m=window.URL||window.webkitURL||window.mozURL;f=n([f],{declaredClass:"esri.workers.WorkerClient",worker:null,returnDeferreds:!1,_queue:null,constructor:function(a,c){this._isIE=k("ie");this.returnDeferreds=!!c;this._queue={};this._acceptMessage=e.hitch(this,
this._acceptMessage);this._errorMessage=e.hitch(this,this._errorMessage);a&&(this.worker=this.setWorker(a))},setWorker:function(a){if(a instanceof Array){var c=a;a=c.shift()}a=this._getUrl(a);var b=!g.hasSameOrigin(g.getAbsoluteUrl(a),location.href),d;if(!1===a)return console.log("Can not resolve worker path"),!1;this.worker&&(d=this.worker,d.removeEventListener("message",this._acceptMessage,!1),d.removeEventListener("error",this._errorMessage,!1),d.terminate(),d=null);if(b){var e=this._getUrl("./mutableWorker",
!0);try{var f=this._getRemoteText(e,!0);d=new Worker(m.createObjectURL(new l([f],{type:"text/javascript"})))}catch(h){try{e=g.getProxyUrl(e).path+"?"+encodeURI(e),d=new Worker(e),this._useProxy=!0}catch(k){return console.log("Can not create worker"),!1}}}else d=new Worker(a);d.addEventListener("message",this._acceptMessage,!1);d.addEventListener("error",this._errorMessage,!1);this.worker=d;b&&this.importScripts(a);c&&this.importScripts(c);return d},postMessage:function(a,c){if(a instanceof Array||
"object"!=typeof a)a={data:a};var b=Math.floor(64E9*Math.random()).toString(36);a.msgId=b;b=this._queue[b]=new h;this.worker?(c?this.worker.postMessage(a,c):this.worker.postMessage(a),this.emit("start-message",{target:this,message:a})):b.reject({message:"No worker was set."});return this.returnDeferreds?b:b.promise||b},terminate:function(){var a=Object.keys(this._queue);this.worker&&this.worker.terminate();for(var c=a.length-1;0<=c;c--)this._queue[a[c]].cancel("terminated"),delete this._queue[a[c]]},
addWorkerCallback:function(a,c){var b;b=this._getUrl(a,!0);!1===b?(b=new h,b.reject({message:"Could not load text from "+a})):(b=this.postMessage({action:"add-callback",url:b,cbName:c||"main"}),b.then(e.hitch(this,function(a){a.target=this;this.emit("callback-added",a)})));return b},importScripts:function(a){Array.isArray(a)||(a=[a]);a=a.map(function(a){a=this._getUrl(a,!0);this._useProxy&&!g.hasSameOrigin(a,location.href)&&(a=g.getProxyUrl(a).path+"?"+encodeURI(a));return a},this);a=this.postMessage({action:"import-script",
url:a});a.then(e.hitch(this,function(a){a.target=this;this.emit("scripts-imported",a)}));return a},_acceptMessage:function(a){var c=a.data,b=c.msgId;if(c.status&&"debug"==c.status)console[c.showAs||"debug"](c);else if(b&&b in this._queue){var d=this._queue[b];"progress"==c.status?d.progress(a.data):("error"==c.status?d.reject(a.data):d.resolve(a.data),delete this._queue[b])}this.emit("message",{message:a.data,event:a,target:this})},_errorMessage:function(a){this.onerror||this.onError?this.onerror?
this.onerror(a):this.onError(a):console.log("Worker Error: "+a.message+"\nIn "+a.filename+" on "+a.lineno)},_getUrl:function(a,c){if(!a)return console.error("can not resolve empty path"),!1;a.match(/\.js$/)||(a+=".js");var b=r.toUrl(a);return c?g.getAbsoluteUrl(b):b},_getRemoteText:function(a,c){var b="";(a=this._getUrl(a))&&p.get(a,{sync:c,handleAs:"text",headers:{"X-Requested-With":"","Content-Type":"text/plain"}}).then(function(a){b=a});return b},_startBlobWorker:function(){var a=this._xdSource;
a||(a=this._getUrl("./mutableWorker"),a=new l(["if(!self._mutable){importScripts('"+a+"');}"],{type:"text/javascript"}),a=this._xdSource=m.createObjectURL(a));try{return new Worker(a)}catch(c){return console.log(c.message),!1}}});k("extend-esri")&&e.setObject("workers.WorkerClient",f,q);return f});