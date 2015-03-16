<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <!-- banner STA -->
    <div class="banner-fk">
		<div class="banner-f"><img src="${context }/resources/rabbit/images/banner-3.jpg" alt="" /></div>
	</div>
    <!-- banner End -->
<!-- 主体内容区 STA -->
    <div class="maink">
       	<div class="main">
        	<div class="tour-k">
            	<ul>
            	<c:forEach var="items" items="${artileList}" varStatus="status">
                	<li>
                    	<div class="tour">
                            <div class="tour-t"><a href="${context}/custom/pdetail?id=${items.id}"><img src="${items.img}" alt="" /></a></div>
                            <div class="tour-m"><a href="${context}/custom/pdetail?id=${items.id}">${items.title }</a></div>
                            <div class="tour-b">${items.specialMaketime}</div>
                        </div>
                    </li>
                    </c:forEach>
                	
                </ul>
                <div style="display:none;" class="page"><a href="#">&lt;</a><a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#" class="on">4</a><a href="#">5</a><a href="#">6</a><a href="#">…</a><a href="#">&gt;</a></div>
            </div>
        </div>
    </div>
    <!-- 主体内容区 End -->