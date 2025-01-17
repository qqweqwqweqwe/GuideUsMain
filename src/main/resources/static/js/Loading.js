// 로딩창을 보여주는 함수
function showLoadingModal() {
  // 모달 엘리먼트를 생성하고 스타일을 설정
  var modal = document.createElement("div");

  // 모달 내용 추가 (로딩 스피너 등)
  modal.innerHTML = '<div class="loading-overlay"><div class="loading-spinner"></div></div>';

  // 모달을 body에 추가
  document.body.appendChild(modal);
}

// 로딩창을 닫는 함수
function hideLoadingModal() {
  var modal = document.querySelector(".loading-modal");
  if (modal) {
    document.body.removeChild(modal);
  }
}

// 로딩창 보여주기

