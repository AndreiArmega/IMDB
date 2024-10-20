package Users;
import java.time.format.DateTimeFormatter;

public class Credentials {

    private String email;
    private String password;

    private Credentials(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public static class Builder {
        private String email;
        private String password;


        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }


        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }


        public Credentials build() {
            return new Credentials(this);
        }
    }


    public static String formatCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return java.time.LocalDateTime.now().format(formatter);
    }
}

