//>>built
define("xstyle/shim/ie-filter",[],function(){return{put:function(b,e,g){b=b.toString();if("box-shadow"==g){var c=b.split(/\s+/),d=parseFloat(c[0]),f=parseFloat(c[1]),a=Math.sqrt(d*d+f*f),d=(0<f?180:360)-180*Math.atan(d/f)/Math.PI;e.setStyle("filter","progid:DXImageTransform.Microsoft.Shadow(strength\x3d"+a+",direction\x3d"+d+",color\x3d'"+c[3]+"')")}"transform"==g&&b.match(/rotate/)&&(a=b.match(/rotate\(([-\.0-9]+)deg\)/)[1]/180*Math.PI,c=Math.cos(a),a=Math.sin(a),e.setStyle("filter","progid:DXImageTransform.Microsoft.Matrix(M11\x3d"+
c+", M12\x3d"+-a+",M21\x3d"+a+", M22\x3d"+c+", sizingMethod\x3d'auto expand')"));"opacity"==g&&(e.setStyle("filter","alpha(opacity\x3d"+100*b+")"),e.setStyle("zoom","1"))}}});