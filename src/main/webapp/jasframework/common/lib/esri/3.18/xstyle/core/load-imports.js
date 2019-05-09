//>>built
define("xstyle/core/load-imports",[],function(){function s(e,b){if(!b||0<b.indexOf(":")||"/"==b.charAt(0))return b;var m;for(b=((e||location.toString()).replace(/[^\/]*$/,"")+b).replace(/\/\.\//g,"/");m!=b;)m=b,b=b.replace(/\/[^\/]+\/\.\.\//g,"/");return b}function k(){if("undefined"!==typeof XMLHttpRequest)k=function(){return new XMLHttpRequest};else for(var e=k=function(){throw Error("getXhr(): XMLHttpRequest not available");};0<(void 0).length&&k===e;)(function(b){try{new ActiveXObject(b),k=function(){return new ActiveXObject(b)}}catch(e){}})((void 0).shift());
return k()}function t(e,b,m){var c=k();c.open("GET",e,!0);c.onreadystatechange=function(){4===c.readyState&&(400>c.status?b(c.responseText):m(Error("fetchText() failed. status: "+c.statusText)))};c.send(null)}var q={};return function(e,b){function m(){--r||(c(p),b&&b(p))}function c(a){for(var b="",e=!a.disabled&&(a.imports||a.rules||a.cssRules),h=0;h<e.length;h++){var f=e[h];f.href&&(b+=c(f.styleSheet||f))}return a.source=b+a.localSource}function k(a,b){var c=s(b,a.correctHref||a.href);a.addRule||
(a.addRule=function(a,b,c){return this.insertRule(a+"{"+b+"}",0<=c?c:this.cssRules.length)});a.deleteRule||(a.deleteRule=a.removeRule);var h=c&&q[c];if(h){var f;if(h!=a){var g=a.parentStyleSheet,d=h.ownerElement;(d.compareDocumentPosition?2!=d.compareDocumentPosition(e):d.sourceIndex<=e.sourceIndex)?f=a:(f=h,h=q[c]=a);f.addRule=function(a,b,c){h.addRule(a,b,-1<c?c:-1)};f.deleteRule=function(a){h.deleteRule(a)};var n=f.ownerNode||!g&&f.owningElement;if(n)n.parentNode.removeChild(n);else if(f.disabled=
!0,"cssText"in f)f.cssText="";else if(n=f.ownerRule)try{for(var g=n.parentStyleSheet,l=g.cssRules,d=0;d<l.length;d++)if(l[d]==n){g.deleteRule(d);break}return!0}catch(p){console.log(p)}}}if(f!=a){if(c){if(/no-xstyle$/.test(c)){a.localSource="";return}q[c]=a;a.ownerElement=e;r++;t(c,function(b){a.localSource=b;m()},function(){a.localSource="";m()})}else a.localSource=e.innerHTML;l=a.rules||a.cssRules||[];for(d=0;d<l.length;d++)g=l[d],g.selectorText&&"x-"==g.selectorText.substring(0,2)&&(a.needsParsing=
!0);l=a.imports||l;for(d=0;d<l.length;d++)g=l[d],g.href&&(g=g.styleSheet||g,k(g,c)&&d--,g.needsParsing&&(a.needsParsing=!0))}}var p=e.sheet||e.styleSheet,r=1;k(p);m()}});