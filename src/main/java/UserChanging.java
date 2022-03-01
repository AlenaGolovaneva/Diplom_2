import org.apache.commons.lang3.RandomStringUtils;

public class UserChanging {
    public final String email;
    public final String name;
//    public final String authorization;

    public UserChanging(String email, String name){
        this.email = email;
        this.name = name;
//        this.authorization = authorization;
    }

    public static UserChanging getChangingUserData(){
        String email = RandomStringUtils.random(7) +"@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(7);
        return new UserChanging(email, name);
    }
}
