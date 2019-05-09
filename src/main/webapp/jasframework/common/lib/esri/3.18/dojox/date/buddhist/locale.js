//>>built
define("dojox/date/buddhist/locale","dojox/main dojo/_base/lang dojo/_base/array dojo/date dojo/i18n dojo/regexp dojo/string ./Date dojo/i18n!dojo/cldr/nls/buddhist".split(" "),function(u,q,p,v,l,w,r,x){function y(d,e,a,f,h){return h.replace(/([a-z])\1*/ig,function(b){var c,a,m=b.charAt(0);b=b.length;var f=["abbr","wide","narrow"];switch(m){case "G":c=e.eraAbbr[0];break;case "y":c=String(d.getFullYear());break;case "M":c=d.getMonth();3>b?(c+=1,a=!0):(m=["months-format",f[b-3]].join("-"),c=e[m][c]);
break;case "d":c=d.getDate(!0);a=!0;break;case "E":c=d.getDay();3>b?(c+=1,a=!0):(m=["days-format",f[b-3]].join("-"),c=e[m][c]);break;case "a":c=12>d.getHours()?"am":"pm";c=e["dayPeriods-format-wide-"+c];break;case "h":case "H":case "K":case "k":a=d.getHours();switch(m){case "h":c=a%12||12;break;case "H":c=a;break;case "K":c=a%12;break;case "k":c=a||24}a=!0;break;case "m":c=d.getMinutes();a=!0;break;case "s":c=d.getSeconds();a=!0;break;case "S":c=Math.round(d.getMilliseconds()*Math.pow(10,b-3));a=
!0;break;case "z":if(c=v.getTimezoneName(d.toGregorian()))break;b=4;case "Z":c=d.toGregorian().getTimezoneOffset();c=[0>=c?"+":"-",r.pad(Math.floor(Math.abs(c)/60),2),r.pad(Math.abs(c)%60,2)];4==b&&(c.splice(0,0,"GMT"),c.splice(3,0,":"));c=c.join("");break;default:throw Error("dojox.date.buddhist.locale.formatPattern: invalid pattern char: "+h);}a&&(c=r.pad(c,b));return c})}function s(d,e,a,f){var h=function(b){return b};e=e||h;a=a||h;f=f||h;var b=d.match(/(''|[^'])+/g),c="'"==d.charAt(0);p.forEach(b,
function(d,m){d?(b[m]=(c?a:e)(d),c=!c):b[m]=""});return f(b.join(""))}function z(d,e,a,f){f=w.escapeString(f);l.normalizeLocale(a.locale);return f.replace(/([a-z])\1*/ig,function(f){var b;b=f.charAt(0);var c=f.length,k="";a.strict?1<c&&(k="0{"+(c-1)+"}"):k="0?";switch(b){case "y":b="\\d+";break;case "M":b=2<c?"\\S+":k+"[1-9]|1[0-2]";break;case "d":b="[12]\\d|"+k+"[1-9]|3[01]";break;case "E":b="\\S+";break;case "h":b=k+"[1-9]|1[0-2]";break;case "k":b=k+"\\d|1[01]";break;case "H":b=k+"\\d|1\\d|2[0-3]";
break;case "K":b=k+"[1-9]|1\\d|2[0-4]";break;case "m":case "s":b=k+"\\d|[0-5]\\d";break;case "S":b="\\d{"+c+"}";break;case "a":c=a.am||e["dayPeriods-format-wide-am"];k=a.pm||e["dayPeriods-format-wide-pm"];a.strict?b=c+"|"+k:(b=c+"|"+k,c!=c.toLowerCase()&&(b+="|"+c.toLowerCase()),k!=k.toLowerCase()&&(b+="|"+k.toLowerCase()));break;default:b=".*"}d&&d.push(f);return"("+b+")"}).replace(/[\xa0 ]/g,"[\\s\\xa0]")}var g=q.getObject("date.buddhist.locale",!0,u);g.format=function(d,e){e=e||{};var a=l.normalizeLocale(e.locale),
f=e.formatLength||"short",h=g._getBuddhistBundle(a),b=[],a=q.hitch(this,y,d,h,a,e.fullYear);if("year"==e.selector)return d.getFullYear();if("time"!=e.selector){var c=e.datePattern||h["dateFormat-"+f];c&&b.push(s(c,a))}"date"!=e.selector&&(f=e.timePattern||h["timeFormat-"+f])&&b.push(s(f,a));return b.join(" ")};g.regexp=function(d){return g._parseInfo(d).regexp};g._parseInfo=function(d){d=d||{};var e=l.normalizeLocale(d.locale),e=g._getBuddhistBundle(e),a=d.formatLength||"short",f=d.datePattern||e["dateFormat-"+
a],a=d.timePattern||e["timeFormat-"+a],h=[];return{regexp:s("date"==d.selector?f:"time"==d.selector?a:"undefined"==typeof a?f:f+" "+a,q.hitch(this,z,h,e,d)),tokens:h,bundle:e}};g.parse=function(d,e){d=d.replace(/[\u200E\u200F\u202A-\u202E]/g,"");e||(e={});var a=g._parseInfo(e),f=a.tokens,h=a.bundle,a=RegExp("^"+a.regexp+"$").exec(d);l.normalizeLocale(e.locale);if(!a)return null;var b=[2513,0,1,0,0,0,0],c="",k=["abbr","wide","narrow"];p.every(a,function(a,d){if(!d)return!0;var g=f[d-1],n=g.length;
switch(g.charAt(0)){case "y":b[0]=Number(a);break;case "M":if(2<n){if(g=h["months-format-"+k[n-3]].concat(),e.strict||(a=a.replace(".","").toLowerCase(),g=p.map(g,function(a){return a?a.replace(".","").toLowerCase():a})),a=p.indexOf(g,a),-1==a)return!1}else a--;b[1]=Number(a);break;case "D":b[1]=0;case "d":b[2]=Number(a);break;case "a":g=e.am||h["dayPeriods-format-wide-am"];n=e.pm||h["dayPeriods-format-wide-pm"];if(!e.strict){var l=/\./g;a=a.replace(l,"").toLowerCase();g=g.replace(l,"").toLowerCase();
n=n.replace(l,"").toLowerCase()}if(e.strict&&a!=g&&a!=n)return!1;c=a==n?"p":a==g?"a":"";break;case "K":24==a&&(a=0);case "h":case "H":case "k":b[3]=Number(a);break;case "m":b[4]=Number(a);break;case "s":b[5]=Number(a);break;case "S":b[6]=Number(a)}return!0});a=+b[3];"p"===c&&12>a?b[3]=a+12:"a"===c&&12==a&&(b[3]=0);return new x(b[0],b[1],b[2],b[3],b[4],b[5],b[6])};var t=[];g.addCustomFormats=function(d,e){t.push({pkg:d,name:e})};g._getBuddhistBundle=function(d){var e={};p.forEach(t,function(a){a=l.getLocalization(a.pkg,
a.name,d);e=q.mixin(e,a)},this);return e};g.addCustomFormats("dojo.cldr","buddhist");g.getNames=function(d,e,a,f,h){var b;f=g._getBuddhistBundle(f);d=[d,a,e];"standAlone"==a&&(a=d.join("-"),b=f[a],1==b[0]&&(b=void 0));d[1]="format";return(b||f[d.join("-")]).concat()};return g});