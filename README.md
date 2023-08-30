Apple Market
===

내일배움캠프 숙련주차 개인과제

Android SDK 33 사용

- 몇 가지 기능에서 버전 문제 발생하여 대응하는 코드가 추가되어있음



### 주요 기능
#### 레벨 - 1

1. 더미 데이터를 활용하여 리사이클러 뷰에 데이터를 바인딩 시키기
    - 데이터 전용 클래스를 생성하여 더미데이터 관리 - ProductsData class

2. 뒤로가기(BACK)버튼 클릭시 종료 다이얼로그를 띄우기
    - API 33(TIRAMISU) 버전 이상은 뒤로가기 버튼 클릭 시 불리는 콜백함수인 onBackPressed가 deprecated되어 onBackPressedDispatcher 적용, 33이하 버전 대응도 위해 onBackPressed도 구현해 놓음

3. 상단 종모양 아이콘을 누르면 Notification을 생성
    - ProductNotification class에서 notification 채널(API 26 이상 필수) 과 notification 실행 함수를 만들어 notification을 구현 (API 33 이상에서는 notification 시 권한 설정이 필수라 권한 요청 로직도 작성)

4. 상품 상세페이지 이동시 intent로 객체를 전달 (Parcelize 사용)
    - kotlin-parcelize 플러그인을 통하여 간결하게 구현


#### 레벨 - 2
1. 메인화면에서 전달받은 데이터로 판매자, 주소, 아이템, 글내용, 가격등을 화면에 표시하고 하단 가격표시 레이아웃을 제외하고 전체화면은 스크롤
    - Constraint Layout과 ScrollView를 통해서 최대한 비슷하게 구현
    - Constraint Layout을 구성할 때, 하단 가격 표시 레이아웃을 고정시켜놓고, 그 윗 영역에만 ScrollView를 적용


#### 선택과제
1. 스크롤 상단이동
    - RecyclerView의 OnScrollListener의 OnScrolled메서드를 통해 제어
    - OnScrolled 메서드 안에서 ```if(RecyclerView.canScrollVertically(-1))```를 통해 버튼의 유무를 조정

2.  리사이클러뷰 상품 삭제하기
    - Adapter의 ViewHolder에서 setOnLongClickListener를 통해 클릭 리스너 구현
    - Adapter에서 LongClick 됐을 때 불릴, removeItem메서드 구현 - 특정 position에 있는 데이터를 삭제하고 notifyDataRemoved(position)을 통해 리사이클러뷰를 업데이트

3. 관심 등록하기
    - ```registerForAcitivityResult```을 통해 데이터 관리
    - 메인으로 나오면 ```registerForAcitivityResult```로 구현해 놓은 activityLauncher에서 id와 좋아요 여부(T, F)를 받고 RecyclerView Adapter로 보내 리사이클러 뷰를 업데이트 시킴


[과제 정리](https://aaapple.tistory.com/66#article-2--%EC%88%99%EB%A0%A8-%EA%B0%9C%EC%9D%B8-%EA%B3%BC%EC%A0%9C-%EC%A0%95%EB%A6%AC)  
  
[시연 영상](https://tv.kakao.com/channel/4395390/cliplink/440676837)  