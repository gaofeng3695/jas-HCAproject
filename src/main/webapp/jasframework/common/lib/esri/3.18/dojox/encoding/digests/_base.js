//>>built
define("dojox/encoding/digests/_base",["dojo/_base/lang"],function(e){e=e.getObject("dojox.encoding.digests",!0);e.outputTypes={Base64:0,Hex:1,String:2,Raw:3};e.addWords=function(d,c){var a=(d&65535)+(c&65535);return(d>>16)+(c>>16)+(a>>16)<<16|a&65535};e.stringToWord=function(d){for(var c=[],a=0,b=8*d.length;a<b;a+=8)c[a>>5]|=(d.charCodeAt(a/8)&255)<<a%32;return c};e.wordToString=function(d){for(var c=[],a=0,b=32*d.length;a<b;a+=8)c.push(String.fromCharCode(d[a>>5]>>>a%32&255));return c.join("")};
e.wordToHex=function(d){for(var c=[],a=0,b=4*d.length;a<b;a++)c.push("0123456789abcdef".charAt(d[a>>2]>>8*(a%4)+4&15)+"0123456789abcdef".charAt(d[a>>2]>>8*(a%4)&15));return c.join("")};e.wordToBase64=function(d){for(var c=[],a=0,b=4*d.length;a<b;a+=3)for(var e=(d[a>>2]>>8*(a%4)&255)<<16|(d[a+1>>2]>>8*((a+1)%4)&255)<<8|d[a+2>>2]>>8*((a+2)%4)&255,f=0;4>f;f++)8*a+6*f>32*d.length?c.push("\x3d"):c.push("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(e>>6*(3-f)&63));return c.join("")};
e.stringToUtf8=function(d){for(var c="",a=-1,b,e;++a<d.length;)b=d.charCodeAt(a),e=a+1<d.length?d.charCodeAt(a+1):0,55296<=b&&(56319>=b&&56320<=e&&57343>=e)&&(b=65536+((b&1023)<<10)+(e&1023),a++),127>=b?c+=String.fromCharCode(b):2047>=b?c+=String.fromCharCode(192|b>>>6&31,128|b&63):65535>=b?c+=String.fromCharCode(224|b>>>12&15,128|b>>>6&63,128|b&63):2097151>=b&&(c+=String.fromCharCode(240|b>>>18&7,128|b>>>12&63,128|b>>>6&63,128|b&63));return c};return e});