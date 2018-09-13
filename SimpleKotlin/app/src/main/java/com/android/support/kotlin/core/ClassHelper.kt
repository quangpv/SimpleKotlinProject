package com.android.support.kotlin.core

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object ClassHelper {

    private val MAX_DEEP = 5
    private val LAYOUT_ID_NOT_FOUND = 0

    /**
     * Get parameter type of object at position
     *
     * @param obj      Any object
     * @param position Position of parameter type in object
     * @return List types of parameter on object
     */
    fun <T> getFirstGenericParameter(obj: Any): Class<T> {
        val types = getParameterTypes(obj)
        if (types!!.isEmpty())
            throw RuntimeException(obj.javaClass.simpleName + " has not first position")
        return types[0] as Class<T>
    }

    /**
     * Get parameter types of object
     *
     * @param obj Any object
     * @return List types of parameter on object
     */
    private fun getParameterTypes(obj: Any): Array<Type>? {
        val objClass = obj.javaClass
        return findParameterTypes(objClass, 0)
    }

    /**
     * Get layout resource Identify
     *
     * @param object Object has annotation [LayoutId]
     * @return Layout resource id [android.support.annotation.LayoutRes]
     */
    fun getLayoutId(`object`: Any): Int {
        val clazz = `object`.javaClass
        val layoutId = findLayoutId(clazz, 0)
        if (layoutId == LAYOUT_ID_NOT_FOUND)
            throw RuntimeException("Not found layout id of " + clazz.simpleName)
        return layoutId
    }

    private fun findParameterTypes(clazz: Class<*>, deep: Int): Array<Type>? {
        val superType = clazz.genericSuperclass
        if (superType is ParameterizedType) {
            val types = superType.actualTypeArguments
            if (types.isNotEmpty())
                return types
        }
        return if (deep > MAX_DEEP) null else findParameterTypes(clazz.superclass, deep + 1)
    }

    private fun findLayoutId(clazz: Class<*>, deep: Int): Int {
        val layoutId = clazz.getAnnotation(LayoutId::class.java)
        if (layoutId == null) {
            if (deep > MAX_DEEP)
                return LAYOUT_ID_NOT_FOUND
            val type = clazz.genericSuperclass
            return if (type !is Class<*>) LAYOUT_ID_NOT_FOUND else findLayoutId(clazz.genericSuperclass as Class<*>, deep + 1)
        }
        return layoutId.value
    }

}
