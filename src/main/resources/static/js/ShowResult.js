// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다


var mapContainer;
var map ;
async function showResult(){
    const data=await setCore();
    console.log(data[0])
    console.log(data[1])
    var routepath=data[2]
    mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(data[1], data[0]), // 지도의 중심좌표
            level: 6 // 지도의 확대 레벨
        };
    map = new kakao.maps.Map(mapContainer, mapOption);
    var polyline = new kakao.maps.Polyline({
        map: map,
        path: routepath,
        strokeWeight: 5,
        strokeColor: '#4B89DC',
        strokeOpacity: 0.8,
        strokeStyle: 'solid'
    });
    polyline.setMap(map)
}

async function showMarkers(data){
    // 여기서 url로 부터 requ
    const url = window.location.href;
    const requestId = url.split('/').pop();

    const response = await fetch("/route/calculate/" + requestId)
    const responsejson=await response.json()
    const key = responsejson.key
    const places = responsejson.value

console.log(ket)
    const placeArray = JSON.parse(places)

    for(var i=0; i<placeArray.length; i++){
        const place = placeArray[i]
        const x =parseFloat(place[1])
        const y = parseFloat(place[2])
        var markerPosition  = new kakao.maps.LatLng(y, x);
        // 마커를 생성합니다

        var marker = addMarker(markerPosition, i)


        var iwContent = `<div style=padding:5px;> " "</div>` // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwPosition = new kakao.maps.LatLng(y, x); //인포윈도우 표시 위치입니다

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            position : iwPosition,
            content : iwContent
        });

        // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
        infowindow.open(map, marker);

    }

}


showResult().then(()=>{
//showMarkers()
})



async function setCore(){
    var globalx=0;
    var globaly=0;
    var routepath=[]
    const url = window.location.href;
    const requestId = url.split('/').pop();
    const response = await fetch("/route/calculate/" + requestId)

    const data=await response.json()
    const roads = JSON.parse(data.value)
    console.log(roads)
    for( var i=0; i<roads.length; i++){
        roads[i][0] = parseFloat(roads[i][0])
        roads[i][1] = parseFloat(roads[i][1])

        globalx+=roads[i][0]
        globaly+=roads[i][1]
        var newLatLng = new kakao.maps.LatLng(roads[i][1], roads[i][0]);
        routepath.push(newLatLng)
    }



    globalx=globalx/roads.length
    globaly=globaly/roads.length

    return [globalx,globaly,routepath]

}


function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다

    return marker;
}
