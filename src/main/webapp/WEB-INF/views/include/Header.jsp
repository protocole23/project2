<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>중고장터</title>
    
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.lineicons.com/4.0/lineicons.css" />

    <style>
        /* 기본 레이아웃 스타일 */
        body { margin: 0; padding: 0; font-family: 'Noto Sans KR', sans-serif; background-color: #f8f9fa; }
        
        /* 헤더 전체 영역 */
        .header { background-color: #ffffff; border-bottom: 1px solid #e9ecef; position: sticky; top: 0; z-index: 1000; }
        
        /* 상단 바 (로고 + 유저메뉴) */
        .header-top { 
            max-width: 1200px; margin: 0 auto; padding: 15px 20px;
            display: flex; justify-content: space-between; align-items: center; 
        }
        .header-top h1 { margin: 0; color: #ff8a3d; font-size: 24px; cursor: pointer; font-weight: 800; }
        
        /* 로그인/마이페이지 버튼 그룹 */
        .top-menu { display: flex; align-items: center; gap: 12px; }
        .top-menu .user-info { font-size: 14px; color: #444; margin-right: 10px; }
        .action-button { 
            background-color: #ff8a3d; color: white; border: none; padding: 8px 16px; 
            border-radius: 6px; font-size: 13px; font-weight: 600; cursor: pointer; transition: 0.3s;
        }
        .action-button:hover { background-color: #e07934; }
        .btn-gray { background-color: #6c757d; }

        /* 내비게이션 바 (메뉴들) */
        .nav-bar { width: 100%; border-top: 1px solid #f1f3f5; background-color: #fff; }
        .nav-bar ul { 
            max-width: 1200px; margin: 0 auto; padding: 0 20px;
            list-style: none; display: flex; justify-content: flex-start; gap: 5px; 
        }
        .nav-bar li form { margin: 0; }
        .nav-bar button { 
            background: none; border: none; padding: 18px 20px; font-size: 15px; 
            font-weight: 500; color: #333; cursor: pointer; position: relative; transition: 0.2s;
        }
        .nav-bar button:hover { color: #ff8a3d; }
        .nav-bar button:after {
            content: ''; position: absolute; bottom: 0; left: 0; width: 0; height: 3px; 
            background-color: #ff8a3d; transition: 0.3s;
        }
        .nav-bar button:hover:after { width: 100%; }

        /* 콘텐츠 영역 시작점 */
        #contents { max-width: 1200px; margin: 40px auto; padding: 0 20px; min-height: 700px; }
    </style>
    
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
</head>
<body>

    <header class="header">
        <div class="header-top">
            <h1 onclick="location.href='/'">중고장터</h1>
            
            <div class="top-menu">
                <c:choose>
                    <c:when test="${empty sessionScope.userName}">
                        <form action="/user/login" method="GET" style="display:inline;">
                            <button type="submit" class="action-button">로그인</button>
                        </form>
                        <form action="/user/join" method="GET" style="display:inline;">
                            <button type="submit" class="action-button btn-gray">회원가입</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <span class="user-info"><strong>${sessionScope.userName}</strong>님 환영합니다</span>
                        <form action="/user/mypage" method="GET" style="display:inline;">
                            <button type="submit" class="action-button">마이페이지</button>
                        </form>
                        <form action="/user/logout" method="GET" style="display:inline;">
                            <button type="submit" class="action-button btn-gray" style="background:#adb5bd;">로그아웃</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <nav class="nav-bar">
            <ul>
                <li><form action="/" method="GET"><button type="submit">홈</button></form></li>
                <li><form action="/product/productlist" method="GET"><button type="submit">상품 목록</button></form></li>
                <li><form action="/notice/list" method="GET"><button type="submit">공지사항</button></form></li>
                <li><form action="/chat/rooms" method="GET"><button type="submit">채팅</button></form></li>
                <li><form action="/order/list" method="GET"><button type="submit">거래내역</button></form></li>
                <li><form action="/report/write" method="GET"><button type="submit">신고하기</button></form></li>
                <li><form action="/wallet/info" method="GET"><button type="submit">내 지갑</button></form></li>
            </ul>
        </nav>
    </header>

    <div id="contents">