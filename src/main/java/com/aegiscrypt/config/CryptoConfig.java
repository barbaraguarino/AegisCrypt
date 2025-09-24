package com.aegiscrypt.config;

public final class CryptoConfig {

    private CryptoConfig() {}

    public static final String ALGORITMO_CHAVE = "PBKDF2WithHmacSHA256";
    public static final String ALGORITMO_AES = "AES";
    public static final String TRANSFORMACAO_CIFRADOR = "AES/GCM/NoPadding";

    public static final int TAMANHO_CHAVE_BITS = 256;
    public static final int TAMANHO_TAG_GCM_BITS = 128;
    public static final int TAMANHO_IV_BYTES = 12;
    public static final int TAMANHO_SALT_BYTES = 16;

    public static final int CONTAGEM_ITERACOES_PBKDF2 = 65536;
}