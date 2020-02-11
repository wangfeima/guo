package common.utils.collection;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    
	public static boolean isEmpty(Collection<?> collection) {
		return isNull(collection) || collection.size() < 1;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.size() < 1;
	}

	public static boolean isEmpty(Object object) {
		if (object instanceof Collection) {
			return isEmpty((Collection<?>) object);
		} else if (object instanceof Map) {
			return isEmpty((Map<?, ?>) object);
		}
		return isNull(object) || "".equals(object);
	}

	public static boolean isEmpty(Object[] object) {
		return isNull(object) || object.length < 1;
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

	public static boolean isNotEmpty(Object[] object) {
		return !isEmpty(object);
	}

	private static boolean isNull(Object object) {
		return object == null;
	}
	
	/**
	   * 对象是否为数组对象
	   *
	   * @param obj 对象
	   * @return 是否为数组对象，如果为{@code null} 返回false
	   */
	  public static boolean isArray(Object obj) {
	      if (null == obj) {
	          return false;
	      }
	      return obj.getClass().isArray();
	  }

}
