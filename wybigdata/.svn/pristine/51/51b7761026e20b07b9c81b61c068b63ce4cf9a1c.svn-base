package common.utils.object2map;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @description: map 实体 互相转换
 * @author: FangRen
 * @version V2.0 
 * @date: 2018年7月24日 上午10:06:12
 * @copyright©2018东方微银网络信息（北京）有限公司 
 * @fileName:util.MapObjUtil.java
 */
public class MapObjUtil {

	/**
	 * 实体对象转成Map
	 *
	 * @param obj
	 *            实体对象
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Map转成实体对象
	 *
	 * @param map
	 *            map实体对象包含属性
	 * @param clazz
	 *            实体对象类型
	 * @return
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		if (map == null) {
			return null;
		}
		T obj = null;
		try {
			obj = clazz.newInstance();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				String filedTypeName = field.getType().getName();
				if (filedTypeName.equalsIgnoreCase("java.util.date")) {
					String datetimestamp = String.valueOf(map.get(field.getName()));
					if (datetimestamp.equalsIgnoreCase("null")) {
						field.set(obj, null); 
					} else {
						field.set(obj, new Date(Long.parseLong(datetimestamp)));
					}
				} else {
					field.set(obj, map.get(field.getName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
