package com.example.corona;

/*import androidx.appcompat.app.AppCompatActivity;*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation  {
    private static final String NAME_PATTERN = "^[a-zA-Z]{3,20}$"; //usernameRegexPatern
    private static final String PASSWORD_PATTERN  = "^[a-zA-Z0-9.!@_]{5,20}$";
    //private static final String EMAIL_PATTERN = "^[a-zA-Z0-9@._-]{10,50}$";
    //"^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isCredentialsValid (String credentials) {  //boolean - loginis kintamasis true (1) arba false (0)
        Pattern pattern=Pattern.compile(NAME_PATTERN); //sukuriamos username validacijos taisykles pagal nurodyta sablona
        Matcher matcher=pattern.matcher(credentials);//pagal anksciau sukurtas taisykles palyginami ivesti duomenys
       // boolean isUserNameValid; //grazins true arba false
       return matcher.find(); //matcher.matches(), grazins true arba false
    }
    public static boolean isPasswordValid(String email) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(email);
       // boolean isUserPasswordValid;
       // isUserPasswordValid = matcher.find(); //grazins true arba false
        return matcher.find();//isUserPasswordValid;
        //return matcher.matches();
    }
    public static boolean isEmailValid (String credentials) {
        Pattern pattern=Pattern.compile(EMAIL_PATTERN );
        Matcher matcher=pattern.matcher(credentials);
        return matcher.find();
    }

}
