public class HomePageController{

    public void handleNewGameButton() {
        //start game code
    }

    public void handleLoadGameButton(){
        //load game code
    }

    public void handleExitButton() {
        System.exit(0);
    }
 
    public static void main (String[] args){
        HomePageController controller = new HomePageController();
        HomePage view = new HomePage(controller);
    }
}
