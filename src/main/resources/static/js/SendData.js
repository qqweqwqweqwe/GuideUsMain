

async function sendData() {

    if (destination==="" || departure===""){
        alert("출발지와 도착지를 선택하세요")
        return
    }
    var dep=[departure,PlaceDict[departure][0],PlaceDict[departure][1]]
    var des=[destination,PlaceDict[destination][0],PlaceDict[destination][1]]

    delete PlaceDict[departure]
    delete PlaceDict[destination]
    const trans = [];

    for (const key in PlaceDict) {
        if (PlaceDict.hasOwnProperty(key)) {
            trans.push([key, PlaceDict[key][0], PlaceDict[key][1]]);
        }
    }
    var data={
        trans :trans,  // 경유지들
        dep:dep,  // 출발
        des:des   // 도착
    }


    showLoadingModal()

    const response = await fetch("/route/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });


    const result = await response.text();
    window.location.href = "/maps/result/" + result; // 결과 페이지로 이동

}
