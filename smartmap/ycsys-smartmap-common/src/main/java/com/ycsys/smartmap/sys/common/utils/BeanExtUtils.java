package com.ycsys.smartmap.sys.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 对象工具类，对org.apache.commons.BeanUtils 的扩展
 * 
 * @author 
 * @date 2016年11月17日
 */
public class BeanExtUtils {

	private static final Logger log = Logger.getLogger(BeanExtUtils.class);

	private static DefaultResolver resolver = new DefaultResolver();

	/**
	 * 复制对象属性
	 * 
	 * @param dest
	 *            目的对象
	 * @param orig
	 *            源对象
	 * @param ignoreArray
	 *            是否忽略对象数组
	 * @param ignoreNull
	 *            是否忽略源对象中的空属性
	 * @param ignoreFields
	 *            不需用复制的属性
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void copyProperties(Object dest, Object orig,
			boolean ignoreArray, boolean ignoreNull, String[] ignoreFields)
			throws IllegalAccessException, InvocationTargetException {
		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);

					if (next(name, value, ignoreArray, ignoreNull, ignoreFields))
						continue;

					getBeanUtilsBean().copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (getPropertyUtils().isWriteable(dest, name)) {
					getBeanUtilsBean().copyProperty(dest, name,
							entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = getPropertyUtils().getSimpleProperty(
								orig, name);

						if (!next(name, value, ignoreArray, ignoreNull,
								ignoreFields))
							continue;

						getBeanUtilsBean().copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}

	/**
	 * 复制对象（忽略源对象中属性值为空的字段）
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyNotNullProperties(Object dest, Object orig) {
		try {
			copyProperties(dest, orig, false, true, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将Map中的数据转换成对象(支持泛型，暂只支持非Map、数组、集合类型)
	 * 
	 * @param <T>
	 * @param datas
	 * @param modelClass
	 * @return
	 */
	public static <T> T assignFromMap(Map<String, Object> datas,
			Class<T> modelClass) {
		// valid paramters
		if (datas == null || datas.isEmpty() || modelClass == null)
			return null;

		try {
			T obj = modelClass.newInstance();

			for (Iterator<Map.Entry<String, Object>> it = datas.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, Object> kv = it.next();
				String name = kv.getKey();
				Object value = kv.getValue();

				if (StringUtils.isEmpty(name))
					continue;

				Object _tempObj = obj;

				boolean _exist = true;

				while (resolver.hasNested(name)) {
					String _tempName = resolver.next(name);
					System.out.println(_tempName);

					try {

						if (getPropertyUtils().isReadable(_tempObj, _tempName)
								&& getPropertyUtils().isWriteable(_tempObj,
										_tempName)) {
							PropertyDescriptor descriptor = getPropertyUtils()
									.getPropertyDescriptor(obj, _tempName);
							Object _nestedObj = descriptor.getReadMethod()
									.invoke(_tempObj, null);

							if (_nestedObj == null) { // 创建一个空实体
								// 判断对象，看是否 泛型参数
								_nestedObj = descriptor.getPropertyType()
										.newInstance();
								descriptor.getWriteMethod().invoke(_tempObj,
										_nestedObj);
							}

							_tempObj = _nestedObj;

						} else {
							// name = resolver.remove(name);
							_exist = false;
							break;
						}
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						// name = resolver.remove(name);
						break;
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
						// name = resolver.remove(name);
						break;
					}

					name = resolver.remove(name);

				}

				if (_exist) {
					// 最后一级的属性，设置
					try {
						// getPropertyUtils().setSimpleProperty(_tempObj, name,
						// getBeanUtilsBean().getConvertUtils().convert(value,
						// targetType));
						// 设置属性team.name, 此PropertyDescriptor 为 team对象的name属性
						// if(value != null && StringUtils.isNotEmpty(
						// (String)value )) {
						if (value != null) {
							setSimpleProperty(_tempObj, name, value);
						}

					} catch (InvocationTargetException e) {
						// e.printStackTrace(); ignore
					} catch (NoSuchMethodException e) {
						// e.printStackTrace(); // ignore
					}
				}

			}

			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException("BeanExtUtils.assignFromMap时报错.", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("BeanExtUtils.assignFromMap时报错.", e);
		}

	}

	/**
	 * 将Map中的数据转换成对象(支持泛型，暂只支持非Map、数组、集合类型)
	 * 
	 * @param <T>
	 * @param datas
	 * @param modelClass
	 * @return
	 */
	public static <T> T assignFromMap1(Map<String, Object> datas,
			Class<T> modelClass) {
		// valid paramters
		if (datas == null || datas.isEmpty() || modelClass == null)
			return null;

		try {
			T obj = modelClass.newInstance();

			for (Iterator<Map.Entry<String, Object>> it = datas.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, Object> kv = it.next();
				String name = kv.getKey();
				Object value = kv.getValue();

				if (StringUtils.isEmpty(name))
					continue;

				Object _tempObj = obj;
				while (resolver.hasNested(name)) {
					String _tempName = resolver.next(name);

					try {
						if (getPropertyUtils().isReadable(_tempObj, _tempName)
								&& getPropertyUtils().isWriteable(_tempObj,
										_tempName)) {
							PropertyDescriptor descriptor = getPropertyUtils()
									.getPropertyDescriptor(obj, _tempName);
							Object _nestedObj = descriptor.getReadMethod()
									.invoke(_tempObj, null);

							if (_nestedObj == null) { // 创建一个空实体
								// 判断对象，看是否 泛型参数
								_nestedObj = descriptor.getPropertyType()
										.newInstance();
								descriptor.getWriteMethod().invoke(_tempObj,
										_nestedObj);
							}

							_tempObj = _nestedObj;

						}
					} catch (InvocationTargetException e) {
						// e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// e.printStackTrace();
					}

					name = resolver.remove(name);

				}
				// 最后一级的属性，设置
				try {
					// getPropertyUtils().setSimpleProperty(_tempObj, name,
					// getBeanUtilsBean().getConvertUtils().convert(value,
					// targetType));
					// 设置属性team.name, 此PropertyDescriptor 为 team对象的name属性
					if (value != null && StringUtils.isNotEmpty((String) value)) {
						setSimpleProperty(_tempObj, name, value);
					}

				} catch (InvocationTargetException e) {
					// e.printStackTrace(); ignore
				} catch (NoSuchMethodException e) {
					// e.printStackTrace(); // ignore
				}

			}

			return obj;
		} catch (InstantiationException e) {
			throw new RuntimeException("BeanExtUtils.assignFromMap时报错.", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("BeanExtUtils.assignFromMap时报错.", e);
		}

	}

	private static BeanUtilsBean getBeanUtilsBean() {
		return BeanUtilsBean.getInstance();
	}

	private static PropertyUtilsBean getPropertyUtils() {
		return getBeanUtilsBean().getPropertyUtils();
	}

	private static boolean next(String name, Object value, boolean ignoreArray,
			boolean ignoreNull, String[] ignoreFields) {

		if (ignoreFields != null && ignoreFields.length > 0) {
			if (org.apache.commons.lang.ArrayUtils.contains(ignoreFields, name)) {
				return false;
			}

		}

		if (ignoreNull && value == null) {
			return false;
		}

		if (value == null)
			return true;

		if (ignoreArray) { // 如果忽略集合类属性（包括 数组、Collection、Map）
			Class type = value.getClass();
			if (type.isArray() || Collection.class.isAssignableFrom(type)
					|| Map.class.isAssignableFrom(type)) {
				return false;
			}
		}

		return true;
	}

	/** ****************************** 测试方法 **************************** */
	public static void main(String[] args) {
		// Map<String, Object> datas = new HashMap<String, Object>();
		// datas.put("stuName", "dingw");
		// datas.put("stuNo", "123456");
		// datas.put("team.id", "teamId123");
		// datas.put("team.teamName", "j1011");
		//
		// Student stu = BeanExtUtils.assignFromMap(datas, Student.class);
		//
		// System.out.println(stu);

	}

	// 设置简单属性值
	private static void setSimpleProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (bean == null)
			throw new IllegalArgumentException("No bean specified.");

		if (name == null) {
			throw new IllegalArgumentException(
					"No name specified for bean class '" + bean.getClass()
							+ "'");
		}

		PropertyDescriptor descriptor = getPropertyUtils()
				.getPropertyDescriptor(bean, name);

		if (descriptor != null && descriptor.getWriteMethod() != null) {
			getPropertyUtils().setSimpleProperty(
					bean,
					name,
					getBeanUtilsBean().getConvertUtils().convert(
							(String) value, descriptor.getPropertyType()));

		}

	}

	/*********************************** 私有类 *******************/
	private static class DefaultResolver {

		private static final char NESTED = '.';
		private static final char MAPPED_START = '(';
		private static final char MAPPED_END = ')';
		private static final char INDEXED_START = '[';
		private static final char INDEXED_END = ']';

		/**
		 * Default Constructor.
		 */
		public DefaultResolver() {
		}

		/**
		 * Return the index value from the property expression or -1.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The index value or -1 if the property is not indexed
		 * @throws IllegalArgumentException
		 *             If the indexed property is illegally formed or has an
		 *             invalid (non-numeric) value.
		 */
		public int getIndex(String expression) {
			if (expression == null || expression.length() == 0) {
				return -1;
			}
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (c == NESTED || c == MAPPED_START) {
					return -1;
				} else if (c == INDEXED_START) {
					int end = expression.indexOf(INDEXED_END, i);
					if (end < 0) {
						throw new IllegalArgumentException(
								"Missing End Delimiter");
					}
					String value = expression.substring(i + 1, end);
					if (value.length() == 0) {
						throw new IllegalArgumentException("No Index Value");
					}
					int index = 0;
					try {
						index = Integer.parseInt(value, 10);
					} catch (Exception e) {
						throw new IllegalArgumentException(
								"Invalid index value '" + value + "'");
					}
					return index;
				}
			}
			return -1;
		}

		/**
		 * Return the map key from the property expression or <code>null</code>.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The index value
		 * @throws IllegalArgumentException
		 *             If the mapped property is illegally formed.
		 */
		public String getKey(String expression) {
			if (expression == null || expression.length() == 0) {
				return null;
			}
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (c == NESTED || c == INDEXED_START) {
					return null;
				} else if (c == MAPPED_START) {
					int end = expression.indexOf(MAPPED_END, i);
					if (end < 0) {
						throw new IllegalArgumentException(
								"Missing End Delimiter");
					}
					return expression.substring(i + 1, end);
				}
			}
			return null;
		}

		/**
		 * Return the property name from the property expression.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The property name
		 */
		public String getProperty(String expression) {
			if (expression == null || expression.length() == 0) {
				return expression;
			}
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (c == NESTED) {
					return expression.substring(0, i);
				} else if (c == MAPPED_START || c == INDEXED_START) {
					return expression.substring(0, i);
				}
			}
			return expression;
		}

		/**
		 * Indicates whether or not the expression contains nested property
		 * expressions or not.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The next property expression
		 */
		public boolean hasNested(String expression) {
			if (expression == null || expression.length() == 0) {
				return false;
			} else {
				return (remove(expression) != null);
			}
		}

		/**
		 * Indicate whether the expression is for an indexed property or not.
		 * 
		 * @param expression
		 *            The property expression
		 * @return <code>true</code> if the expresion is indexed, otherwise
		 *         <code>false</code>
		 */
		public boolean isIndexed(String expression) {
			if (expression == null || expression.length() == 0) {
				return false;
			}
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (c == NESTED || c == MAPPED_START) {
					return false;
				} else if (c == INDEXED_START) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Indicate whether the expression is for a mapped property or not.
		 * 
		 * @param expression
		 *            The property expression
		 * @return <code>true</code> if the expresion is mapped, otherwise
		 *         <code>false</code>
		 */
		public boolean isMapped(String expression) {
			if (expression == null || expression.length() == 0) {
				return false;
			}
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (c == NESTED || c == INDEXED_START) {
					return false;
				} else if (c == MAPPED_START) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Extract the next property expression from the current expression.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The next property expression
		 */
		public String next(String expression) {
			if (expression == null || expression.length() == 0) {
				return null;
			}
			boolean indexed = false;
			boolean mapped = false;
			for (int i = 0; i < expression.length(); i++) {
				char c = expression.charAt(i);
				if (indexed) {
					if (c == INDEXED_END) {
						return expression.substring(0, i + 1);
					}
				} else if (mapped) {
					if (c == MAPPED_END) {
						return expression.substring(0, i + 1);
					}
				} else {
					if (c == NESTED) {
						return expression.substring(0, i);
					} else if (c == MAPPED_START) {
						mapped = true;
					} else if (c == INDEXED_START) {
						indexed = true;
					}
				}
			}
			return expression;
		}

		/**
		 * Remove the last property expresson from the current expression.
		 * 
		 * @param expression
		 *            The property expression
		 * @return The new expression value, with first property expression
		 *         removed - null if there are no more expressions
		 */
		public String remove(String expression) {
			if (expression == null || expression.length() == 0) {
				return null;
			}
			String property = next(expression);
			if (expression.length() == property.length()) {
				return null;
			}
			int start = property.length();
			if (expression.charAt(start) == NESTED) {
				start++;
			}
			return expression.substring(start);
		}
	}

}
