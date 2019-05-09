//>>built
define("dojox/sketch/Slider",["dojo/_base/kernel","dojo/_base/lang","dojo/_base/declare","dijit/form/HorizontalSlider","./_Plugin"],function(b){b.getObject("sketch",!0,dojox);b.declare("dojox.sketch.Slider",dojox.sketch._Plugin,{_initButton:function(){this.slider=new dijit.form.HorizontalSlider({minimum:5,maximum:100,style:"width:100px;",baseClass:"dijitInline dijitSlider"});this.slider._movable.node.title='Double Click to "Zoom to Fit"';this.connect(this.slider,"onChange","_setZoom");this.connect(this.slider.sliderHandle,
"ondblclick","_zoomToFit")},_zoomToFit:function(){var a=this.figure.getFit();this.slider.attr("value",this.slider.maximum<a?this.slider.maximum:this.slider.minimum>a?this.slider.minimum:a)},_setZoom:function(a){a&&this.figure&&this.figure.zoom(a)},reset:function(){this.slider.attr("value",this.slider.maximum);this._zoomToFit()},setToolbar:function(a){this._initButton();a.addChild(this.slider);a._reset2Zoom||(a._reset2Zoom=!0,this.connect(a,"reset","reset"))}});dojox.sketch.registerTool("Slider",dojox.sketch.Slider);
return dojox.sketch.Slider});