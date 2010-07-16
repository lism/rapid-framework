package javacommon.util;


import java.util.Map;

import javacommon.base.BaseQuery;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.page.PageRequest;
/**
 * 用于分页组件覆盖的类,新的分页组件覆盖此类的newPageRequest()方法以适合不同的分页创建
 * @author badqiu
 */
public class PageRequestFactory {
    public static final int MAX_PAGE_SIZE = 1000;
    
    static {
        System.out.println("PageRequestFactory.MAX_PAGE_SIZE="+MAX_PAGE_SIZE);
    }
    
    public static PageRequest bindPageRequest(PageRequest pageRequest,HttpServletRequest request,String defaultSortColumns){
        return bindPageRequest(pageRequest, request, defaultSortColumns, BaseQuery.DEFAULT_PAGE_SIZE);
    }
    
    public static PageRequest bindPageRequest(PageRequest pageRequest, HttpServletRequest request,String defaultSortColumns, int defaultPageSize) {
        Map params = WebUtils.getParametersStartingWith(request, "");
        BeanUtils.copyProperties(pageRequest, params);
        
        pageRequest.setPageNumber(getIntParameter(request, "pageNumber", 1));
        pageRequest.setPageSize(getIntParameter(request, "pageSize", defaultPageSize));
        pageRequest.setSortColumns(getStringParameter(request, "sortColumns",defaultSortColumns));
        
        if(pageRequest.getPageSize() > MAX_PAGE_SIZE) {
            pageRequest.setPageSize(MAX_PAGE_SIZE);
        }
        return pageRequest;
    }
    
    static String getStringParameter(HttpServletRequest request,String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    static int getIntParameter(HttpServletRequest request,String paramName,int defaultValue) {
        String value = request.getParameter(paramName);
        return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
    }
}