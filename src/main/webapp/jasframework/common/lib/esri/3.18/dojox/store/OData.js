//>>built
define("dojox/store/OData","dojo/io-query dojo/request dojo/_base/lang dojo/json dojo/_base/declare dojo/store/util/QueryResults".split(" "),function(k,f,h,l,g,m){return g(null,{headers:{MaxDataServiceVersion:"2.0"},target:"",idProperty:"id",accepts:"application/json;odata\x3dverbose",childAttr:"children",constructor:function(b){g.safeMixin(this,b)},get:function(b,a){a=a||{};var c=h.mixin({Accept:this.accepts},this.headers,a.headers||a);return f(this.target+"("+b+")",{handleAs:"json",headers:c}).then(function(a){return a.d})},
getIdentity:function(b){return b[this.idProperty]},put:function(b,a){a=a||{};var c=this.getIdentity(b)||a[this.idProperty],d=c?this.target+"("+c+")":this.target,e=h.mixin({"Content-Type":"application/json;odata\x3dverbose",Accept:this.accepts},this.headers,a.headers);c&&(e["X-HTTP-Method"]=a.overwrite?"PUT":"MERGE",e["IF-MATCH"]=a.overwrite?"*":a.etag||"*");return f.post(d,{handleAs:"json",data:l.stringify(b),headers:e})},add:function(b,a){a=a||{};a.overwrite=!1;return this.put(b,a)},remove:function(b,
a){a=a||{};return f.post(this.target+"("+b+")",{headers:h.mixin({"IF-MATCH":"*","X-HTTP-Method":"DELETE"},this.headers,a.headers)})},getFormDigest:function(){var b=this.target.indexOf("_vti_bin"),b=this.target.slice(0,b)+"_api/contextinfo";return f.post(b).then(function(a){return a.substring(a.indexOf("\x3cd:FormDigestValue\x3e")+19,a.indexOf("\x3c/d:FormDigestValue\x3e"))})},getChildren:function(b,a){var c=this.getIdentity(object)||a[this.idProperty];return this.query({$filter:this.idProperty+" eq "+
c,$expand:this.childAttr},a)},query:function(b,a){a=a||{};var c=h.mixin({Accept:this.accepts},this.headers,a.headers);if(a&&a.sort){b.$orderby="";var d,e,g;e=0;for(g=a.sort.length;e<g;e++)d=a.sort[e],b.$orderby+=(0<e?",":"")+encodeURIComponent(d.attribute)+(d.descending?" desc":" asc")}if(0<=a.start||0<=a.count)b.$skip=a.start||0,b.$inlinecount="allpages","count"in a&&Infinity!=a.count&&(b.$top=a.count);b=this.buildQueryString(b);c=f(this.target+(b||""),{handleAs:"json",headers:c});d=c.then(function(a){return a.d.results});
d=m(d);d.total=c.then(function(a){return a.d.__count});return d},buildQueryString:function(b){var a="",c;for(c in b)if(b.hasOwnProperty(c)&&-1==c.indexOf("$")){var d=b[c]+"",e=d.indexOf("*");-1!=e&&(d=d.slice(0!=e?0:1,d.length-(0!=e?1:0)),0<d.length&&(a+=0==a.length?"":"and ",a+=(0==e?"endswith":"startswith")+"("+c+",'"+d+"')"))}0<a.length&&(b.$filter=b.$filter&&0<b.$filter.length?b.$filter+" and "+a:a);a=-1<this.target.indexOf("?");b&&"object"==typeof b&&(b=(b=k.objectToQuery(b))?(a?"\x26":"?")+
b:"");return b}})});