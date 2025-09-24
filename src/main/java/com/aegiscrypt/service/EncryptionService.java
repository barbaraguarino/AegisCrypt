package com.aegiscrypt.service;

public interface EncryptionService {

    String encrypt(String password, String plainText);

    String decrypt(String password, String encryptedData);
}