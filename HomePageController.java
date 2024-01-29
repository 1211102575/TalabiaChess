// Fam Yi Qi

// Handles buttons from home page view
public class HomePageController{

    public void handleNewGameButton() {
        new BoardController();
        
    }

    public void handleLoadGameButton(){
        BoardController controller = new BoardController();
        controller.handleLoadGameButton();
    }

    public void handleExitButton() {
        System.exit(0);
    }
}
