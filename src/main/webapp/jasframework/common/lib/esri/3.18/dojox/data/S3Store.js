//>>built
define("dojox/data/S3Store",["dojo/_base/declare","dojox/data/JsonRestStore","dojox/rpc/ProxiedPath"],function(c,f,g){return c("dojox.data.S3Store",f,{_processResults:function(a){a=a.getElementsByTagName("Key");for(var b=[],c=this,d=0;d<a.length;d++){var e={_loadObject:function(a,b){return function(b){delete this._loadObject;c.service(a).addCallback(b)}}(a[d].firstChild.nodeValue,e)};b.push(e)}return{totalCount:b.length,items:b}}})});