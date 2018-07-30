package com.example.es.design.prototype;

import java.util.List;

/**
 * Created by Mario on 2018/7/24.
 */
public class PrototypeDemo {

    private static User user = new User();
    public static void main(String[] args){
        user.setAge(1);
        user.setId(1);
        user.setRoom(new Room(1,"BIG HOUSE"));
        user.setName("hahaha");
        try {
            User user1 = user.clone();
            System.out.println(user == user1);
            System.out.println(user.getRoom() == user1.getRoom());
            System.out.println(user.getLists() == user1.getLists());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}


class User implements Cloneable{
    private int id;
    private String name;
    private int age;
    private List<String> lists;
    private Room room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User clone() throws CloneNotSupportedException {
      User clone =  (User) super.clone();
      clone.setRoom(this.getRoom().clone());
      return clone;

    }
}

class Room implements Cloneable{
    private int id;
    private String name;

    public Room(int id,String name){
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Room clone() throws CloneNotSupportedException {
        return (Room) super.clone();
    }
}

