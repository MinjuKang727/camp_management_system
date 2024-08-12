package camp;

import camp.display.Viewer;

public class CampManagementApplication {
    public static void main(String[] args) {
        Viewer viewer = new Viewer(3, 2);

        try {
            viewer.displayMainView();
        } catch (Exception e) {
            System.out.println("오류 발생!");
            System.out.print("[ 오류 원인 ]");
            System.out.println(e.getMessage());
            System.out.println("\n프로그램을 종료합니다.");
        }
    }
}

