<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e521fcb93dd80c8fe95035867771d15c&libraries=services"></script>
</head>
<body>

    <h1>상품 등록</h1>

    <form id="productForm" action="/product/register" method="post" enctype="multipart/form-data">
        
        <div><label for="title">상품명:</label><input type="text" id="title" name="title" required></div>
        <div><label for="price">가격:</label><input type="number" id="price" name="price" required min="0"></div>
        <div><label for="description">상품 설명:</label><textarea id="description" name="description" required></textarea></div>

        <div>
            <label for="location">거래 희망 지역:</label>
            <input type="text" id="location" name="location" readonly required placeholder="주소 찾기 버튼을 눌러주세요.">
            <button type="button" onclick="searchAddress()">주소 찾기</button>
        </div>

        <input type="hidden" id="lat" name="lat">
		<input type="hidden" id="lon" name="lon">
        
        <input type="hidden" name="status" value="0"> 
        
        <div><input type="file" name="uploadFiles" accept="image/*" multiple></div>

        <button type="submit">등록하기</button>
        <button type="button" onclick="history.back()">취소</button>
    </form>
    
    <script>
    // var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 서비스
    var geocoder;

    // 카카오 맵이 로드된 후 실행되도록 보장 (kakao is not defined 에러 방지)
    kakao.maps.load(function() {
        geocoder = new kakao.maps.services.Geocoder();
        console.log("카카오 맵 Geocoder 초기화 완료");
    });

    function searchAddress() {
        if (typeof daum === 'undefined') {
            alert("주소 찾기 스크립트가 로드되지 않았습니다. 새로고침 해주세요.");
            return;
        }

        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address;
                document.getElementById('location').value = addr;

                geocoder.addressSearch(addr, function(results, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        var result = results[0];
                        document.getElementById('lat').value = result.y;
                        document.getElementById('lon').value = result.x;
                        console.log("지역 설정 완료: " + addr + " (Lat: " + result.y + ", Lon: " + result.x + ")");
                        // 좌표 변환 완료 시 바로 submit 하고 싶으면 주석 해제
                        //document.getElementById("productForm").submit();
                    } else {
                        alert("좌표 변환에 실패했습니다.");
                    }
                });
            }
        }).open();
    }

    document.getElementById("productForm").addEventListener("submit", function(e){
        var lat = document.getElementById('lat').value;
        var lon = document.getElementById('lon').value;
        if(!lat || !lon){
            e.preventDefault(); // submit 막기
            alert("주소 선택 후 잠시 기다려주세요.");
        }
    });
</script>
    
    <jsp:include page="/WEB-INF/views/include/Footer.jsp" />
    
</body>
</html>