package com.aegiscrypt.service.impl;

import com.aegiscrypt.config.CryptoConfig;
import com.aegiscrypt.model.EncryptedPayload;
import com.aegiscrypt.service.EncryptionService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AegisCryptService implements EncryptionService {

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String encrypt(String password, String plainText) {
        try {
            byte[] salt = generateRandomBytes(CryptoConfig.TAMANHO_SALT_BYTES);
            byte[] iv = generateRandomBytes(CryptoConfig.TAMANHO_IV_BYTES);

            SecretKey aesKey = deriveKeyFromPassword(password, salt);

            Cipher cipher = Cipher.getInstance(CryptoConfig.TRANSFORMACAO_CIFRADOR);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(CryptoConfig.TAMANHO_TAG_GCM_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmParameterSpec);

            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            EncryptedPayload payload = new EncryptedPayload(salt, iv, cipherText);

            return serializeAndEncodePayload(payload);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar os dados.", e);
        }
    }

    @Override
    public String decrypt(String password, String encryptedData) {
        try {
            EncryptedPayload payload = decodeAndDeserializePayload(encryptedData);

            SecretKey aesKey = deriveKeyFromPassword(password, payload.salt());

            Cipher cipher = Cipher.getInstance(CryptoConfig.TRANSFORMACAO_CIFRADOR);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(CryptoConfig.TAMANHO_TAG_GCM_BITS, payload.iv());
            cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmParameterSpec);

            byte[] decryptedTextBytes = cipher.doFinal(payload.cipherText());

            return new String(decryptedTextBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar. Senha incorreta ou dados corrompidos.", e);
        }
    }

    private byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    private SecretKey deriveKeyFromPassword(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(CryptoConfig.ALGORITMO_CHAVE);
        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                CryptoConfig.CONTAGEM_ITERACOES_PBKDF2,
                CryptoConfig.TAMANHO_CHAVE_BITS
        );
        SecretKey secret = factory.generateSecret(spec);
        return new SecretKeySpec(secret.getEncoded(), CryptoConfig.ALGORITMO_AES);
    }

    private String serializeAndEncodePayload(EncryptedPayload payload) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(payload.salt().length + payload.iv().length + payload.cipherText().length);
        byteBuffer.put(payload.salt());
        byteBuffer.put(payload.iv());
        byteBuffer.put(payload.cipherText());
        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    private EncryptedPayload decodeAndDeserializePayload(String encryptedData) {
        byte[] decodedPayload = Base64.getDecoder().decode(encryptedData);
        ByteBuffer byteBuffer = ByteBuffer.wrap(decodedPayload);

        byte[] salt = new byte[CryptoConfig.TAMANHO_SALT_BYTES];
        byteBuffer.get(salt);

        byte[] iv = new byte[CryptoConfig.TAMANHO_IV_BYTES];
        byteBuffer.get(iv);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        return new EncryptedPayload(salt, iv, cipherText);
    }
}
