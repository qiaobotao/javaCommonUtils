/**
 * 
 */
package com.jinhetech.comunication.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zmm
 * 
 * 2010-8-24
 */
public class ReflectionUtil {

	/**
	 * 得到某个对象的属性
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getPublicField(Object obj, String fieldName) throws Exception { 
		
	    Class ownerClass = obj.getClass();
	    Field field = ownerClass.getField(fieldName);
	    Object property = field.get(obj); // 通过对象得到该属性的实例，如果这个属性是非公有的，这里会报IllegalAccessException。
	    return property;
	}
	
	/**
	 * 得到某个类的静态属性
	 * 
	 * @param className
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getStaticField(String className, String fieldName) 
	            throws Exception {
		
	    Class ownerClass = Class.forName(className);
	    Field field = ownerClass.getField(fieldName);
	    Object property = field.get(ownerClass);
	    return property;
	 }

	/**
	 * 执行某对象的方法
	 * 
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object obj, String methodName, Object[] args) throws Exception {

	    Class ownerClass = obj.getClass();
	    Class[] argsClass = new Class[args.length];
	    for (int i = 0, j = args.length; i < j; i++) {
	        argsClass[i] = args[i].getClass();
	    }
	     Method method = ownerClass.getMethod(methodName, argsClass);
	     return method.invoke(obj, args);
	 }
	
	/**
	 * 执行某对象的方法
	 * 
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object obj, String methodName, Class[] argsClass, Object[] args) throws Exception {

	    Class ownerClass = obj.getClass();

	     Method method = ownerClass.getMethod(methodName, argsClass);
	     return method.invoke(obj, args);
	 }

	/**
	 * 设定对象的属性，该属性必须有set方法
	 * 
	 * @param obj
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public static Object setField(Object obj, String fieldName, Object fieldValue) throws Exception{
		
		String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return invokeMethod(obj, setMethodName, new Object[]{fieldValue});
	}
	
	/**
	 * 取得对象的属性，改属性必须有get方法
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getField(Object obj, String fieldName) throws Exception{
		
		String setMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		return invokeMethod(obj, setMethodName, new Object[]{});
	}
	/**
	 * 执行某个类的静态方法
	 * 
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeStaticMethod(String className, String methodName,
	            Object[] args) throws Exception {
		
	    Class ownerClass = Class.forName(className);
	    Class[] argsClass = new Class[args.length];
	    for (int i = 0, j = args.length; i < j; i++) {
	        argsClass[i] = args[i].getClass();
	    }
	    Method method = ownerClass.getMethod(methodName, argsClass);
	    return method.invoke(null, args);
	 } 

	/**
	 * 新建实例
	 * 
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(String className, Object[] args) throws Exception {
		
	    Class newoneClass = Class.forName(className);
	    Class[] argsClass = new Class[args.length];
	    for (int i = 0, j = args.length; i < j; i++) {
	        argsClass[i] = args[i].getClass();
	    }
	    Constructor cons = newoneClass.getConstructor(argsClass);
	    return cons.newInstance(args);
	 }

	/**
	 * 取得指定对象的属性
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Field[] getFields(Object obj) throws Exception { 

	    Class ownerClass = obj.getClass();
	    Field[] fields = ownerClass.getDeclaredFields();
	    return fields;
	}

	/**
	 * 取得指定类的属性
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Field[] getFields(String className) throws Exception { 

	    Class ownerClass = Class.forName(className);
	    Field[] fields = ownerClass.getDeclaredFields();
	    return fields;
	}
	
	/**
	 * 复制对象的相同名称的属性
	 * 
	 * @param srcObj
	 * @param destObj
	 * @throws Exception
	 */
	public static void copyProperties(Object srcObj, Object destObj) throws Exception{
		
		Field[] srcFields = getFields(srcObj);
		String fieldName;
		for(int i = 0; i < srcFields.length; i++){
			fieldName = srcFields[i].getName();
			try{
				setField(destObj, fieldName, getField(srcObj, fieldName));
			}catch(NoSuchMethodException e){
			}
		}
	}
}
