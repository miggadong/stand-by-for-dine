import gui.LoginPanel;
import model.service.DBManager;

import java.sql.Connection;


/*
발표 10분
예약 알림기능 (대기열 3번째정도되면 알림) / 예약독점? (한사람이 모든곳에 거는것) 방지 /
음식 카테고리 별 검색 / 메뉴판 / 음식점 평점,댓글 기능
 */
// TODO : CustomerService.update 전달 & SearchPanel 현재 순서 : 를 가게 공지 : 로 바꾸고 긴급공지를 쓸수있는 기능 추가 (update)
// ppt 형식 자유 + demo 7분안에 줌 화면공유로 발표 -> 돌아다니면서 각 조 demo 직접 실행해보고 순위정함
public class Main {/* 한글 깨질때
System.getProperty("").equals("MS49?") -> MS 949로 맞춰야 한글 안깨짐 UTF-8 x
*/
    public static void main(String[] args) {
        new LoginPanel();
    }
}
