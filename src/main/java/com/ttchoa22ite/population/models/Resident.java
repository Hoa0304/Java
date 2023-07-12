package com.ttchoa22ite.population.models;



import javafx.beans.property.*;

import java.sql.Date;

public class Resident {

    private IntegerProperty id;
    private StringProperty sshk;
    private StringProperty cccd;
    private StringProperty name;
    private StringProperty NOphone;
    private StringProperty address;
    private StringProperty job;
    private StringProperty sex;
    private StringProperty birt;


    public Resident() {
        this.id = new SimpleIntegerProperty();
        this.sshk = new SimpleStringProperty();
        this.cccd = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.NOphone = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.job = new SimpleStringProperty();
        this.sex = new SimpleStringProperty();
        this.birt = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSshk() {
        return sshk.get();
    }

    public StringProperty sshkProperty() {
        return sshk;
    }

    public void setSshk(String sshk) {
        // Kiểm tra tính hợp lệ của sshk trước khi thiết lập giá trị
        if (isValidSshk(sshk)) {
            this.sshk.set(sshk);
        } else {
            // Xử lý khi sshk không hợp lệ
            throw new IllegalArgumentException("Invalid sshk");
        }
    }

    public String getCccd() {
        return cccd.get();
    }

    public StringProperty cccdProperty() {
        return cccd;
    }

    public void setCccd(String cccd) {
        // Kiểm tra tính hợp lệ của cccd trước khi thiết lập giá trị
        if (isValidCccd(cccd)) {
            this.cccd.set(cccd);
        } else {
            // Xử lý khi cccd không hợp lệ
            throw new IllegalArgumentException("Invalid cccd");
        }

    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNOphone() {
        return NOphone.get();
    }

    public StringProperty NOphoneProperty() {
        return NOphone;
    }

    public void setNOphone(String NOphone) {
        // Kiểm tra tính hợp lệ của NOphone trước khi thiết lập giá trị
        if (isValidPhone(NOphone)) {
            this.NOphone.set(NOphone);
        } else {
            // Xử lý khi NOphone không hợp lệ
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getJob() {
        return job.get();
    }

    public StringProperty jobProperty() {
        return job;
    }

    public void setJob(String job) {
        this.job.set(job);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getBirt() {
        return birt.get();
    }

    public StringProperty birtProperty() {
        return birt;
    }

    public void setBirt(String birt) {
        // Kiểm tra tính hợp lệ của birt trước khi thiết lập giá trị
        if (isValidDate(birt)) {
            this.birt.set(birt);
        } else {
            // Xử lý khi birt không hợp lệ
            throw new IllegalArgumentException("Invalid birthdate");
        }
    }

    // Các phương thức kiểm tra tính hợp lệ của dữ liệu

    private boolean isValidSshk(String sshk) {
        // Kiểm tra các điều kiện hợp lệ cho sshk
        // Ví dụ: độ dài, định dạng, ...
        return true;
    }

    private boolean isValidCccd(String cccd) {
        // Kiểm tra các điều kiện hợp lệ cho cccd
        // Ví dụ: độ dài, định dạng, ...
        return true;
    }

    private boolean isValidPhone(String phone) {
        // Kiểm tra các điều kiện hợp lệ cho số điện thoại
        // Ví dụ: độ dài, định dạng, ...
        return true;
    }

    private boolean isValidDate(String date) {
        // Kiểm tra các điều kiện hợp lệ cho ngày sinh
        // Ví dụ: định dạng, giới hạn tuổi, ...
        return true;
    }
}

