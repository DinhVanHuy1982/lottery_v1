package com.lottery.app.commons;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class IpUtils {

    // Chuyển đổi địa chỉ IP thành số nguyên
    public static BigInteger ipToInt(String ip) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        byte[] ipBytes = inetAddress.getAddress();

        if (ipBytes.length == 16) { // IPv6
            // Chuyển đổi byte array IPv6 thành BigInteger
            return new BigInteger(1, ipBytes);
        } else { // IPv4
            // Chuyển đổi byte array IPv4 thành BigInteger
            return new BigInteger(1, ipBytes);
        }
    }

    // Chuyển đổi số nguyên thành địa chỉ IP
    public static String intToIp(BigInteger ipInt) {
        byte[] ipBytes;
        if (ipInt.bitLength() <= 32) { // IPv4
            ipBytes = new byte[4];
            byte[] ipIntBytes = ipInt.toByteArray();
            // Đảm bảo byte array có đủ chiều dài cho IPv4
            int offset = Math.max(0, ipIntBytes.length - 4);
            System.arraycopy(ipIntBytes, offset, ipBytes, 0, 4);
        } else { // IPv6
            ipBytes = ipInt.toByteArray();
            // Đảm bảo byte array có đủ chiều dài cho IPv6
            if (ipBytes.length < 16) {
                byte[] paddedBytes = new byte[16];
                System.arraycopy(ipBytes, 0, paddedBytes, 16 - ipBytes.length, ipBytes.length);
                ipBytes = paddedBytes;
            }
        }
        try {
            InetAddress inetAddress = InetAddress.getByAddress(ipBytes);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Invalid IP address", e);
        }
    }
}
