import org.apache.commons.lang3.RandomStringUtils;

public class User {
    public final String email;
    public final String password;
    public final String name;

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom(){
        final String email = RandomStringUtils.random(7) +"@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(7);
        final String name = RandomStringUtils.randomAlphabetic(7);
        return new User(email, password, name);
    }

    public static User getEmptyPassword(){
        final String email = RandomStringUtils.random(7) +"@yandex.ru";
        final String password = "";
        final String name = RandomStringUtils.randomAlphabetic(7);
        return new User(email, password, name);
    }

}
