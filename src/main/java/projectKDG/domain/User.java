package projectKDG.domain;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user_data")
public class User {
    @Id
    @SequenceGenerator(
            name = "sequence_user",
            sequenceName = "sequence_user" ,
            allocationSize =   1

    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "sequence_user"
    )
    @Column(
            name = "user_id"
    )
    private int userID;

    @Column(
            name = "user_name"
    )
    private String userName;

    @Column(
            name = "age"
    )
    private int age;

    @Column(
            name = "password"
    )
    private String password;
    @Column(
            name = "email"
    )
    private String email;

    @Column(
            name = "phone"
    )
    private String phone;

    public User(String phone, String email, String password, int age, String userName) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.age = age;
        this.userName = userName;
    }

    public User() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}