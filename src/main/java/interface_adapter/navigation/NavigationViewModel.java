package interface_adapter.navigation;

import interface_adapter.ViewModel;

public class NavigationViewModel extends ViewModel {
    private String currentPage;
    private String username;
    
    public NavigationViewModel() {
        super("navigation");
    }
    
    public String getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
        firePropertyChanged();
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
        firePropertyChanged();
    }
}

