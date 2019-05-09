package cn.jasgroup.gis.web.opengis.controller;

import cn.jasgroup.gis.current.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.current.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.current.dataaccess.MapQueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

import javax.annotation.Resource;

/**
 * Created by kc on 2018/6/4.
 */
@Controller
@RequestMapping("/jasgis")
public class GeodataAccessController {
    /**
     *
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *
     */
    @Resource(name="geodataAccessService")
    private IGeodataAccessService geodataAccessService;
    /**
     *
     * @param sourceName
     * @param layerQueryParam
     * @return
     */
    @RequestMapping(value="{source}/query",method = RequestMethod.POST)
    @ResponseBody
    public String query(@PathVariable(value="source") String sourceName ,@RequestBody  LayerQueryParam layerQueryParam) throws Exception {
    	String result = "";
    	try{
	        layerQueryParam.setSrsname(sourceName);
	        //StringUtil.time("layer "+ sourceName + " query");
	        result = (String) geodataAccessService.query(layerQueryParam);
	        //StringUtil.timeEnd("layer "+ sourceName + " query");
    	}catch (Exception e) {
			// TODO: handle exception
    		System.err.println(e);
		}
        return result;
    }
    /**
     *
     * @param mapQueryParam
     * @return
     */
    @RequestMapping(value="query",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String query( @RequestBody MapQueryParam mapQueryParam) throws Exception {
        //StringUtil.time("map query");
        String result = (String) geodataAccessService.query(mapQueryParam);
        //StringUtil.timeEnd("map query");
        return result;
    }

}
