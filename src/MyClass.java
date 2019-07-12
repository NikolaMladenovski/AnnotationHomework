import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class User{
    @MyAnnotation(emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    public String email;
    User(String email){
        this.email=email;
    }
}

public class MyClass {
    static class MyAnnotationHanlder {
        public void handle(Object ob) throws Exception {
            Field[] fields = ob.getClass().getFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(MyAnnotation.class)) {
                    MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
                    String emailPattern = myAnnotation.emailPattern();
                    Pattern p = Pattern.compile(emailPattern);
                    Matcher m = p.matcher(field.get(ob).toString());
                    try {
                        if(!m.matches()){
                            throw new Exception("Enter valid email");
                        }else{
                            System.out.println("Valid Email");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                }
            }
        }

    }
    public static void main(String[] args) throws Exception {
        User user = new User("nikola@yahoo.com");
        MyAnnotationHanlder checkEmail = new MyAnnotationHanlder();

        checkEmail.handle(user);
    }
}
