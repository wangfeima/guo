package common.utils.json.fastjson;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import common.utils.collection.CollectionUtils;
import common.utils.stringutils.StringUtils;

public class JsonUtils {

	/**
	 * Map转String类型
	 * @param map
	 * @return
	 */
	public static String map2String(Map<String,Object> map){
		
		if(CollectionUtils.isEmpty(map)){
			return "";
		}
		return JSONObject.toJSONString(map);
	}
	 
	/**
	 * Map转String类型
	 * @param
	 * @return
	 */
	public static String entity2String(Object object){
		return JSONObject.toJSONString(object);
	}

	/**
	 * 字符串转Map
	 * "[{aa:XX,bb:XX}]" 这种类型数据
	 * @param str
	 * @return
	 */
	public static Map<String,Object> string2Map(String str){
		
		if(StringUtils.isEmpty(str)){
			return null;
		} 
		return JSONObject.parseObject(str);
	}
}
