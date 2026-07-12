package org.example.bean;


import java.util.List;

public class UserContext {

    private static final ThreadLocal<CurrentUser> USER_HOLDER = new ThreadLocal<>();

    public static void set(CurrentUser user) {
        USER_HOLDER.set(user);
    }

    public static CurrentUser get() {
        return USER_HOLDER.get();
    }

    public static void remove() {
        USER_HOLDER.remove();
    }

    public static String getUserId() {
        CurrentUser user = get();
        return user != null ? user.getUserId() : null;
    }

    public static String getUsername() {
        CurrentUser user = get();
        return user != null ? user.getUsername() : null;
    }

    public static List<String> getRoles() {
        CurrentUser user = get();
        return user != null ? user.getRoles() : null;
    }

    public static class CurrentUser {
        private String userId;
        private String username;
        private List<String> roles;

        public CurrentUser(String userId, String username, List<String> roles) {
            this.userId = userId;
            this.username = username;
            this.roles = roles;
        }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public List<String> getRoles() { return roles; }
        public void setRoles(List<String> roles) { this.roles = roles; }
    }
}