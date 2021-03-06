Vue.component('jas-panel-one', { //value 值 支持逗号分隔 的多选下拉框
  props: {
    title: {},
  },
  template: [
    '<div style="height: 100%;width:100%;">',
    '  <div style="height: 32px;background: #010609 url(../../../src/images/paneltitle.png) repeat center center;border: 0;border-top: 1px solid #283f5f;border-bottom: 1px solid #2b2e3f;">',
    '    <span style="padding:0 0 0 10px;height: 100%;line-height: 30px;color: #6ee2ff;font-size: 16px;">{{title}}</span>',
    '  </div>',
    '  <div style="height: calc(100% - 32px);background: #010609;border: 1px solid #010609;">',
    '    <slot></slot>',
    '  </div>',
    '</div>',
  ].join(''),
  methods: {

  }
});
Vue.component('jas-panel-opacity', { //value 值 支持逗号分隔 的多选下拉框
  props: {
    title: {},
  },
  template: [
    '<div style="height: 100%;width:100%;">',
    '  <div style="height: 40px;background: #01060900 url(../../../src/images/line.png) no-repeat 10px 12px;">',
    '    <span style="padding-left:24px;line-height: 40px;color: #fff;font-size: 16px;">{{title}}</span>',
    '  </div>',
    '  <div style="height: calc(100% - 40px);">',
    '    <slot></slot>',
    '  </div>',
    '</div>',
  ].join(''),
  methods: {

  }
});
Vue.component('jas-onepage-wrapper', { //value 值 支持逗号分隔 的多选下拉框
  props: {
    title: {},
    index: {},
    ismax: {},
  },
  data: function () {
    return {
      titlehoverindex: '',
      rightbtnhover: false,
      ppthover: false,
      itemstyle: {
        'cursor': 'pointer ',
        'float': ' left ',
        'color': '#b8deff ',
        'height': '49px ',
        'line-height': '49px ',
        'text-align': 'center ',
        'height': '49px ',
        'width': '116px ',
        'background': ' #ffffff00 url(../../../src/images/normal_left.png) no-repeat ',
        'background-size': '100% 100%',
        'margin': '26px 1px 0 ',
      },
      itemstyle_r: {
        'background': ' #ffffff00 url(../../../src/images/normal_right.png) no-repeat ',
        'background-size': '100% 100%',

      },
      pptshowed: false
    }
  },
  computed: {
    activeStyle: function () {
      if (this.ismax) {
        return {
          width: '0px',
          'min-width': '0px'
        }
      }
      return {};
    },
    bgStyle: function () {
      if (this.index) {
        return {
          'background': '#0b1529 url(../../../src/images/ppt/' + this.index + '.png) no-repeat center center',
          'background-size': '100% 100%'
        }
      }
      return {};
    },
    pptStyle: function () {
      if (this.ppthover) {
        return {
          'background': ' url(../../../src/images/pptbtnactive.png) no-repeat center center',
          'background-size': '100% 100%'
        }
      }
      return {
        'background': ' url(../../../src/images/pptbtn.png) no-repeat center center',
        'background-size': '100% 100%'
      };
    },
    rightbtnStyle: function () {
      if (this.rightbtnhover) {
        return {
          'background': 'url(../../../src/images/rightbtn2.png) no-repeat 0 0',
          'width':'44px'
        }
      }
      return {};
    },
  },
  template: [
    // '<div style="position:relative;height: 100%;background: #0e1833 url(../../../src/images/bg.min.png) no-repeat;background-size: cover;min-height: 860px;min-width: 1596px;">',
    '<div style="position:relative;height: 100%;background-size: cover;min-height: 400px;min-width: 500px;">',
    // '	<div style="height: 110px;position: absolute;z-index:9;top: 0;width: 100%;min-width: 1596px;">',
    // '   <el-button style="position: absolute;top:-2px;right:10px;z-index: 2;" type="text" @click="checkout" >退出</el-button>',
    // '		<div style="background: #f9000000 url(../../../src/images/title02.png) no-repeat top center;height: 100%">',
    // '   <div @click="clickHome" style="width:600px;height:60px;margin:0 auto;cursor:pointer;"></div>',
    // '   </div>',
    // '	</div>',
    '	<div class=" jas-flex-box is-horizontal" style="height: 100%;min-width: 500px;">',
    // '		<div class="is-grown " style="padding-top: 110px;box-sizing: border-box;">',
    '		<div class="is-grown " style="padding-top: 0px;box-sizing: border-box;">',
    '      <slot name="center"></slot>',
    '		</div>',
    '	</div>',
    ' <div v-show="pptshowed" style="width:100%;height:100%;position: fixed;top:0;left:0;z-index: 9999;background:#000000d9;">',
    '   <div style="position:relative;border-radius:4px; margin:5vh auto 0;height:90vh;width:133vh;padding:10px;border: 2px solid #0aacff;background:#0b1529;">',
    '     <div style="height:100%;" :style="bgStyle"></div>',
    '     <el-button type="text" style="position:absolute;right:10px;top:0px;" @click="pptshowed=false">关闭</el-button>',
    '   </div>',
    ' </div>',
    '</div>',
  ].join(''),
  methods: {
    clickHome: function () {
      location.href = '../total/total.html';
    },
    checkout: function () {
      location.href = '../../../login.html';
    },
    showppt: function () {
      this.pptshowed = true;
    }
  }
});


Vue.component('jas-iframe-onepage-dialog', {
  props: {
    refSelf: {
      default: 'jas-iframe-dialog',
      type: String
    },
    width: {
      default: '80%',
      type: String
    },
    height: {
      default: '80%',
      type: String
    },
    iframeUrl: {
      default: '',
      type: String
    },
    visible: {
      default: false,
      type: Boolean
    },
    title: {
      default: '提示',
      type: String
    }

  },
  data: function () {

    return {
      selfvisible: true,
    }
  },
  watch: {
    visible: function () {
      this.selfvisible = this.visible;
    },
    selfvisible: function (newValue) {
      this.$emit('update:visible', newValue);
    },
  },
  methods: {
    close: function () {
      // this.$emit('update:visible', false)
      this.$emit('close');
    },
    setDialogHeiht: function () {
      var dom = this.$el.querySelector('.el-dialog');
      if (this.height.indexOf('%') !== -1) {
        var height = this.height.split('%')[0];
        if (height <= 0 || height >= 100) return {};
        dom.style['margin-top'] = (100 - height) / 2 + 'vh';
        dom.style['height'] = height + 'vh';
      } else if (this.height.indexOf('px') !== -1) {
        var height02 = this.height.split('px')[0];
        var wrap_height = document.documentElement.clientHeight;
        if (height02 < wrap_height) {
          var toper = (wrap_height - height02) / 2;
          dom.style['margin-top'] = toper + 'px';
        }
        dom.style['height'] = this.height;
      }
    }
  },
  mounted: function () {
    this.setDialogHeiht();
  },
  template: [
    '<el-dialog :ref="refSelf" class="jas-iframe-onepage-dialog jas-iframe-dialog" :close-on-click-modal="false" :title="title" :visible.sync="selfvisible" :width="width" @close="close" :fullscreen="false" :modal="false">',
    '  <iframe class="dialog-iframe" :src="iframeUrl" frameborder="0"></iframe>',
    '</el-dialog>'
  ].join(''),
});



Vue.component('jas-dialog', {
	props: {
		refSelf: {
			default: 'jas-self-dialog',
			type: String
		},
		width: {
			default: '80%',
			type: String
		},
		height: {
			default: '80%',
			type: String
		},
		iframeUrl: {
			default: '',
			type: String
		},
		visible: {
			default: false,
			type: Boolean
		},
		title: {
			default: '提示',
			type: String
		},
    isDetail:{
       default: false,
			 type: Boolean
    },
    left:{
       default:"",
			 type: String
    },
    right:{
       default:"",
			 type: String
    },
    top:{
       default:"",
			 type: String
    },
    bottom:{
       default:"",
			 type: String
    },

  },
  data: function () {

		return {
			selfvisible: true,
		}
	},
	watch: {
		visible: function () {
      this.selfvisible = this.visible;
		},
		selfvisible: function (newValue) {
      this.$emit('update:visible', newValue);
      if(!newValue){
        this.$emit('close');
      }
		},
	},
	methods: {
		close: function () {
			this.$emit('close');
		},
		setDialogHeiht: function () {
			var dom = this.$el;
			if(this.isDetail){
				dom.className = "jas-dialog jas-dialog-detail"
			}
      if(this.left && this.left != "" || this.right && this.right != "" || this.top && this.top != "" || this.bottom && this.bottom != ""){
          dom.className = "jas-dialog-self";
          if(this.left && this.left != ""){
            dom.style['left'] = this.left;
          }
          if(this.right && this.right != ""){
            dom.style['right'] = this.right;
          }
          if(this.top && this.top != ""){
            dom.style['top'] = this.top;
          }
          if(this.bottom && this.bottom != ""){
            dom.style['bottom'] = this.bottom;
          }

          dom.style['width'] = this.width;
          dom.style['height'] = this.height;

      }else{
        if (this.height.indexOf('%') !== -1) {
          var height = this.height.split('%')[0];
          if (height <= 0 || height >= 100) return {};
          dom.style['margin-top'] = (100 - height) / 2 + 'vh';
          dom.style['height'] = height + 'vh';
        } else if (this.height.indexOf('px') !== -1) {
          var height02 = this.height.split('px')[0];
          var wrap_height = document.documentElement.clientHeight;
          if (height02 < wrap_height) {
            var toper = (wrap_height - height02) / 2;
            dom.style['margin-top'] = toper + 'px';
          }
          dom.style['width'] = this.width;
          dom.style['height'] = this.height;
        }
      }
		}
	},
	mounted: function () {
    var that = this;
    // setTimeout(function(){
    that.setDialogHeiht();
    // })
  },
  template: [
    '<div class="jas-dialog"  :ref="refSelf" :fullscreen="false" >',
    '<div class="jas-dialog-header">',
    '<div class="jas-dialog-title">',
    '{{title}}',
    '</div>',
    '<div class="jas-dialog-tools">',
    '<span class="jas-dialog-close" @click="close"></span>',
    '</div>',
    '</div>',
    '<div class="jas-dialog-body">',
    '<iframe :src="iframeUrl"  frameborder="0" width="100%" height="100%"></iframe>',
    '</div>',
    '</div>',
  ].join(''),
});

Vue.component('jas-dialog-new', {
	props: {
		refSelf: {
			default: 'jas-self-dialog-new',
			type: String
		},
		width: {
			default: '80%',
			type: String
		},
		height: {
			default: '80%',
			type: String
		},
		iframeUrl: {
			default: '',
			type: String
		},
		visible: {
			default: false,
			type: Boolean
		},
		title: {
			default: '提示',
			type: String
		},
    isDetail:{
       default: false,
			 type: Boolean
    }

	},
	data: function () {

		return {
			selfvisible: true,
		}
	},
	watch: {
		visible: function () {
			this.selfvisible = this.visible;
		},
		selfvisible: function (newValue) {
			this.$emit('update:visible', newValue);
		},
	},
	methods: {
		close: function () {
			this.$emit('close');
		},
		setDialogHeiht: function () {
      console.log(this.$el);

      console.log("isDetail:"+this.isDetail);
			var dom = this.$el;
			if(this.isDetail){
				dom.className = "jas-dialog-new jas-dialog-new-detail"
			}
			if (this.height.indexOf('%') !== -1) {
				var height = this.height.split('%')[0];
				if (height <= 0 || height >= 100) return {};
				dom.style['margin-top'] = (100 - height) / 2 + 'vh';
				dom.style['height'] = height + 'vh';
			} else if (this.height.indexOf('px') !== -1) {
				var height02 = this.height.split('px')[0];
				var wrap_height = document.documentElement.clientHeight;
				if (height02 < wrap_height) {
					var toper = (wrap_height - height02) / 2;
					dom.style['margin-top'] = toper + 'px';
				}
				dom.style['width'] = this.width;
        dom.style['height'] = this.height;
			}
		}
	},
	mounted: function () {
    var that = this;
    // setTimeout(function(){
      that.setDialogHeiht();
    // })
	},
  template: [
			'<div class="jas-dialog-new"  :ref="refSelf" :fullscreen="false" :modal="false">',
        '<div class="jas-dialog-new-header">',
          '<div class="jas-dialog-new-title">',
            '{{title}}',
          '</div>',
          '<div class="jas-dialog-new-tools">',
            '<span class="jas-dialog-new-close" @click="close"></span>',
          '</div>',
        '</div>',
        '<div class="jas-dialog-new-body">',
            '<iframe :src="iframeUrl"  frameborder="0" width="100%" height="100%"></iframe>',
        '</div>',
      '</div>',
	].join(''),
});