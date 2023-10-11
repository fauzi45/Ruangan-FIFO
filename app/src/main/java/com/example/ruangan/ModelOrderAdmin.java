package com.example.ruangan;

public class ModelOrderAdmin {
    String orderUserId;
    String ruanganId;
    String orderDate;
    String orderId;
    String orderStartTime;
    String orderFinishTime;
    String orderPhone;

    public ModelOrderAdmin() {
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getRuanganId() {
        return ruanganId;
    }

    public void setRuanganId(String ruanganId) {
        this.ruanganId = ruanganId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public ModelOrderAdmin(String orderUserId, String ruanganId, String orderDate, String orderId, String orderStartTime, String orderFinishTime, String orderPhone, String orderDesc, String orderStatus) {
        this.orderUserId = orderUserId;
        this.ruanganId = ruanganId;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderStartTime = orderStartTime;
        this.orderFinishTime = orderFinishTime;
        this.orderPhone = orderPhone;
        this.orderDesc = orderDesc;
        this.orderStatus = orderStatus;
    }

    String orderDesc;
    String orderStatus;
}
