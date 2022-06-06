package com.batch.batchstudy.vo;

import java.sql.Timestamp;

public class Member {
    public String id;
    public String name;
    public Timestamp last_login;

    public Member(){}
    public Member(String id, String name, Timestamp last_login) {
        this.id = id;
        this.name = name;
        this.last_login = last_login;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
