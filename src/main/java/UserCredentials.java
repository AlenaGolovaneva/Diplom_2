public class UserCredentials {
    public final String email;
    public final String password;

    public UserCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static UserCredentials getUserCredentials(User user){
        return new UserCredentials(user.email, user.password);
    }

    public static UserCredentials getUserCredentialsIsNotCorrect(){
        final String email = "AlenaTest@gmail.ru";
        final String password = "AlenaTest";
        return new UserCredentials(email, password);
    }


}
