package cmpe.sjsu.food4u;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by manas on 5/7/2018.
 */

public class User {
    private String username;
    private boolean isAdmin;



    public  User() {
        username ="";
        isAdmin=false;
    }

    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
