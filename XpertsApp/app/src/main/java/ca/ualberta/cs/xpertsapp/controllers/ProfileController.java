package ca.ualberta.cs.xpertsapp.controllers;

import java.security.InvalidParameterException;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/3/15.
 * Controller for adding and removing friends
 */
public class ProfileController {
    //should probably be an exception
    /**
     * adds friend to local user unless user doesn't exist, then returns null
     * @param email the email of the user to add
     * @return the users added or null if user not found
     */
    public User addFriend(String email){
        User user = MyApplication.getLocalUser();
        User friend;
        try {
            friend = UserManager.sharedManager().getUser(email);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        if (friend == null) {
            return null;
        }
        if (!friend.equals(user) && !user.getFriends().contains(friend)) {
            user.addFriend(friend);
        }
        return friend;
    }
    /**
     * Deletes friend from local user
     * @param friend the user to delete
     */
    public void deleteFriend(User friend) {
        User user = MyApplication.getLocalUser();
        user.removeFriend(friend);
    }
}
