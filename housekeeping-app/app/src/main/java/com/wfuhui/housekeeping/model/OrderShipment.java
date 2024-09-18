package com.wfuhui.housekeeping.model;

/**
 * 订单地址
 */
public class OrderShipment {
    // 姓名
    private String contacts;
    // 手机号码
    private String mobile;
    // 省名称
    private String provinceName;
    // 市名称
    private String cityName;
    // 区名称
    private String districtName;
    // 详细地址
    private String address;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
