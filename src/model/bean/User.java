package model.bean;

/**
 * Created by  Nigel on 2016/4/14.
 */
public class User {

    /**
     * 用户基本信息
     * name,nickname,email,pass,photo,
     */
    //每次登陆成功，在session里面添加一个user，其他的所有页面都可以访问到用户的信息
    private int id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String tel;

    public User() {

        this.id = 0;
        this.name = "";
        this.nickname = "";
        this.email = "";
        this.password = "";
    }

    public User(int id, String name, String nickname, String email, String password, String tel) {

        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNickname() {

        return nickname;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
