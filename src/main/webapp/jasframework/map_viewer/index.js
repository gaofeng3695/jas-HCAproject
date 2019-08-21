/**
 * 项目js
 *
 * Created by kc on 2017/9/15.
 */
var rootPath = getRootPath();
/**
 * @desc 获取系统根路径
 */
function getRootPath() {
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substring(1).indexOf('/') + 1);
    return (localhostPaht + projectName + "/");
}
/**
 * 引入init.js之前，需要先定义dojoConfig对象，用于定义自定义扩展的dojo类的命名空间
 */
var dojoConfig = {
    "parseOnLoad": true,
    "async": true,
    "baseUrl":"../../jasframework/common/lib/esri/3.18/dojo"
};

