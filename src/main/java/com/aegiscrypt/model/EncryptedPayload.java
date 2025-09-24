package com.aegiscrypt.model;

public record EncryptedPayload(
        byte[] salt,
        byte[] iv,
        byte[] cipherText
){}
