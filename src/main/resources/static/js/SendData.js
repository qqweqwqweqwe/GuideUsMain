

function sendData() {

    if (destination==="" || departure===""){
        alert("출발지와 도착지를 선택하세요")
        return
    }
    showLoadingModal()
    var dep=[departure,PlaceDict[departure][0],PlaceDict[departure][1]]
    var des=[destination,PlaceDict[destination][0],PlaceDict[destination][1]]

    delete PlaceDict[departure]
    delete PlaceDict[destination]

    var data={
        PlaceDict :PlaceDict,  // 경유지들
        dep:dep,  // 출발
        des:des   // 도착
    }



    // AJAX를 사용하여 HTTP POST 요청 보내기
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/sendDataToSpring", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    // 여기서 로딩창 띄어주고

    xhr.onreadystatechange = function () {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        // 서버에서 처리한 결과를 받아와서 처리
        hideLoadingModal()
        const response = JSON.parse(xhr.responseText);
        console.log(response.message)
        window.location.href="/result"


        ; // 스프링에서 반환한 결과 출력
      }
    };

    console.log(JSON.stringify(data))

    xhr.send(JSON.stringify(data));
}
