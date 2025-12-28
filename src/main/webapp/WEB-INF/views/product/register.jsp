<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e521fcb93dd80c8fe95035867771d15c&libraries=services"></script>

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<style>
    .register-wrapper {
        background-color: #f8f9fa;
        padding: 50px 0;
        min-height: 80vh;
    }

    .register-card {
        max-width: 550px;
        margin: 0 auto;
        background: #fff;
        padding: 40px;
        border-radius: 15px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.08);
    }

    .register-card h1 {
        font-size: 24px;
        font-weight: 800;
        color: #ff8a3d;
        text-align: center;
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
    }

    .form-group label {
        font-weight: 600;
        margin-bottom: 8px;
        color: #444;
        font-size: 14px;
    }

    .form-group input[type="text"],
    .form-group input[type="number"],
    .form-group textarea {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
    }
    .address-flex {
        display: flex;
        gap: 8px;
    }

    .btn-search {
        white-space: nowrap;
        background-color: #333;
        color: #fff;
        border: none;
        padding: 0 15px;
        border-radius: 8px;
        cursor: pointer;
        font-size: 13px;
    }

    
    .button-group {
        display: flex;
        gap: 10px;
        margin-top: 30px;
    }

    .btn-submit {
        flex: 2;
        background-color: #ff8a3d;
        color: white;
        border: none;
        padding: 15px;
        border-radius: 8px;
        font-weight: bold;
        font-size: 16px;
        cursor: pointer;
        transition: 0.3s;
    }

    .btn-submit:hover { background-color: #e07934; }

    .btn-cancel {
        flex: 1;
        background-color: #eee;
        color: #666;
        border: none;
        padding: 15px;
        border-radius: 8px;
        cursor: pointer;
    }
</style>

<div class="register-wrapper">
    <div class="register-card">
        <h1>상품 등록</h1>

        <form id="productForm" action="/product/register" method="post" enctype="multipart/form-data">
            
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            
            <div class="form-group">
                <label for="title">상품명</label>
                <input type="text" id="title" name="title" placeholder="상품 이름을 입력하세요" required>
            </div>

            <div class="form-group">
                <label for="price">판매 가격 (원)</label>
                <input type="number" id="price" name="price" placeholder="숫자만 입력" required min="0">
            </div>

            <div class="form-group">
                <label for="description">설명</label>
                <textarea id="description" name="description" rows="5" placeholder="상품 상태, 사용 기간 등을 적어주세요" required></textarea>
            </div>

            <div class="form-group">
                <label>거래 희망 지역</label>
                <div class="address-flex">
                    <input type="text" id="location" name="location" readonly required placeholder="우측 버튼으로 주소 검색">
                    <button type="button" class="btn-search" onclick="searchAddress()">주소 찾기</button>
                </div>
            </div>

            <input type="hidden" id="lat" name="lat">
            <input type="hidden" id="lon" name="lon">
            <input type="hidden" name="status" value="0"> 
            
            <div class="form-group">
                <label>상품 이미지</label>
                <input type="file" name="uploadFiles" accept="image/*" multiple>
            </div>

            <div class="button-group">
                <button type="submit" class="btn-submit">등록하기</button>
                <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</div>

<script>
    var geocoder;
    kakao.maps.load(function() {
        geocoder = new kakao.maps.services.Geocoder();
    });

    function searchAddress() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address;
                document.getElementById('location').value = addr;
                geocoder.addressSearch(addr, function(results, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        document.getElementById('lat').value = results[0].y;
                        document.getElementById('lon').value = results[0].x;
                    }
                });
            }
        }).open();
    }

    document.getElementById("productForm").addEventListener("submit", function(e){
        if(!document.getElementById('lat').value){
            e.preventDefault();
            alert("주소 검색을 완료해주세요.");
        }
    });
</script>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />