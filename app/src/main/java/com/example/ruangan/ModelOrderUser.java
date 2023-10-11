package com.example.ruangan;

public class ModelOrderUser {
    String orderId;

    public ModelOrderUser(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    String orderUserId;
    String ruanganId;
    String orderDate;

    public String getRuanganId() {
        return ruanganId;
    }

    public void setRuanganId(String ruanganId) {
        this.ruanganId = ruanganId;
    }

    String orderStartTime;

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    String orderFinishTime;
    String orderPhone;
    String orderDesc;
    String orderStatus;


    public ModelOrderUser() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(String orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ModelOrderUser(String orderId, String orderUserId, String ruanganId, String orderDate, String orderStartTime, String orderFinishTime, String orderPhone, String orderDesc, String orderStatus) {
        this.orderId = orderId;
        this.orderUserId = orderUserId;
        this.ruanganId = ruanganId;
        this.orderDate = orderDate;
        this.orderStartTime = orderStartTime;
        this.orderFinishTime = orderFinishTime;
        this.orderPhone = orderPhone;
        this.orderDesc = orderDesc;
        this.orderStatus = orderStatus;
    }

    public ModelOrderUser(String ruanganId, String orderId, String orderDate, String orderStartTime, String orderFinishTime, String orderPhone, String orderDesc, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStartTime = orderStartTime;
        this.orderFinishTime = orderFinishTime;
        this.orderPhone = orderPhone;
        this.orderDesc = orderDesc;
        this.orderStatus = orderStatus;
        this.ruanganId = ruanganId;
    }
}
