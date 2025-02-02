// 선택한 장소들 옆에 리스트로 뜨게 해주는 파일
departure=""
destination=""

function Showlist(PD){
    var sw=document.getElementById("search_wrap");
    sw.innerHTML=""
    for (var key in PD){

        var newdiv=document.createElement("li")
        newdiv.classList.add('point')
        newdiv.innerHTML='<h5>'+ key+ '</h5>'

        // 출발지 버튼
        if(key===departure){
            newdiv.innerHTML += '<button style= background-color:red onclick="Makedepa(\'' + key + '\')">출발지</button>';
        }
        else{
            newdiv.innerHTML += '<button onclick="Makedepa(\'' + key + '\')">출발지</button>';
        }

        // 도착지 버튼
        if(key===destination){
            newdiv.innerHTML += '<button style= background-color:green onclick="Makedest(\'' + key + '\')">도착지</button>';
        }
        else{
            newdiv.innerHTML += '<button onclick="Makedest(\'' + key + '\')">도착지</button>';
        }
        newdiv.innerHTML += '<button class="delbutton" onclick="Deleteitem(\'' + key + '\')">삭제</button>'; // 삭제 버튼에 onclick 이벤트 추가
        newdiv.innerHTML+='<span style="display:none;">'+PD[key]+'</span>'
        sw.appendChild(newdiv)
    }
    sw.innerHTML+="<button onclick='sendData()'> ㄱㄱ </button>"


}

function Deleteitem(key){
    if(key===departure){
        departure=""
    }
    if(key===destination){
        destination=""
    }
    delete PlaceDict[key]
    Showlist(PlaceDict)
}

function Makedepa(key){  // 출발장소로 정하는 함ㅁ수


    if (departure===""){
        departure=key
    }
    else{
        if(key===departure){
            var result =window.confirm("출발지를 취소하시겠습니까?")
            if(result){
                alert("출발지가 취소되었습니다")
                departure=""
            }

        }

        else if (departure!==key){
            var result=window.confirm("출발지를 변경하시겠습니까?")
            if (result){
                alert("출발지가 변경되었습니다")
                departure=key
            }
        }

    }
    Showlist(PlaceDict)
}

function Makedest(key){  // 도착 장소로 정하는 함수

    if (destination===""){
        destination=key
    }
    else{
        if(key===destination){
            var result =window.confirm("도착지를 취소하시겠습니까?")
            if(result){
                alert("도착지가 취소되었습니다")
                destination=""
            }
        }

        else if (destination!==key){
            var result=window.confirm("도착지를 변경하시겠습니까?")
            if (result){
                alert("도착지가 변경되었습니다")
                destination=key
            }
        }

    }
    Showlist(PlaceDict)
}



