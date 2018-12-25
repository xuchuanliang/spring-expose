package com.snail.springbootsource.share.entity;

public class Event {
    private Order order;
    private User user;
    public Event(){}

    public Event(Order order){
        this.order = order;
    }

    public Event(Order order,User user){
        this.order = order;
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
