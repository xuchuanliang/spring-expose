package com.snail.springbootsource.base.service;

/**
 * 密码解密
 */
public interface PasswordDecodable {
    String getEncodedPassword();

    void setDecodedPassword(String password);
}
