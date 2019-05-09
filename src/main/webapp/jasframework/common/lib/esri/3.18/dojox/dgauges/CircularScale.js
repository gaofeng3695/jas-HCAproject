//>>built
define("dojox/dgauges/CircularScale",["dojo/_base/declare","dojox/gfx","./ScaleBase","./_circularUtils"],function(s,q,t,d){return s("dojox.dgauges.CircularScale",t,{originX:50,originY:50,radius:50,startAngle:0,endAngle:180,orientation:"clockwise",constructor:function(){this.labelPosition="inside";this.addInvalidatingProperties("originX originY radius startAngle endAngle orientation".split(" "))},_getOrientationNum:function(){return"cclockwise"==this.orientation?-1:1},positionForValue:function(a){var b=
d.computeTotalAngle(this.startAngle,this.endAngle,this.orientation);a=this.scaler.positionForValue(a);return d.modAngle(this.startAngle+this._getOrientationNum()*b*a,360)},_positionForTickItem:function(a){var b=d.computeTotalAngle(this.startAngle,this.endAngle,this.orientation);return d.modAngle(this.startAngle+this._getOrientationNum()*b*a.position,360)},valueForPosition:function(a){if(this.positionInRange(a))var b=d.modAngle(this._getOrientationNum()*(a-this.startAngle),360),e=d.computeTotalAngle(this.startAngle,
this.endAngle,this.orientation),b=b/e;else{b=d.modAngle(this.startAngle-a,360);e=360-b;a=d.modAngle(this.endAngle-a,360);var f=360-a,b=Math.min(b,e)<Math.min(a,f)?0:1}return this.scaler.valueForPosition(b)},positionInRange:function(a){if(this.startAngle==this.endAngle)return!0;a=d.modAngle(a,360);return 1==this._getOrientationNum()?this.startAngle<this.endAngle?a>=this.startAngle&&a<=this.endAngle:!(a>this.endAngle&&a<this.startAngle):this.startAngle<this.endAngle?!(a>this.startAngle&&a<this.endAngle):
a>=this.endAngle&&a<=this.startAngle},_distance:function(a,b,e,f){return Math.sqrt((e-a)*(e-a)+(f-b)*(f-b))},_layoutLabel:function(a,b,e,f,d,g,k){var l=this._getFont();b=q._base._getTextBox(b,{font:q.makeFontString(q.makeParameters(q.defaultFont,l))}).w;var l=q.normalizedLength(l.size),m=e+Math.cos(g)*d-b/2,n=f-Math.sin(g)*d-l/2,h,p=[],c=h=m;h=-Math.tan(g)*h+f+Math.tan(g)*e;h>=n&&h<=n+l&&p.push({x:c,y:h});c=h=m+b;h=-Math.tan(g)*h+f+Math.tan(g)*e;h>=n&&h<=n+l&&p.push({x:c,y:h});h=n;c=-1/Math.tan(g)*
h+e+1/Math.tan(g)*f;c>=m&&c<=m+b&&p.push({x:c,y:h});h=n+l;c=-1/Math.tan(g)*h+e+1/Math.tan(g)*f;c>=m&&c<=m+b&&p.push({x:c,y:h});if("inside"==k)for(k=0;k<p.length;k++){if(c=p[k],c=this._distance(c.x,c.y,e,f)-d,0<=c){m=e+Math.cos(g)*(d-c)-b/2;n=f-Math.sin(g)*(d-c)-l/2;break}}else for(k=0;k<p.length;k++)if(c=p[k],c=this._distance(c.x,c.y,e,f)-d,0>=c){m=e+Math.cos(g)*(d-c)-b/2;n=f-Math.sin(g)*(d-c)-l/2;break}a&&a.setTransform([{dx:m+b/2,dy:n+l}])},refreshRendering:function(){this.inherited(arguments);
if(this._gfxGroup&&this.scaler){this.startAngle=d.modAngle(this.startAngle,360);this.endAngle=d.modAngle(this.endAngle,360);this._ticksGroup.clear();for(var a,b,e=this.scaler.computeTicks(),f,r=0;r<e.length;r++){b=e[r];a=this.tickShapeFunc(this._ticksGroup,this,b);f=this._gauge._computeBoundingBox(a);var g;g=b.position?this._positionForTickItem(b):this.positionForValue(b.value);a&&a.setTransform([{dx:this.originX,dy:this.originY},q.matrix.rotateg(g),{dx:this.radius-f.width-2*f.x,dy:0}]);if(b=this.tickLabelFunc(b)){a=
this._ticksGroup.createText({x:0,y:0,text:b,align:"middle"}).setFont(this._getFont()).setFill(this._getFont().color?this._getFont().color:"black");var k=this.radius,k="inside"==this.labelPosition?k-(f.width+this.labelGap):k+this.labelGap;this._layoutLabel(a,b,this.originX,this.originY,k,d.toRadians(360-g),this.labelPosition)}}for(var l in this._indicatorsIndex)this._indicatorsRenderers[l]=this._indicatorsIndex[l].invalidateRendering()}}})});