package interface_adapter.navigation;

public class NavigationController {
    private NavigationViewModel navigationViewModel;
    
    public NavigationController(NavigationViewModel navigationViewModel) {
        this.navigationViewModel = navigationViewModel;
    }
    
    public void execute(String targetPage, String username) {
        navigationViewModel.setCurrentPage(targetPage);
        navigationViewModel.setUsername(username);
    }
}

